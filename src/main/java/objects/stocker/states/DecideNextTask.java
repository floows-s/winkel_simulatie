package objects.stocker.states;

import core.Simulation;
import objects.graph.Vertex.VertexID;
import objects.path.Path;
import objects.stocker.Stocker;

public class DecideNextTask implements StockerState{

    Path pathToMiddleOfStockroom = null;

    @Override
    public void update(Stocker st, double delta, long now, Simulation s) {
        if(st.isHoldingBox()){
            st.setState(new UnpackHeldBox());
            return;
        }

        if(s.getTruck().isReadyToUnload()){
            st.setState(new UnloadTruck());
            return;
        }

        // Grab a box that can actually be unpacked (its product type has a shelf with room)
        VertexID shelfContainingBox = s.getShelfManager().getAccessPointOfStorageShelfWithUnpackableBox();

        if(shelfContainingBox != null){
            st.setState(new GrabBoxFromShelf(shelfContainingBox));
            return;
        }

        // Nothing to do -> go back to the middle of the stockroom and wait there
        if(st.getCurrentVertexID() != VertexID.STOCKROOM_MIDDLE){
            if(pathToMiddleOfStockroom == null){
                pathToMiddleOfStockroom = s.getGraph().getShortestPath(
                        st.getCurrentVertexID(),
                        VertexID.STOCKROOM_MIDDLE
                );
            }

            if(pathToMiddleOfStockroom != null){
                st.followPath(pathToMiddleOfStockroom, s.getGraph(), delta, now);
            }
            return;
        }

        // Already at middle of stockroom -> just stand idle
        st.setSpriteToIdle();
    }
}