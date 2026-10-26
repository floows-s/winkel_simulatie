package objects.customer;

import core.orientation.Coordinates;
import objects.graph.Vertex.Vertex;
import objects.graph.Vertex.VertexID;
import objects.groceryList.GroceryListFactory;
import utilities.Randomizer;

public class CustomerFactory {

    private final GroceryListFactory groceryListFactory;
    private final int MINIMAL_AMOUNT_OF_GROCERIES = 1;
    Vertex spawnPoint = null;

    public CustomerFactory(GroceryListFactory groceryListFactory, Vertex spawnPoint){
        this.groceryListFactory = groceryListFactory;
        this.spawnPoint = spawnPoint;
    }

    public Customer create(){
        // Decide amount of groceries in list
        int amountOfGroceries = Randomizer.nextInt(
                MINIMAL_AMOUNT_OF_GROCERIES,
                GroceryListFactory.MAX_AMOUNT_OF_GROCERY_ITEMS + 1
        );

        return new Customer(
                spawnPoint,
                groceryListFactory.create(amountOfGroceries)
        );
    }

    public Customer create(Vertex startVertex){
        // Decide amount of groceries in list
        int amountOfGroceries = Randomizer.nextInt(
                MINIMAL_AMOUNT_OF_GROCERIES,
                GroceryListFactory.MAX_AMOUNT_OF_GROCERY_ITEMS + 1
        );

        return new Customer(
                startVertex,
                groceryListFactory.create(amountOfGroceries)
        );
    }
}
