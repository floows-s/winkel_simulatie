package objects.graph;

import objects.graph.Vertex.Vertex;
import objects.graph.Vertex.VertexID;

import java.util.List;
import java.util.Set;

public class GraphDataCollection {

    /// A small, general-purpose store layout graph, intended for testing simple cases.
    /// <p>
    /// Contains six vertices: {@code ENTRANCE}, four shelves in a single aisle
    /// ({@code AISLE_1_SHELF_1} to {@code AISLE_1_SHELF_4}), and {@code EXIT}.
    /// Edges connect neighboring locations with a default weight, so the graph can be walked from the entrance to the exit via multiple routes.
    public static final GraphData generalPurpose = new GraphData(
            Set.of(
                    new Vertex(VertexID.ENTRANCE, List.of(new Edge(VertexID.AISLE_1_SHELF_1), new Edge(VertexID.AISLE_1_SHELF_3)), null),
                    new Vertex(VertexID.AISLE_1_SHELF_1, List.of(new Edge(VertexID.ENTRANCE), new Edge(VertexID.AISLE_1_SHELF_2)), null),
                    new Vertex(VertexID.AISLE_1_SHELF_2, List.of(new Edge(VertexID.AISLE_1_SHELF_1), new Edge(VertexID.AISLE_1_SHELF_3), new Edge(VertexID.AISLE_1_SHELF_4), new Edge(VertexID.EXIT)), null),
                    new Vertex(VertexID.AISLE_1_SHELF_3, List.of(new Edge(VertexID.ENTRANCE), new Edge(VertexID.AISLE_1_SHELF_2), new Edge(VertexID.AISLE_1_SHELF_4)), null),
                    new Vertex(VertexID.AISLE_1_SHELF_4, List.of(new Edge(VertexID.AISLE_1_SHELF_3), new Edge(VertexID.AISLE_1_SHELF_2), new Edge(VertexID.EXIT)), null),
                    new Vertex(VertexID.EXIT, List.of(new Edge(VertexID.AISLE_1_SHELF_2), new Edge(VertexID.AISLE_1_SHELF_4)), null)
            )
    );


    /// Use to test Dijkstra's algorithm
    /// <p>
    /// <b>Fastest route</b>
    /// <p>
    /// {@code [ENTRANCE]} -> {@code [AISLE_1_SHELF_1]} -> {@code [AISLE_1_SHELF_2]} -> {@code [EXIT]}
    public static final GraphData dijkstraPathFinder = new GraphData(
            Set.of(
                    new Vertex(VertexID.ENTRANCE, List.of(new Edge(VertexID.AISLE_1_SHELF_1, 1), new Edge(VertexID.AISLE_1_SHELF_3, 5)), null),
                    new Vertex(VertexID.AISLE_1_SHELF_1, List.of(new Edge(VertexID.ENTRANCE, 1), new Edge(VertexID.AISLE_1_SHELF_2, 1)), null),
                    new Vertex(VertexID.AISLE_1_SHELF_2, List.of(new Edge(VertexID.AISLE_1_SHELF_1, 1), new Edge(VertexID.AISLE_1_SHELF_3, 5), new Edge(VertexID.AISLE_1_SHELF_4, 5), new Edge(VertexID.EXIT, 1)), null),
                    new Vertex(VertexID.AISLE_1_SHELF_3, List.of(new Edge(VertexID.ENTRANCE, 5), new Edge(VertexID.AISLE_1_SHELF_2, 5), new Edge(VertexID.AISLE_1_SHELF_4, 5)), null),
                    new Vertex(VertexID.AISLE_1_SHELF_4, List.of(new Edge(VertexID.AISLE_1_SHELF_3, 5), new Edge(VertexID.AISLE_1_SHELF_2, 5), new Edge(VertexID.EXIT, 5)), null),
                    new Vertex(VertexID.EXIT, List.of(new Edge(VertexID.AISLE_1_SHELF_2, 1), new Edge(VertexID.AISLE_1_SHELF_4, 5)), null)
            )
    );
}
