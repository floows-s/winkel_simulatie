package objects.stocker.states;

import core.Simulation;
import objects.customer.Customer;
import objects.stocker.Stocker;

public interface StockerState {
    void update(Stocker st, double delta, long now, Simulation s);
}
