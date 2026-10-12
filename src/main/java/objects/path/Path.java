package objects.path;

import objects.graph.VertexID;

import java.util.*;

/// An immutable list of steps.
/// <p>
/// Allowing to iterate over the steps, remembering the current step.
public class Path {

    protected final List<VertexID> steps;
    protected final ListIterator<VertexID> iterator;
    protected VertexID current;

    /// Construct a Path object which follows the order of the given {@link VertexID}'s.
    /// <p>
    /// Creates deep copy of given steps.
    /// @param steps To take in the path.
    public Path(VertexID... steps) {
        this.steps = Arrays.stream(steps).toList();
        this.iterator = this.steps.listIterator();
    }

    /// Construct a Path object which follows the order of the given list of {@link VertexID}'s.
    /// <p>
    /// Creates a deep copy of the given list.
    /// @param steps To take in the path.
    public Path(List<VertexID> steps){
        this.steps = List.copyOf(steps);
        this.iterator = this.steps.listIterator();
    }

    /// Constructs a copy of {@code path}.
    /// @param path to copy from
    public Path(Path path){
        this.current = path.current;
        this.steps = List.copyOf(path.steps);
        this.iterator = this.steps.listIterator();
    }



    /// Check if there is no next element in the path.
    /// @return if path is finished.
    public boolean isFinished(){
        return !this.iterator.hasNext() && this.current == null;
    }

    /// Sets the current VertexID on the path to the next one and return the ID.
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


    /// Get the first step of the path.
    /// @return first step of the path
    public VertexID getFirst(){
        return this.steps.getFirst();
    }

    /// Get the last step of the path.
    /// @return last step of the path
    public VertexID getLast(){
        return this.steps.getLast();
    }

    /// @return a list of the steps in the path. Order from beginning to end.
    public List<VertexID> toList(){
        return List.copyOf(this.steps); // Note: VertexID is an enum. No need to deep copy.
    }
}