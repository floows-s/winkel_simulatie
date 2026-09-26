package objects.graph;

import core.Point;

import java.util.List;

/**
 * @param id
 * @param location On the canvas
 * @param edges    Outgoing
 */
public record Vertex(int id, Point location, List<Edge> edges) {
    public Vertex(int id, Point location, List<Edge> edges) {
        this.id = id;
        this.location = location;
        this.edges = List.copyOf(edges); // TODO: deep copy?
    }

    @Override
    public List<Edge> edges() {
        return List.copyOf(edges); // TODO: deep copy?
    }
}
