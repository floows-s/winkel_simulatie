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
        }

        if(s.getTruck().isReadyToUnload()){
            st.setState(new UnloadTruck());
            return;
        }

        VertexID shelfContainingBox = s.getShelfManager().getAccessPointOfStorageShelfWithBox();
        if(shelfContainingBox != null){
            st.setState(new GrabBoxFromShelf(shelfContainingBox));
        }

        // Else -> just wait...
        st.setState(new DecideNextTask());
    }
}
