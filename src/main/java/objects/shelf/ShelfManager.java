package objects.shelf;

import core.Game;
import core.logger.Logger;
import core.logger.TagLogger;
import objects.graph.Vertex.VertexID;
import objects.product.ProductName;
import utilities.JsonFileParser;

import java.util.*;
import java.util.function.Consumer;

public class ShelfManager {
    private final Map<VertexID, Shelf> shelves = new HashMap<>();
    private final Logger log = new TagLogger(ShelfManager.class.getSimpleName());

    /// Create ShelfManager. Loads shelves from the given json file. Note that the json file should contain an ARRAY of {@link ShelfData} objects.
    /// @param jsonFileURI to load shelves from
    public ShelfManager(String jsonFileURI, Game game){
        load(jsonFileURI, game);
    }

    public ShelfManager(List<ShelfData> data, Game game){
        load(data, game);
    }

    /// Load shelves from the given json file and add them to the given game. Note that the json file should contain an ARRAY of {@link ShelfData} objects.
    /// @param jsonFileURI to load shelves from
    /// @param game to add shelves to
    public void load(String jsonFileURI, Game game){
        ShelfData[] data = JsonFileParser.loadObjectFromFile(jsonFileURI, ShelfData[].class);
        if(data == null){
            log.logError("Failed to load json data. Given file URI: " + jsonFileURI);
            return;
        }

        load(List.of(data), game);
    }

    /// Load shelves from given data and add them to the given game.
    /// @param data to load shelves from
    /// @param game to add shelves to
    public void load(List<ShelfData> data, Game game){
        if(data == null){
            log.logError("Failed to load json data. Given data is null.");
            return;
        }

        // Create shelf objects
        List<Shelf> createdShelves = createShelves(data);

        // Map shelf to accessPoint
        mapShelfToAccessPoint(createdShelves, this.shelves);

        // Add shelves to game
        addShelvesToGame(createdShelves, game);
    }

    private List<Shelf> createShelves(List<ShelfData> data){
        List<Shelf> shelves = new ArrayList<>(data.size());

        for(ShelfData d : data){
            shelves.add(new Shelf(d));
        }

        return shelves;
    }

    private void addShelvesToGame(List<Shelf> shelves, Game game){
        for(Shelf s : shelves){
            game.addObject(s);
        }
    }

    private void mapShelfToAccessPoint(List<Shelf> shelves, Map<VertexID, Shelf> map){
        for(Shelf s : shelves){
            map.put(
                    s.getAccessPoint(),
                    s
            );
        }
    }

    /// Gets the shelf that correspond with the given VertexID.
    /// @param accessPoint of the shelf to retrief.
    /// @return The shelf that corresponds with the given {@code accessPoint}, or null if non is found
    public Shelf getShelf(VertexID accessPoint){
        Shelf foundShelf = shelves.get(accessPoint);

        if(foundShelf == null){
            log.logError("Failed to get shelf. No shelf is at given VertexID [" + accessPoint + "]");
            return null;
        }

        return foundShelf;
    }

    /// Search for a shelf that has the given product, and return the {@code VertexID} of the access point for the shelf.
    /// @param product to search for
    /// @return The access point of the found shelf, or null if there is no shelf found
    public VertexID getAccessPointOfShelfThatHasProduct(ProductName product){
        for(var entry : shelves.entrySet()){
            VertexID accessPoint = entry.getKey();
            Shelf s = entry.getValue();

            if(s.hasProduct(product)){
                return accessPoint;
            }
        }

        return null;
    }

    /// Execute an action on every shelf.
    /// @param action the action to be executed
    public void forEach(Consumer<Shelf> action){
        shelves.forEach((_, shelf) -> action.accept(shelf));
    }
}
