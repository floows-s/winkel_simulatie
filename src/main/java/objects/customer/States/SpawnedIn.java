package objects.customer.States;

import core.Simulation;
import objects.customer.Customer;

public class SpawnedIn implements CustomerState{
    @Override
    public void update(Customer customer, double delta, long now, Simulation s) {
        customer.setState(new GrabShoppingCart());
    }
}
