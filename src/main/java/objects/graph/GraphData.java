package objects.graph;


import core.Point;
import utilities.JsonFileParser;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


public class GraphData {
    private final Set<Vertex> vertices;

    public GraphData(Set<Vertex> vertices){
        this.vertices = vertices;
    }

    /// Load GraphData from JSON file.
    /// @param jsonFileUri Path to the JSON file with serialized GraphData.
    /// @return The parsed GraphData filled with data from the given JSON file. Or NULL if there was an error.
    public static GraphData fromJson(String jsonFileUri){
        return JsonFileParser.loadObjectFromFile(jsonFileUri, GraphData.class);
    }

    /// @return deep-copy of vertices.
    public Set<Vertex> getVertices() {
        return vertices.stream().map(Vertex::new).collect(Collectors.toSet());
    }
}
