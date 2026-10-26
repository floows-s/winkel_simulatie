package objects.customer.States;

import core.Simulation;
import core.logger.Logger;
import core.logger.TagLogger;
import objects.customer.Customer;
import objects.graph.Graph;
import objects.graph.Vertex.VertexID;
import objects.path.Path;
import utilities.Randomizer;

public class ExploringStore implements CustomerState {
    private Path path = null;

    private Logger log = new TagLogger(ExploringStore.class.getSimpleName());

    @Override
    public void update(Customer customer, double delta, long now, Simulation s) {
        if(path == null || path.isFinished()){
            path = decideNewRandomPath(customer.getCurrentVertexID(), s.graph);
        }

        customer.followPath(path, s.graph, delta, now);
    }

    private Path decideNewRandomPath(VertexID source, Graph g) {
        VertexID random = VertexID.values()[Randomizer.nextInt(1, VertexID.values().length)];
        log.logInfo("Next target: [" + random + "]");

        return g.getShortestPath(source, random);
    }
}
