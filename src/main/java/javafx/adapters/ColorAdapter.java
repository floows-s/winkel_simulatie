package javafx.adapters;

import com.google.gson.*;
import javafx.scene.paint.Color;
import java.lang.reflect.Type;


public class ColorAdapter implements JsonSerializer<Color>, JsonDeserializer<Color> {
    @Override
    public JsonElement serialize(Color c, Type t, JsonSerializationContext ctx) {
        return new JsonPrimitive(c.toString()); // for example: "0xff0000ff"
    }

    @Override
    public Color deserialize(JsonElement json, Type t, JsonDeserializationContext ctx) {
        return Color.web(json.getAsString());
    }
}
