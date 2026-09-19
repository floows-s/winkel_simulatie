package utilities;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.NoSuchFileException;

public class jsonFileParser {

    /// Load object of type T from JSON file.
    /// @param fileURI Path to the JSON file with serialized object.
    /// @param typeClass Class of type T
    /// @return The parsed object of type T filled with data from the given JSON file. Or NULL if there was an error.
    /// @param <T> Type of object
    public static <T> T loadObjectFromFile(String fileURI, Class<T> typeClass){
        // Read file
        String file;

        try(InputStream i = jsonFileParser.class.getResourceAsStream(fileURI)){
            if(i == null){
                System.out.println("Error: Couldn't load " + typeClass.getName() + ". Failed to get resource.");
                return null;
            }

            file = new String(i.readAllBytes());
        }catch(NoSuchFileException ex){
            System.out.println("Error: Couldn't load " + typeClass.getName() + ". JSON file not found. (URI: " + fileURI + ")");
            return null;
        }catch(IOException ex){
            System.out.println("Error: Couldn't load " + typeClass.getName() + ". Failed to read file. Exception: " + ex);
            return null;
        }


        // Deserialize
        T result;

        try{
            Gson gson = new Gson();
            result = gson.fromJson(file, typeClass);
        }catch(JsonSyntaxException ex){
            System.out.println("Error: Invalid JSON data. (URI: " + fileURI + ")");
            return null;
        }

        return result;
    }
}
