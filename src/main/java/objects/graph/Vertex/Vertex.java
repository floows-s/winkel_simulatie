package objects.graph.Vertex;

import core.Point;
import core.Renderable;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import objects.graph.Edge;

import java.util.List;


///
/// @param id
/// @param location On the canvas
/// @param edges Outgoing
/// @param color Of circle on canvas. (Default: {@code Color.RED})
/// @param diameter Of circle on canvas. (Default: {@code 21})
public record Vertex(VertexID id, Point location, List<Edge> edges, Color color, Integer diameter) implements Renderable {

    public static Color DEFAULT_COLOR = Color.RED;
    public static Integer DEFAULT_DIAMETER = 21;


    public Vertex {
        edges = edges == null ? List.of() : List.copyOf(edges);
        color = color == null ? DEFAULT_COLOR : color;
        diameter = diameter == null ? DEFAULT_DIAMETER : diameter;
    }

    public Vertex(VertexID id, Point location, List<Edge> edges) {
        this(id, location, List.copyOf(edges), DEFAULT_COLOR, DEFAULT_DIAMETER);
    }

    public Vertex(Vertex v) {
        this(v.id, v.location, List.copyOf(v.edges), v.color, v.diameter);
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

    @Override
    public void render(GraphicsContext g) {
        g.setFill(color);

        int r = diameter / 2;

        g.fillOval(
                location.x() - r,
                location.y() - r,
                diameter,
                diameter
        );
    }

    @Override
    public float getX() {
        return location.x();
    }

    @Override
    public float getY() {
        return location.y();
    }
}
