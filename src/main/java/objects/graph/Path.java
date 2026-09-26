package objects.graph;

import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class Path {

    private final List<Vertex> vertices;
    private final ListIterator<Vertex> iterator;

    public Path(List<Vertex> vertices){
        this.vertices = vertices;
        this.iterator = this.vertices.listIterator();
    }

    public Vertex next(){
        return this.iterator.next();
    }

    public Vertex previous(){
        return this.iterator.previous();
    }
}
