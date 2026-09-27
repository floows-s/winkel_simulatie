package objects.graph;

public record Edge(VertexID to, float weight) {
    public Edge(VertexID to) {
        this(to, 1);
    }
}
