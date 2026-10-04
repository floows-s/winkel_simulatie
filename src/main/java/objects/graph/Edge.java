package objects.graph;

public record Edge(VertexID to, int weight) {
    /// @param to the outgoing vertex
    public Edge(VertexID to) {
        this(to, 1);
    }
}
