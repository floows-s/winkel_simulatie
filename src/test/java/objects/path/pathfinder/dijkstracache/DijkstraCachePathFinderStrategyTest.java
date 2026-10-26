package objects.path.pathfinder.dijkstracache;

import objects.graph.Graph;
import objects.graph.GraphDataCollection;
import objects.graph.Vertex.VertexID;
import objects.path.Path;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

class DijkstraCachePathFinderStrategyTest {

    @Test
    void findShortestPath() {
        // Arrange
        VertexID from = VertexID.ENTRANCE;
        VertexID to = VertexID.EXIT;

        Path expected = new Path(List.of(
                VertexID.ENTRANCE, VertexID.AISLE_1_SHELF_1, VertexID.AISLE_1_SHELF_2, VertexID.EXIT
        ));
        Graph g = new Graph(GraphDataCollection.dijkstraPathFinder, new DijkstraCachePathFinderStrategy());

        // Act
        Path result = g.getShortestPath(from, to);

        // Assert
        Assertions.assertEquals(expected, result);
    }

    // TODO: get path, check if first step is source

    // TODO: get path, check if last step is target
}