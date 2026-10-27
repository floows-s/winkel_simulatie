package objects.groceryList;

import objects.graph.Vertex.VertexID;
import objects.product.Product;
import objects.product.ProductType;
import objects.shelf.ShelfManager;
import utilities.Randomizer;

import java.util.ArrayList;
import java.util.List;

public class GroceryListFactory {
    public static final int MAX_AMOUNT_OF_GROCERY_ITEMS = 10;
    public static final float PERCENTAGE_CHANGE_ON_INCLUDING_PRODUCT_LOCATION = 50;

    private ShelfManager shelfManager;

    public GroceryListFactory(ShelfManager shelfManager){
        this.shelfManager = shelfManager;
    }

    public GroceryList create(int amountOfGroceries){
        if(amountOfGroceries > MAX_AMOUNT_OF_GROCERY_ITEMS){
            amountOfGroceries = MAX_AMOUNT_OF_GROCERY_ITEMS;
        }

        List<GroceryListItem> groceries = new ArrayList<>();

        for(int i = 0; i < amountOfGroceries; i++){
            int random = Randomizer.nextInt(0, ProductType.values().length);

            ProductType product = ProductType.values()[random];
            VertexID location = null;

            // Decide if include location
            boolean includeProductLocation = Randomizer.nextBoolean(PERCENTAGE_CHANGE_ON_INCLUDING_PRODUCT_LOCATION);
            if(includeProductLocation){
                location = shelfManager.getAccessPointOfShelfThatHasProduct(product);
            }

            // Create item
            GroceryListItem item = new GroceryListItem(
                    product,
                    location, // Note: the location is randomly included. BUT if no shelf has the product it will still be null.
                    false
            );

            groceries.add(item);
        }

        return new GroceryList(groceries);
    }
}
