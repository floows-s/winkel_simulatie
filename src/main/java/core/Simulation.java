package core;

import objects.customer.Customer;
import utilities.Randomizer;

import java.util.ArrayList;
import java.util.List;

public class Simulation implements Updatable{
    private final Game game;

    Simulation(Game game){
        this.game = game;
        setup();
    }

    /*
     * TODO:
     *  Nadenken over: wat gaat simulatie allemaal doen? Wat is zijn verantwoordelijkheid?
     *  Bijv vakkenvullers naar juiste schap sturen die gevuld moet worden?
     *  Nieuwe customers aanmaken?
     *  Stats bijhouden?
     *
     *  Want customer stuurt zich zelf aan via een soort statemachine. Niet persee door de simulatie
     *
     * */

    private void setup(){

        Customer c1 = new Customer(0, 0);
        game.addObject(c1);
        cs.add(c1);

        Customer c2 = new Customer(0, 300);
        game.addObject(c2);
        cs.add(c2);


        Customer c3 = new Customer(0, 500);
        game.addObject(c3);
        cs.add(c3);

    }


    private long start_time = 0;
    private List<Customer> cs = new ArrayList<Customer>();

    @Override
    public void update(double delta, long now) {
        if(start_time == 0){
            start_time = now;
        }

        double passed_time_sec = (now - start_time) / 1E9;

        float threshold = 2;
        int divide = (int)threshold * cs.size();
        double time_in_sec = now / 1E9;
        if(passed_time_sec > threshold){
            game.removeObject(cs.get((divide/(int)time_in_sec) - 1));
            start_time = now;
        }

        System.out.println("Passed time: " + passed_time_sec);
    }
}
