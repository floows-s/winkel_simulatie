package objects.customer.states;

import core.Simulation;
import objects.customer.Customer;

public interface CustomerState {
    void update(Customer c, double delta, long now, Simulation s);
}
