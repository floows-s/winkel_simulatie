package objects.stocker.states;


import core.Simulation;
import core.logger.Logger;
import core.logger.TagLogger;
import objects.graph.Vertex.VertexID;
import objects.path.Path;
import objects.shelf.Shelf;
import objects.stocker.Stocker;

public class PutHeldBoxIntoStockroom implements StockerState {

    Path pathToShelf = null;
    VertexID shelfAccessPoint = null;

    Logger log = new TagLogger(this.getClass().getSimpleName());

    @Override
    public void update(Stocker st, double delta, long now, Simulation s) {
        if(shelfAccessPoint == null){
            VertexID foundShelf = s.getShelfManager().getAccessPointOfShelfWithRoomForBox();

            if(foundShelf == null){
                log.logError("No shelf found to put held box on. Removing box to the void...");
                st.removeBox();

                st.setState(new DecideNextTask());
                return;
            }

            shelfAccessPoint = foundShelf;
        }

        if(st.getCurrentVertexID() == shelfAccessPoint){
            Shelf shelf = s.getShelfManager().get(shelfAccessPoint);

            shelf.addItem(st.removeBox());
            st.setState(new DecideNextTask());
            return;
        }

        if(pathToShelf == null){
            pathToShelf = s.getGraph().getShortestPath(st.getCurrentVertexID(), shelfAccessPoint);
        }

        st.followPath(pathToShelf, s.getGraph(), delta, now);
    }
}
