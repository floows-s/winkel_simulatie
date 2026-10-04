package objects.path;

import objects.graph.VertexID;

import java.util.Arrays;
import java.util.List;

public class MutablePath extends Path{
    public MutablePath(List<VertexID> vertices) {
        super(vertices);
    }

    public MutablePath(VertexID... vertices) {
        super(Arrays.stream(vertices).toList());
    }

    public MutablePath(MutablePath m){
        super(List.copyOf(m.vertices)); // TODO: deep copy
    }

    public MutablePath add(VertexID v){
        this.vertices.add(v);

        return this;
    }

    public MutablePath remove(int index){
        this.vertices.remove(index);

        return this;
    }
}
