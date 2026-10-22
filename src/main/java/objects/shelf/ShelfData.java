package objects.shelf;


import core.Point;
import objects.graph.Vertex.VertexID;
import utilities.JsonFileParser;


public class ShelfData {
    private Point position;
    private Shelf.ShelfType type;

    /// The Vertex that is close to the shelf. Only when standing on this vertex, will it be logical to interact with the shelf by a character, for example a customer.
    private VertexID accessPoint;

    public ShelfData(Point position){
        this.position = position;
    }

    /// Load ShelfData from JSON file.
    /// @param jsonFileUri Path to the JSON file with serialized ShelfData.
    /// @return The parsed ShelfData filled with data from the given JSON file. Or NULL if there was an error.
    public static ShelfData fromJson(String jsonFileUri){
        return JsonFileParser.loadObjectFromFile(jsonFileUri, ShelfData.class);
    }


    public float getX() {
        return position.x();
    }

    public float getY() {
        return position.y();
    }

    public Shelf.ShelfType getType() {
        return type;
    }

    /// The Vertex that is close to the shelf. Only when standing on this vertex, will it be logical to interact with the shelf by a character, for example a customer.
    public VertexID getAccessPoint() {
        return accessPoint;
    }
}