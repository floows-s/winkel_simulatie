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

    private final Map<Integer, Vertex> vertices = new HashMap<>();

    public Graph(List<Vertex> vertices){
        mapVertices(vertices);
    }

    private void mapVertices(List<Vertex> vertices){
        for(Vertex v : vertices){
            this.vertices.put(
                    v.id(),
                    v
            );
        }
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
