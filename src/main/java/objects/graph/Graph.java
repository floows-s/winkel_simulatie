package objects.graph;

import core.Point;
import core.Renderable;
import core.logger.Logger;
import core.logger.TagLogger;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import objects.path.Path;
import objects.path.pathfinder.PathFinderStrategy;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Graph implements Renderable {
    private final Color VERTEX_COLOR = Color.BLUEVIOLET;
    private final int VERTEX_DIAMETER = 30;
    private final Color LINE_COLOR = Color.BLUEVIOLET;
    private final int LINE_WIDTH = 3;

    private final Logger log = new TagLogger(this.getClass().toString());
    private final PathFinderStrategy pathFinder;

    private final Map<VertexID, Vertex> vertices = new HashMap<>();

    public Graph(Set<Vertex> vertices, PathFinderStrategy pathFinder){
        this.pathFinder = pathFinder;
        mapVerticesToID(vertices, this.vertices);
    }

    private void mapVerticesToID(Set<Vertex> vertices, Map<VertexID, Vertex> map){
        for(Vertex v : vertices){
            map.put(
                    v.id(),
                    v
            );

            // TODO [TEST]: Create test to test there isn't duplicate entry's
        }
    }

    /// Gets the Vertex associated with the given ID.
    /// @return The found Vertex or null if there is none found.
    public Vertex get(VertexID id){
        return vertices.get(id);
    }


    // TODO: description
    public Path getShortestPath(VertexID from, VertexID to){
        return pathFinder.find(this, from, to);
    }

    /// A set of vertices // TODO give more useful info
    /// @return A set of vertices
    public Set<Vertex> toSet(){
        return Set.copyOf(vertices.values()); // TODO: deep copy
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

    /// Draw the outgoing edges for the given Vertex.
    private void drawEdges(Vertex v, GraphicsContext g){
        for(Edge e : v.edges()){
            Vertex to = vertices.get(e.to());
            if(to == null){
                log.logWarning("Vertex with ID [" + e.to() + "] not found. Skipping edge drawing.");
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
