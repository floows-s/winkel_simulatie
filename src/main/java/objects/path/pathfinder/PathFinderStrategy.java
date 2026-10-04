package objects.path.pathfinder;

public interface PathFinderStrategy<T, P> {
    P find(T from, T to); // TODO: remove redundant generic types T and P, we only use vertexID in this project and P could be ImmutablePath????
}
