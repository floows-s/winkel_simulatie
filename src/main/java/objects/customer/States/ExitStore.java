package objects.customer.States;

import core.Simulation;
import objects.customer.Customer;
import objects.graph.Vertex.VertexID;
import objects.path.Path;

public class ExitStore implements CustomerState {
    Path pathToExit = null;

    @Override
    public void update(Customer c, double delta, long now, Simulation s) {
        if(pathToExit == null){
            pathToExit = s.graph.getShortestPath(c.getCurrentVertexID(), VertexID.DESPAWN_POINT);
        }

        boolean atDestination = c.followPath(pathToExit, s.graph, delta, now);
        if(!atDestination) return;

        c.setState(new DespawnMyself());
    }
}
