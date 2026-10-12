package objects.graph;

import core.Point;

import java.util.List;

/**
 * @param id
 * @param location On the canvas
 * @param edges    Outgoing
 */
public record Vertex(VertexID id, Point location, List<Edge> edges) {
    public Vertex(VertexID id, Point location, List<Edge> edges) {
        this.id = id;
        this.location = location;
        this.edges = List.copyOf(edges);
    }

    public Vertex(Vertex v) {
        this(v.id, v.location, List.copyOf(v.edges));
    }

    /// List of outgoing edges
    /// @return List of outgoing edges
    @Override
    public List<Edge> edges() {
        return List.copyOf(edges);
    }

    /// Is equal when the id() is equal
    /// @param obj   the reference object with which to compare.
    /// @return If obj is equal
    @Override
    public boolean equals(Object obj) {
        if(this == obj){
            return true;
        }

        if(obj instanceof Vertex v){
            if(this.id() == v.id()){
                return true;
            }
        }

        return false;
    }


    @Override
    public int hashCode() {
        return this.id.ordinal();
    }
}
