package objects.graph;

import objects.graph.Vertex.Vertex;
import objects.graph.Vertex.VertexID;
import objects.path.pathfinder.dijkstracache.DijkstraCachePathFinderStrategy;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Set;

class GraphTest {

    @Test
    void getVertexFromGraph() {
        // Arrange
        Graph g = new Graph(GraphDataCollection.generalPurpose, new DijkstraCachePathFinderStrategy());
        VertexID target = VertexID.ENTRANCE;

        // Act
        Vertex result = g.get(target);

        // Assert
        Assertions.assertEquals(target, result.id());
    }

    @Test
    void convertVertexToSetWithoutChanging() {
        // Arrange
        GraphData data = GraphDataCollection.generalPurpose;
        Set<Vertex> original = data.getVertices();
        Graph g = new Graph(data, new DijkstraCachePathFinderStrategy());

        // Act
        Set<Vertex> result = g.toSet();

        // Assert
        Assertions.assertEquals(original, result);
    }
}