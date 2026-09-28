package core;

import Product.Product;
import objects.shelf.Shelf;
import objects.shelf.ShelfData;
import utilities.JsonFileParser;

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
        // Shelf's
        ShelfData[] shelfL = JsonFileParser.loadObjectFromFile("/json/shelfs/shelfs.json", ShelfData[].class);
        Product bread = new Product("Bread");
        Product pizza = new Product("Pizza");
        Product meat  = new Product("Meat");

        for(ShelfData d : shelfL){
            Shelf s = new Shelf(d);

            s.AddProduct(bread);
            s.AddProduct(pizza);
            s.AddProduct(meat);

            s.AddProduct(bread);
            s.AddProduct(pizza);
            s.AddProduct(meat);

            s.AddProduct(bread);
            s.AddProduct(pizza);
            s.AddProduct(meat);

            game.addObject(s);
        }

        //



    }



    @Override
    public void update(double delta, long now) {

    }
}
