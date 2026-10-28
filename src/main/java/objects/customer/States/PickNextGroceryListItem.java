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
        GroceryListItem nextGroceryItem = c.getNextGroceryListItem();

        if(nextGroceryItem == null){
            if(c.getShoppingCart().amountOfProducts() == 0){
                c.setState(new ExitStore());
                return;
            }

            c.setState(new CheckoutItemsAtRegister());
            return;
        }

        // Check if product location is known
        CustomerState newState;
        if(nextGroceryItem.location() == null){
            newState = new LookupProductAtKiosk(nextGroceryItem.product());
        }else{
            newState = new GrabProductFromShelf(
                    nextGroceryItem.product(),
                    nextGroceryItem.location()
            );
        }

        c.setState(newState);
    }
}
