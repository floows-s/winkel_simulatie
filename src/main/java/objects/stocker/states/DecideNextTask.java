package objects.stocker.states;

import core.Simulation;
import objects.graph.Vertex.VertexID;
import objects.stocker.Stocker;

public class DecideNextTask implements StockerState{


    @Override
    public void update(Stocker st, double delta, long now, Simulation s) {
        if(st.isHoldingBox()){
            st.setState(new UnpackBox());
        }

        if(s.getTruck().isReadyToUnload()){
            st.setState(new UnloadTruck());
            return;
        }

        VertexID shelfContainingBox = s.getShelfManager().getAccessPointOfStorageShelfWithBox();
        if(shelfContainingBox != null){
            st.setState(new GrabBoxFromShelf());
        }

        // Else -> just wait...
        st.setState(new DecideNextTask());
    }
}
