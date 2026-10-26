package objects.customer.States;

import core.Simulation;
import objects.customer.Customer;

public interface CustomerState {
    void update(Customer customer, double delta, long now, Simulation s);
}
