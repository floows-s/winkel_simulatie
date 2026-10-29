package objects.customer.states;

import core.Simulation;
import objects.customer.Customer;
import objects.graph.Vertex.VertexID;
import objects.path.Path;

public class DespawnMyself implements CustomerState {
    Path pathToDespawnPoint = null;

    @Override
    public void update(Customer c, double delta, long now, Simulation s) {
        if(c.getCurrentVertexID() == VertexID.DESPAWN_POINT){
            s.despawnCustomer(c);
        }

        if(pathToDespawnPoint == null){
            pathToDespawnPoint = s.getGraph().getShortestPath(c.getCurrentVertexID(), VertexID.DESPAWN_POINT);
        }

        c.followPath(pathToDespawnPoint, s.getGraph(), delta, now);
    }
}
