package objects.stocker.states;

import core.Simulation;
import core.logger.Logger;
import core.logger.TagLogger;
import objects.box.Box;
import objects.graph.Vertex.VertexID;
import objects.path.Path;
import objects.shelf.Shelf;
import objects.stocker.Stocker;
import objects.truck.Truck;

public class UnloadTruck implements StockerState{
    Path pathToLoadingDock = null;
    Path pathToShelf = null;
    VertexID shelfAccessPoint;

    Logger log = new TagLogger(this.getClass().getSimpleName());

    @Override
    public void update(Stocker st, double delta, long now, Simulation s) {
        // If truck is unloaded
        if(!s.getTruck().isReadyToUnload() && !st.isHoldingBox()){
            st.setState(new DecideNextTask());
            return;
        }

        // Decide which shelf to put box on
        if(shelfAccessPoint == null){
            decideShelfToPutBoxOn(st, s);
            return;
        }

        // If holding box -> put on shelf
        if(st.isHoldingBox()){

            if(st.getCurrentVertexID() == shelfAccessPoint){
                putBoxOnShelf(st, s, delta, now);

                // Continue to unload truck
                st.setState(new UnloadTruck());
                return;
            }

            walkToShelf(st, s, delta, now);
            return;
        }

        // If at loading dock -> grab box from shelf
        if(st.getCurrentVertexID() == VertexID.LOADING_DOCK){
            grabBoxFromTruck(st, s.getTruck());
            return;
        }

        walkToLoadingDock(st, s, delta, now);
    }

    private void walkToLoadingDock(Stocker st, Simulation s, double delta, long now){
        if(pathToLoadingDock == null){
            pathToLoadingDock = s.getGraph().getShortestPath(st.getCurrentVertexID(), VertexID.LOADING_DOCK);
        }

        st.followPath(pathToLoadingDock, s.getGraph(), delta, now);
    }

    private void walkToShelf(Stocker st, Simulation s, double delta, long now) {
        if(pathToShelf == null){
            pathToShelf = s.getGraph().getShortestPath(st.getCurrentVertexID(), shelfAccessPoint);
        }

        st.followPath(pathToShelf, s.getGraph(), delta, now);
    }

    private void decideShelfToPutBoxOn(Stocker st, Simulation s){
        VertexID foundShelf = s.getShelfManager().getAccessPointOfShelfWithRoomForBox();

        // If no shelf has room for a box
        if(foundShelf == null){
            log.logError("No shelf has room for a box. Cant unload truck!");
            st.setState(new DecideNextTask());
            return;
        }

        shelfAccessPoint = foundShelf;
    }

    private void putBoxOnShelf(Stocker st, Simulation s, double delta, long now){
        Shelf shelf = s.getShelfManager().get(shelfAccessPoint);
        shelf.addItem(st.removeBox());
    }

    private void grabBoxFromTruck(Stocker st, Truck t){
        Box box = t.GetBox();
        st.holdBox(box);
    }
}
