package core;

import objects.customer.Customer;

public class Simulation {
    private final Game game;

    Simulation(Game game){
        this.game = game;
    }

    public void Start(){


        Customer c = new Customer(0, game.HEIGHT / 2);

        game.AddObject(c);
    }
}
