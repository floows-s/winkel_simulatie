package objects.path.pathfinder;

import objects.graph.Graph;
import objects.graph.VertexID;
import objects.path.Path;

public interface PathFinderStrategy {
    Path find(Graph graph, VertexID from, VertexID to);
}
