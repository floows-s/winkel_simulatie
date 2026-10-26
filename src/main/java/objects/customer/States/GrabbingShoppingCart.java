package objects.customer.States;

import core.Simulation;
import objects.customer.Customer;
import objects.graph.Vertex.VertexID;
import objects.path.Path;
import objects.shoppingCart.ShoppingCart;

public class GrabbingShoppingCart implements CustomerState {
    Path path = null;



    @Override
    public void update(Customer c, double delta, long now, Simulation s) {
        // If path has not been set
        if(path == null){
            path = s.graph.getShortestPath(c.getCurrentVertexID(), VertexID.SHOPPING_CART_PICKUP);
        }

        if(c.followPath(path, s.graph, delta, now)){
            // If done following path -> customer grab shopping cart
            c.setShoppingCart(new ShoppingCart());

            // Set next state
            c.setState(new ExploringStore());
        }
    }
}
