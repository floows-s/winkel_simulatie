package objects.path.pathfinder.dijkstracache;

import core.logger.Logger;
import core.logger.TagLogger;
import objects.graph.Edge;
import objects.graph.Graph;
import objects.graph.Vertex.Vertex;
import objects.graph.Vertex.VertexID;
import objects.path.Path;
import objects.path.pathfinder.PathFinderStrategy;

import java.util.*;

public class DijkstraCachePathFinderStrategy implements PathFinderStrategy {

    private record cacheEntryKey (VertexID source, VertexID target){}
    private final Map<cacheEntryKey, List<VertexID>> cache = new HashMap<>();
    private boolean shouldPrintCacheAfterUpdate = false;
    private final Logger log = new TagLogger(getClass().getSimpleName());

    public DijkstraCachePathFinderStrategy(){}

    /// Calculates the shortest path between two vertices using Dijkstra's algorithm.
    /// <p>
    /// It will hold the calculated shortest path's in a cache so you don't need target worry about repeated calculations.
    /// @return The shortest path from {@code source} to {@code target}
    @Override
    public Path find(Graph graph, VertexID source, VertexID target) {
        // Pre-check's

        if(source.equals(target)){
            // From one vertex target the same vertex is the shortest path: A path with no steps...
            return new Path();
        }

        { // Check if given VertexID's is a part of given graph
            Vertex sourceVertex = graph.get(source);
            Vertex targetVertex = graph.get(target);

            if(sourceVertex == null || targetVertex == null){
                log.logError("Given source or target VertexID is not part of the given Graph. Can't find shortest path.");
                return new Path();
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
            // Get paths
            List<VertexID> foundPath = dijkstraAlgorithm(graph, source, target);
            List<List<VertexID>> prefixPaths = extractPrefixPaths(foundPath);

            // Collect
            List<List<VertexID>> pathsToCache = new ArrayList<>();
            pathsToCache.add(foundPath);
            pathsToCache.addAll(prefixPaths);

            // Update
            UpdateCache(pathsToCache);
        }


        List<VertexID> shortestPath = cache.get(key);

        if(shortestPath == null){
            log.logError("Failed to get shortest path from cache. Path from entry [" + source + "], to [" + target + "] is null.");
            return new Path();
        }

        return new Path(cache.get(key));
    }

    /// Returns all prefixes of the given path that start at its source.
    /// <p>
    /// Excludes the single-vertex prefix (the source alone).
    /// In order of increasing length.
    ///
    /// @param steps of the path to extract prefixes from
    /// @return a list of prefix paths
    private List<List<VertexID>> extractPrefixPaths(List<VertexID> steps){
        List<List<VertexID>> prefixPaths = new ArrayList<>();

        // Start at 2 because subList toIndex is exclusive & we want to skip the single-vertex prefix, that is the source alone
        for(int i = 2; i < steps.size(); ++i){
            prefixPaths.add(
                steps.subList(0, i)
            );
        }

        return prefixPaths;
    }


    /// Update cache with given paths. This could overwrite existing entry's.
    ///
    /// @param paths The paths to update to the cache
    private void UpdateCache(List<List<VertexID>> paths){
        for(List<VertexID> path : paths){
            VertexID source = path.getFirst();
            VertexID target = path.getLast();

            cache.put(
                    new cacheEntryKey(source, target),
                    path
            );
        }
    }

    /// Print the current cache to {@code System.out}.
    /// <p>
    /// Used for debugging.
    private void printCache(){
        for(var entry : cache.entrySet()){
            cacheEntryKey key = entry.getKey();
            List<VertexID> value = entry.getValue();

            System.out.print("From: [" + key.source() + "] To: [" + key.target() +  "] Path: ");

            for(VertexID vID : value){
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
    /// @return The shortest path from {@code source} to {@code target} in {@code graph}.
    private List<VertexID> dijkstraAlgorithm(Graph graph, VertexID source, VertexID target){
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

        return extractPathFromParentArray(prev, target);
    }

    /// Extract the path from source to target by walking through it. Prefixing every step so it has the right order.
    /// @param previous The parent array where the index is the vertex and the value the previous vertex on the path
    /// @param target The ending vertex of the path
    /// @return A path that is the result of walking through {@code previous} from target to source
    private List<VertexID> extractPathFromParentArray(VertexID[] previous, VertexID target){
        List<VertexID> steps = new ArrayList<>();

        VertexID cur = target;
        while(cur != null){
            steps.addFirst(cur);

            cur = previous[cur.ordinal()];
        }

        return steps;
    }
}
