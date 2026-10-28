package objects.stocker.states;

import core.Simulation;
import objects.graph.Vertex.VertexID;
import objects.shelf.Shelf;
import objects.stocker.Stocker;

public class GrabBoxFromShelf implements StockerState {
    private VertexID shelfAccessPoint;

    public GrabBoxFromShelf(VertexID shelfAccessPoint){
        this.shelfAccessPoint = shelfAccessPoint;
    }

    @Override
    public void update(Stocker st, double delta, long now, Simulation s) {
        if(st.getCurrentVertexID() == shelfAccessPoint){
            Shelf shelf = s.getShelfManager().get(shelfAccessPoint);

            shelf.grabProduct()
        }
    }
}
