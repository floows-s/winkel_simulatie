package objects.customer;

import core.Point;
import objects.groceryList.GroceryListFactory;
import utilities.Randomizer;

public class CustomerFactory {

    private final GroceryListFactory groceryListFactory;

    public CustomerFactory(GroceryListFactory groceryListFactory){
        this.groceryListFactory = groceryListFactory;
    }

    public Customer create(Point startPoint){
        int amountOfGroceries = Randomizer.nextInt(1, GroceryListFactory.MAX_AMOUNT_OF_GROCERY_ENTRIES + 1);

        return new Customer(
                startPoint,
                groceryListFactory.create(amountOfGroceries)
        );
    }
}
