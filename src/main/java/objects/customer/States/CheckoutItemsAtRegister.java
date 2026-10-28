package objects.customer.States;

import core.Simulation;
import objects.customer.Customer;
import objects.path.Path;
import objects.register.Register;
import objects.shoppingCart.ShoppingCart;

public class CheckoutItemsAtRegister implements CustomerState {
    Register register = null;
    Path pathToRegister = null;
    boolean isInQueue = false;

    @Override
    public void update(Customer c, double delta, long now, Simulation s) {
        if(isInQueue) return;

        if(register == null){
            register = s.getRegisterToCheckout();
        }

        if(pathToRegister == null){
            pathToRegister = s.graph.getShortestPath(
                    c.getCurrentVertexID(),
                    register.getAccessPoint()
            );
        }

        boolean atDestination = c.followPath(pathToRegister, s.graph, delta, now);
        if(!atDestination) return;

        // Enter register queue
        register.enterQueue(
                c,
                () -> c.setState(new ExitStore()) // Exit store after checkout
        );
        isInQueue = true;

        c.setSpriteToIdle();
    }
}
