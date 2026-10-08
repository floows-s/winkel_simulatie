package objects.path;

import objects.graph.VertexID;

import java.util.*;
import java.util.stream.Collectors;

//TODO: def update this and make more clear, make more queue like, Like get next step, or something like that
public class Path {

    protected final List<VertexID> vertices;
    protected final ListIterator<VertexID> iterator;
    protected VertexID current;

    public Path(VertexID... vertices) {
        this.vertices = Arrays.stream(vertices).toList();
        this.iterator = this.vertices.listIterator();
    }

    public Path(List<VertexID> vertices){
        this.vertices = List.copyOf(vertices);
        this.iterator = this.vertices.listIterator();
    }



    /// Check if there is no next element in the path.
    /// @return if path is finished.
    public boolean isFinished(){
        return !this.iterator.hasNext() && this.current == null;
    }

    /// Sets the current VertexID on the path to the next one and returns the ID.
    /// If there is no next it will return NULL.
    /// @return Next VertexID or NULL
    public VertexID next(){
        try{
            this.current = this.iterator.next();
        }catch(NoSuchElementException ex){
            this.current = null;
        }

        return this.current;
    }

    /// Returns the current VertexID on the path.
    /// Current is set by next() and previous().
    /// @return The current VertexID
    public VertexID current(){
        return this.current;
    }

    /// Returns the current VertexID on the path, advancing to the next one first if there is no current.
    /// If current is already set, it is returned unchanged. Otherwise, this behaves like next().
    /// @return Current or next VertexID, or NULL if there is none
    public VertexID currentOrNext(){
        if(this.current == null){
            return this.next();
        }

        return this.current;
    }

    /// Sets the current VertexID on the path to the previous one and returns the ID.
    /// If there is no previous it will return NULL.
    /// @return Previous VertexID or NULL
    public VertexID previous(){
        try{
            this.current = this.iterator.previous();
        }catch(NoSuchElementException ex){
            this.current = null;
        }

        return this.current;
    }

    public boolean hasPrevious(){
        return this.iterator.hasPrevious();
    }

    public List<VertexID> toList(){
        return List.copyOf(this.vertices); // Note: VertexID is an enum. No need to deep copy.
    }
}