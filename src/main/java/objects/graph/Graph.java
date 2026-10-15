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
import java.util.stream.Collectors;

public class Graph implements Renderable {
    private final Color VERTEX_COLOR = Color.RED;
    private final int VERTEX_DIAMETER = 21;
    private final Color LINE_COLOR = Color.RED;
    private final int LINE_WIDTH = 2;

    private final Logger log = new TagLogger(this.getClass().getSimpleName());
    private final PathFinderStrategy pathFinder;

    private final Map<VertexID, Vertex> vertices = new HashMap<>();

    public Graph(GraphData data, PathFinderStrategy pathFinder){
        this.pathFinder = pathFinder;
        mapVerticesToID(data.getVertices(), this.vertices);
    }

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


    /// Get the shortest path between two vertices.
    /// @param from starting vertex
    /// @param to ending vertex
    /// @return The shortest path between {@code from} and {@code to}
    public Path getShortestPath(VertexID from, VertexID to){
        return pathFinder.find(this, from, to);
    }

    /// Collects all the vertices in the graph into a set.
    /// @return a set of all the vertices in the graph
    public Set<Vertex> toSet(){
        return vertices.values().stream().map(Vertex::new).collect(Collectors.toSet());
    }

    @Override
    public void render(GraphicsContext g) {

        for(var kv : vertices.entrySet()){
            g.setFill(VERTEX_COLOR);
            g.setStroke(LINE_COLOR);
            g.setLineWidth(LINE_WIDTH);

            Vertex v = kv.getValue();
            Point loc = v.location();

            int r = VERTEX_DIAMETER / 2;

            // Draw vertex
            g.fillOval(
                    loc.x() - r,
                    loc.y() - r,
                    VERTEX_DIAMETER,
                    VERTEX_DIAMETER
            );

            // Draw edges of vertex
            drawEdges(v, g);
        }
    }

    /// Draw the outgoing edges for the given {@code Vertex}.
    /// Skipping once where the outgoing vertex is not part of this graph.
    private void drawEdges(Vertex v, GraphicsContext g){
        for(Edge e : v.edges()){
            Vertex to = vertices.get(e.to());
            if(to == null){
                log.logWarning("Vertex with ID [" + e.to() + "] not found. Skipping edge drawing.");
                continue;
            }

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
