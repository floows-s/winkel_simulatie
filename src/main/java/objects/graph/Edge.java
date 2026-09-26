package objects.graph;

public record Edge(int to, float weight) {
    public Edge(int to) {
        this(to, 1);
    }
}
