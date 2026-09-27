package objects.graph;

import core.Point;
import core.Renderable;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Graph implements Renderable {

    private final Color VERTEX_COLOR = Color.BLUEVIOLET;
    private final int VERTEX_DIAMETER = 30;

    private final Color LINE_COLOR = Color.BLUEVIOLET;
    private final int LINE_WIDTH = 3;

    private final Map<VertexID, Vertex> vertices = new HashMap<>();

    public Graph(List<Vertex> vertices){
        mapVerticesToID(vertices, this.vertices);
    }

    private void mapVerticesToID(List<Vertex> vertices, Map<VertexID, Vertex> map){
        for(Vertex v : vertices){
            map.put(
                    v.id(),
                    v
            );
        }
    }

    /// Gets the Vertex associated with the given ID.
    /// @return The found Vertex or null if there is none found.
    public Vertex get(VertexID id){
        return vertices.get(id);
    }

    @Override
    public void render(GraphicsContext g) {
        g.setFill(VERTEX_COLOR);
        g.setStroke(LINE_COLOR);
        g.setLineWidth(LINE_WIDTH);

        for(var kv : vertices.entrySet()){
            Vertex v = kv.getValue();
            Point loc = v.location();

            int r = VERTEX_DIAMETER / 2;

            // Draw vertex
            g.fillOval(
                    loc.x() - r,
                    loc.y() - r,
                    30,
                    30
            );

            // Draw edges of vertex
            drawEdges(v, g);
        }
    }

    private void drawEdges(Vertex v, GraphicsContext g){
        for(Edge e : v.edges()){
            Vertex to = vertices.get(e.to());
            if(to == null){
                System.out.println("[Graph] Warning: Vertex with ID [" + e.to() + "] not found. Skipping edge drawing.");
                continue;
            }

            // TODO: Right now its drawing edges which may be already drawn. This can be optimized.
            g.strokeLine(
                    v.location().x(),
                    v.location().y(),
                    to.location().x(),
                    to.location().y()
            );
        }
    }

    @Override
    public float getX() {
        return 0;
    }

    @Override
    public float getY() {
        return 0;
    }
}
