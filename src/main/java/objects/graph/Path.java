package objects.graph;

import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;

public class Path {

    private final List<VertexID> vertices;
    private final ListIterator<VertexID> iterator;
    private VertexID current = null;

    public Path(List<VertexID> vertices){
        this.vertices = vertices;
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
}
