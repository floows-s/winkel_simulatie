package objects.customer;

import core.orientation.Coordinates;
import objects.groceryList.GroceryListFactory;
import utilities.Randomizer;

public class CustomerFactory {

    private final GroceryListFactory groceryListFactory;
    private final int MINIMAL_AMOUNT_OF_GROCERIES = 1;

    public CustomerFactory(GroceryListFactory groceryListFactory){
        this.groceryListFactory = groceryListFactory;
    }

    public Customer create(Coordinates startPoint){
        // Decide amount of groceries in list
        int amountOfGroceries = Randomizer.nextInt(
                MINIMAL_AMOUNT_OF_GROCERIES,
                GroceryListFactory.MAX_AMOUNT_OF_GROCERY_ITEMS + 1
        );

        return new Customer(
                startPoint,
                groceryListFactory.create(amountOfGroceries)
        );
    }
}
