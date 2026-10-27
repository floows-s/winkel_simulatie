package objects.storeItems.shelf;


import core.Point;
import utilities.JsonFileParser;


public class ShelfData {
    private final Point position;
    private Shelf.ShelfType type;

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
}