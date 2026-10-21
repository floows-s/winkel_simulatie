package objects.graph;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import javafx.adapters.ColorAdapter;
import javafx.scene.paint.Color;
import objects.graph.Vertex.Vertex;
import utilities.JsonFileParser;

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
        // Note: To give context the ColorAdapter is (at least) used for Vertex object creation.
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(Color.class, new ColorAdapter())
                .create();

        return JsonFileParser.loadObjectFromFile(jsonFileUri, GraphData.class, gson);
    }

    /// @return deep-copy of vertices.
    public Set<Vertex> getVertices() {
        return vertices.stream().map(Vertex::new).collect(Collectors.toSet());
    }
}
