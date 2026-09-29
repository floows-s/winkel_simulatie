package objects.graph;

public record Edge(VertexID to, float weight) {
    /// @param to the outgoing vertex
    public Edge(VertexID to) {
        this(to, 1);
    }
}
