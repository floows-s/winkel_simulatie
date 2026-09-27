package objects.shelf;


import core.Point;
import utilities.JsonFileParser;


public class ShelfData {
    private float x;
    private float y;

    public ShelfData(Point position){
        x = position.x();
        y = position.y();
    }

    /// Load ShelfData from JSON file.
    /// @param jsonFileUri Path to the JSON file with serialized ShelfData.
    /// @return The parsed ShelfData filled with data from the given JSON file. Or NULL if there was an error.
    public static ShelfData fromJson(String jsonFileUri){
        return JsonFileParser.loadObjectFromFile(jsonFileUri, ShelfData.class);
    }


    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }
}
