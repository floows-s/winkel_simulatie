package objects.path.pathfinder.dijkstracache;

import core.logger.Logger;
import core.logger.TagLogger;
import objects.graph.Edge;
import objects.graph.Graph;
import objects.graph.Vertex;
import objects.graph.VertexID;
import objects.path.Path;
import objects.path.pathfinder.PathFinderStrategy;

import java.util.*;

public class DijkstraCachePathFinderStrategy implements PathFinderStrategy {

    private record cacheEntryKey (VertexID source, VertexID target){}
    private final Map<cacheEntryKey, Path> cache = new HashMap<>();

    private final Logger log = new TagLogger(getClass().getSimpleName());

    /// Calculates the shortest path between two vertices using Dijkstra's algorithm.
    /// <p>
    /// It will hold the calculated shortest path's in a cache so you don't need target worry about repeated calculations.
    /// @return The shortest path from {@code source} to {@code target}
    @Override
    public Path find(Graph graph, VertexID source, VertexID target) {
        // Pre-check's

        if(source.equals(target)){
            // From one vertex target the same vertex is the shortest path: A path with no steps...
            return new Path(new ArrayList<>());
        }

        { // Check if given VertexID's is a part of given graph
            Vertex sourceVertex = graph.get(source);
            Vertex targetVertex = graph.get(target);

            if(sourceVertex == null || targetVertex == null){
                log.logError("Given source or target VertexID is not part of the given Graph. Can't find shortest path.");
                return new Path(new ArrayList<>());
            }
        }

        return findCache(graph, source, target);
    }

    /// First check's the cache if the shortest path has already been found.
    /// If not, it will find the shortest path and update the cache.
    /// @return The shortest path from {@code source} to {@code target}
    private Path findCache(Graph graph, VertexID source, VertexID target){
        cacheEntryKey key = new cacheEntryKey(source, target);

        if(!cache.containsKey(key)){
            Path foundPath = dijkstraAlgorithm(graph, source, target);
            List<Path> subPaths = extractSubPathsFromPath(foundPath, source);

            UpdateCache(source, subPaths);
            printCache();
        }

        return cache.get(key);
    }

    List<Path> extractSubPathsFromPath(Path path, VertexID source){
        ArrayList<Path> subPaths = new ArrayList<>();

        List<VertexID> currentPath = new ArrayList<>();
        for(VertexID vID : path.toList()){
            currentPath.add(vID);

            if(vID.equals(source)) continue;

            subPaths.add(
                    new Path(currentPath)
            );
        }

        return subPaths;
    }

    void UpdateCache(VertexID source, List<Path> paths){
        for(Path path : paths){
            List<VertexID> vertices = path.toList(); // TODO: update this? Make in Path class getLast and getFirst??
            VertexID to = vertices.getLast();

            cache.put(
                    new cacheEntryKey(source, to),
                    path
            );
        }
    }

    /// Print the cache to System.out.
    /// Used for debugging.
    private void printCache(){
        for(var entry : cache.entrySet()){
            cacheEntryKey key = entry.getKey();
            Path value = entry.getValue();

            System.out.print("From: [" + key.source() + "] To: [" + key.target() +  "] Path: ");

            for(VertexID vID : value.toList()){
                System.out.print("[" + vID + "] ");
            }

            System.out.println();
        }
    }

    /// An entry that holds a vertex which should be explored. Used in {@link dijkstraAlgorithm}.
    /// @param vertex Vertex to explore
    /// @param dist Shortest distance to the vertex known at time of {@code ExploreEntry } creation.
    private record ExploreEntry(VertexID vertex, int dist) {}

    /// Finds the shortest path between two vertices in a weighted graph using Dijkstra's algorithm.
    /// <p>
    /// Edge weights must be non-negative, otherwise the result is not guaranteed to be the shortest path.
    /// @param graph the graph to search
    /// @param source the vertex the path starts at
    /// @param target the vertex the path should end at
    /// @return TODO
    private Path dijkstraAlgorithm(Graph graph, VertexID source, VertexID target){
        // --- SETUP ---

        // Note: This could be optimized, not every vertexID may be in a graph.
        //       You could map an index to the enum value and use that, instead of the ordinal of the enum.
        //       For this project I don't think its worth it to spend time on this.
        //       Just wanting to note that i'm aware of this.
        int nOfPossibleVertexIDs = VertexID.values().length;

        // Prev: Index -> vertex, value -> previous vertex on the path
        VertexID[] prev = new VertexID[nOfPossibleVertexIDs];

        // Dist: Index -> vertex, value -> shortest known distance
        int[] dist = new int[nOfPossibleVertexIDs];
        Arrays.fill(dist, Integer.MAX_VALUE);
        dist[source.ordinal()] = 0;

        // Priority queue decides which vertex will be explored next (poll shortest distance)
        PriorityQueue<ExploreEntry> exploreQueue = new PriorityQueue<>(Comparator.comparingInt(ExploreEntry::dist));
        exploreQueue.add(new ExploreEntry(source, 0));

        // --- ALGORITHM ---
        while(!exploreQueue.isEmpty()){
            ExploreEntry cur = exploreQueue.poll();
            if(cur.vertex() == target) break;

            int curIndex = cur.vertex().ordinal();

            // Check if a shorter path has already been found
            if(cur.dist() > dist[curIndex]) continue;

            List<Edge> edges = graph.get(cur.vertex()).edges();
            for(Edge e : edges){
                int combinedWeight = cur.dist() + e.weight();
                int outgoingVertexIndex = e.to().ordinal();

                if(combinedWeight < dist[outgoingVertexIndex]){
                    dist[outgoingVertexIndex] = combinedWeight;
                    prev[outgoingVertexIndex] = cur.vertex();

                    exploreQueue.add(new ExploreEntry(e.to(), combinedWeight));
                }
            }
        }

        return extractPathFromParentArray(prev, source, target);
    }

    private Path extractPathFromParentArray(VertexID[] prev, VertexID source, VertexID target){
        for(int i = 0; i<prev.length; i++){
            System.out.println("[" + i + "]: " + prev[i]);
        }



        VertexID cur = target;
        while(cur != source){

        }

        return null;
    }
}
