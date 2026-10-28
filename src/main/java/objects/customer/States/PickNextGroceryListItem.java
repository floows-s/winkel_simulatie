package objects.customer.States;

import core.Simulation;
import core.logger.Logger;
import core.logger.TagLogger;
import objects.customer.Customer;
import objects.groceryList.GroceryList;
import objects.groceryList.GroceryListItem;
import objects.product.ProductType;

public class PickNextGroceryListItem implements CustomerState {

    Logger log = new TagLogger(PickNextGroceryListItem.class.getSimpleName());

    @Override
    public void update(Customer c, double delta, long now, Simulation s) {
        GroceryList groceryList = c.getGroceryList();
        GroceryListItem groceryItem = groceryList.getNext();
        if(groceryItem == null){
            log.logInfo("Grocery list done! Removing customer from game.");
            s.game.removeObject(c);
        }

        log.logInfo("Next product to get: [" + groceryItem.product() + "]");

        CustomerState newState;
        if(groceryItem.location() == null){
            newState = new LookupProductAtKiosk(groceryItem.product());
        }else{
            newState = new GrabProductFromShelf(
                    groceryItem.product(),
                    groceryItem.location()
            );
        }

        c.setState(newState);
    }
}
