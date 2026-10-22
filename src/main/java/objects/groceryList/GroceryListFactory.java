package objects.groceryList;

import core.Simulation;
import objects.graph.Vertex.VertexID;
import objects.product.Product;
import objects.product.ProductFactory;
import objects.product.ProductName;
import objects.shelf.ShelfManager;
import utilities.Randomizer;

import java.util.ArrayList;
import java.util.List;

public class GroceryListFactory {

    public static final int MAX_AMOUNT_OF_GROCERY_ENTRIES = 10;

    public static GroceryList createGroceryList(int amountOfGroceries, ShelfManager shelfManager){
        if(amountOfGroceries > MAX_AMOUNT_OF_GROCERY_ENTRIES){
            amountOfGroceries = MAX_AMOUNT_OF_GROCERY_ENTRIES;
        }

        List<GroceryListEntry> groceries = new ArrayList<>();

        for(int i = 0; i < amountOfGroceries; i++){
            ProductName product = ProductFactory.getRandomProductName();
            VertexID location = null;

            // Decide if include location
            boolean includeProductLocation = Randomizer.nextBoolean();
            if(includeProductLocation){
                location = shelfManager.getAccessPointOfShelfThatHasProduct(product);
            }

            // Create entry
            GroceryListEntry entry = new GroceryListEntry(
                    product,
                    location, // Note: the location is randomly included. But if no shelf has the product it will still be null.
                    false
            );

            groceries.add(entry);
        }


        return new GroceryList(groceries);
    }
}
