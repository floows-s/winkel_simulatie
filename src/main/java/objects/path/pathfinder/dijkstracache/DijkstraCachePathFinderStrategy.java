package objects.path.pathfinder.dijkstracache;

import javafx.util.Pair;
import objects.graph.Edge;
import objects.graph.Graph;
import objects.graph.Vertex;
import objects.graph.VertexID;
import objects.path.Path;
import objects.path.pathfinder.PathFinderStrategy;

import java.util.*;

public class DijkstraCachePathFinderStrategy implements PathFinderStrategy {

    private record cacheEntryKey (VertexID from, VertexID to){}
    private final Map<cacheEntryKey, Path> cache = new HashMap<>();

    /// Calculates the shortest path between two vertices using Dijkstra's algorithm.
    /// It will hold the calculated shortest path's in a cache so you don't need to worry about repeated calculations.
    ///
    /// @return The shortest path
    @Override
    public Path find(Graph graph, VertexID from, VertexID to) {
        if(from.equals(to)){
            // From one vertex to the same vertex is the shortest path, a path with no steps...
            return new Path(new ArrayList<>());
        }

        return findCache(graph, from, to);
    }

    private Path findCache(Graph graph, VertexID from, VertexID to){
        cacheEntryKey key = new cacheEntryKey(from, to);

        if(!cache.containsKey(key)){
            List<Path> foundPaths = dijkstraAlgorithm(graph, from, to);

            UpdateCache(from, foundPaths);
            printCache();
        }

        return cache.get(key);
    }

    private void printCache(){
        for(var entry : cache.entrySet()){
            cacheEntryKey key = entry.getKey();
            Path value = entry.getValue();

            System.out.print("From: [" + key.from() + "] To: [" + key.to() +  "] Path: ");

            for(VertexID vID : value.toList()){
                System.out.print("[" + vID + "] ");
            }

            System.out.println();
        }
    }

    // TODO: look at this maybe rename??
    private record State(VertexID vertex, int dist) {}

    private List<Path> dijkstraAlgorithm(Graph graph, VertexID from, VertexID target){
        // --- SETUP ---
        int nOfPossibleVertexIDs = VertexID.values().length;

        // Prev: Index -> vertex, value -> previous vertex on the path
        VertexID[] prev = new VertexID[nOfPossibleVertexIDs];

        // Dist: Index -> vertex, value -> shortest known distance
        int[] dist = new int[nOfPossibleVertexIDs]; // TODO: think about how to correspond the enum to the index without making a array that is the size of all possible vertex ids
        Arrays.fill(dist, Integer.MAX_VALUE);

        // Priority queue decides which vertex will be explored next (poll shortest distance)
        PriorityQueue<State> pq = new PriorityQueue<>(Comparator.comparingInt(State::dist));
        pq.add(new State(from, 0));

        // --- ALGORITHM ---
        while (!pq.isEmpty()) {
            // Get next vertex to explore
            State cur = pq.poll();

            if(cur.vertex() == target){
                break;
            }

            int curIndex = cur.vertex().ordinal();
            List<Edge> edges = graph.get(cur.vertex()).edges();

            // Check if a shorter path has already been found
            if(cur.dist() > dist[curIndex]) continue;

            for(Edge e : edges){
                int combinedWeight = cur.dist() + e.weight();

                if(combinedWeight < dist[e.to().ordinal()]){
                    dist[e.to().ordinal()] = combinedWeight;
                    prev[e.to().ordinal()] = cur.vertex();

                    pq.add(new State(e.to(), combinedWeight));
                }
            }
        }

        return parsePathParentArray(prev, from, target);
    }

    List<Path> parsePathParentArray(VertexID[] path, VertexID from, VertexID target){
        // Print parent array.
        // TODO: rename path parrent array

        // All the found paths. From to (target and everything in between).
        ArrayList<ArrayList<VertexID>> result = new ArrayList<>();

        VertexID cur = target;
        while(cur != from){
            // We are reversing through the list
            VertexID previousVertex = path[cur.ordinal()];

            result.add(new ArrayList<>());
            result.getLast().add(cur);

            // Loop over all paths we have
            for(ArrayList<VertexID> p : result){
                p.addFirst(previousVertex);
            }

            cur = previousVertex;
        }

        List<Path> paths = new ArrayList<>();
        for(ArrayList<VertexID> p : result){
            paths.add(
                    new Path(p)
            );
        }

        return paths;
    }

    void UpdateCache(VertexID from, List<Path> paths){
        for(Path path : paths){
            List<VertexID> vertices = path.toList(); // TODO: update this?
            VertexID to = vertices.getLast();

            cache.put(
                    new cacheEntryKey(from, to),
                    path
            );
        }
    }
}
