package objects.graph;

import objects.graph.Vertex.VertexID;

/// TODO
/// @param to
/// @param weight TODO: shouldnt be less then 1
public record Edge(VertexID to, int weight) {
    public Edge(VertexID to, int weight){
        this.to = to;
        this.weight = weight;
    }

    /// @param to the outgoing vertex
    public Edge(VertexID to) {
        this(to, 1);
    }
}
