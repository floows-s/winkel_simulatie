package objects.path.pathfinder.dijkstracache;

import javafx.util.Pair;
import objects.graph.Edge;
import objects.graph.Graph;
import objects.graph.Vertex;
import objects.graph.VertexID;
import objects.path.MutablePath;
import objects.path.Path;
import objects.path.pathfinder.PathFinderStrategy;

import java.lang.reflect.Array;
import java.util.*;

public class DijkstraCachePathFinderStrategy implements PathFinderStrategy<Vertex, Path> {

    private final Map<Pair<Vertex, Vertex>, Path> cache = new HashMap<>();
    private final Graph graph;

    public DijkstraCachePathFinderStrategy(Graph g){
        graph = g;
    }

    /// Calculates the shortest path between two vertices using Dijkstra's algorithm.
    /// It will hold the calculated shortest path's in a cache so you don't need to worry about repeated calculations.
    ///
    /// @return The shortest path
    @Override
    public Path find(Vertex from, Vertex to) {
        if(from.equals(to)){
            // From one vertex to the same vertex is the shortest path, a path with no steps...
            return new Path(List.of());
        }

        return findCache(from, to);
    }

    private Path findCache(Vertex from, Vertex to){
        Pair key = new Pair(from, to);

        if(!cache.containsKey(key)){
            cache.put(key, dijkstraAlgorithm(from, to));
        }

        return cache.get(key);
    }

    record State(VertexID vertex, int dist) {}

    private Path dijkstraAlgorithm(Vertex from, Vertex to){
        // Set of explored vertices
        Set<Vertex> vertices = graph.toSet();

        int n = vertices.size();

        // Prev: Index -> vertex, value -> previous vertex on the path
        VertexID[] prev = new VertexID[n];

        // Dist: Index -> vertex, value -> shortest known distance
        int[] dist = new int[n];
        Arrays.fill(dist, Integer.MAX_VALUE);

        // Priority queue decides which vertex will be explored next (poll shortest distance)
        PriorityQueue<State> pq = new PriorityQueue<>(Comparator.comparingInt(State::dist));
        pq.add(new State(from.id(), 0));

        while (!pq.isEmpty()) {
            State cur = pq.poll();
            int curIndex = cur.vertex().ordinal();
            List<Edge> edges = graph.get(cur.vertex()).edges();

            // Check if a shorter path has already been found
            if(cur.dist() < dist[curIndex]) continue;


            for(Edge e : edges){
                // Check if combined weight is more than current
                // Update dist prev and queue
            }
        }


        return null;
    }
}
