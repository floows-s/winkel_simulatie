package objects.graph;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import javafx.adapters.ColorAdapter;
import javafx.scene.paint.Color;
import objects.graph.Vertex.Vertex;
import utilities.JsonFileParser;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


public class GraphData {
    private Set<Vertex> vertices;

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

        GraphData data = JsonFileParser.loadObjectFromFile(jsonFileUri, GraphData.class, gson);

        // Normalize edge weights to 1 for edges where the weight is 0. (i.e. not given in JSON)
        return data.normalizeEdgeWeights();
    }

    /// Replaces every edge weight lower than 1 with 1.
    /// @return this {@code GraphData}, with normalized edge weights
    private GraphData normalizeEdgeWeights(){
        List<Vertex> normalizedVertices = new ArrayList<>(vertices);

        // Replace weights which are less than 1, with 1
        normalizedVertices.replaceAll((v) -> {
            List<Edge> normalizedEdges = v.edges().stream().map(
                    (e)-> e.weight() < 1 ? new Edge(e.to(), 1) : e
            ).toList();

            return new Vertex(v.id(), normalizedEdges, v.location());
        });

        this.vertices = new HashSet<>(normalizedVertices);

        return this;
    }

    /// @return deep-copy of vertices.
    public Set<Vertex> getVertices() {
        return vertices.stream().map(Vertex::new).collect(Collectors.toSet());
    }
}
