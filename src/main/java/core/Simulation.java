package core;

import objects.customer.Customer;
import objects.shelf.Shelf;
import objects.shelf.ShelfData;
import utilities.Randomizer;

import java.nio.file.Path;
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

        ShelfData sd = ShelfData.fromJson(Path.of("/json", "shelfs", "shelf-1.json").toString());
        Shelf s = new Shelf(sd);

        game.addObject(s);
    }


    @Override
    public void update(double delta, long now) {

    }
}
