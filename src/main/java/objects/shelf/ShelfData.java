package objects.shelf;


import utilities.jsonFileParser;


public class ShelfData {
    private float x;
    private float y;

    /// Load ShelfData from JSON file.
    /// @param jsonFileUri Path to the JSON file with serialized ShelfData.
    /// @return The parsed ShelfData filled with data from the given JSON file. Or NULL if there was an error.
    public static ShelfData fromJson(String jsonFileUri){
        return jsonFileParser.loadObjectFromFile(jsonFileUri, ShelfData.class);
    }


    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }
}
