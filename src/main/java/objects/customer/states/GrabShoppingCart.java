package objects.customer.states;

import core.Simulation;
import objects.customer.Customer;
import objects.graph.Vertex.VertexID;
import objects.path.Path;
import objects.shoppingCart.ShoppingCart;

public class GrabShoppingCart implements CustomerState {
    Path path = null;

    @Override
    public void update(Customer c, double delta, long now, Simulation s) {
        // If path has not been set
        if(path == null){
            path = s.getGraph().getShortestPath(c.getCurrentVertexID(), VertexID.SHOPPING_CART_PICKUP);
        }

        if(c.followPath(path, s.getGraph(), delta, now)){
            // If done following path -> customer grab shopping cart
            c.setShoppingCart(new ShoppingCart());

            // Set next state
            c.setState(new PickNextGroceryListItem());
        }
    }
}
