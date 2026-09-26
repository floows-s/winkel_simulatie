package objects.graph;

import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class Path {

    private final List<Vertex> vertices;
    private final ListIterator<Vertex> iterator;
    private Vertex current = null;

    public Path(List<Vertex> vertices){
        this.vertices = vertices;
        this.iterator = this.vertices.listIterator();
    }

    public Vertex next(){
        this.current = this.iterator.next();
        return this.current;
    }

    public Vertex current(){
        return this.current; // Todo: clone?
    }

    public Vertex previous(){
        this.current = this.iterator.previous();
        return this.current;
    }
}
