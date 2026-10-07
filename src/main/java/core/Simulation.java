package core;

import objects.FloatingText.FloatingNumber;
import objects.box.Box;
import objects.product.Product;
import objects.register.Register;
import objects.shelf.Shelf;
import objects.shelf.ShelfData;
import utilities.JsonFileParser;
import objects.background.BackgroundLoader;

public class Simulation implements Updatable{
    private final Game game;

    Simulation(Game game){
        this.game = game;
        setup();
    }


    private void setup(){
        // add background to the game as a gameObject
        game.addObject(new BackgroundLoader());

        // Shelf's
        ShelfData[] shelfL = JsonFileParser.loadObjectFromFile("/json/shelfs/shelfs.json", ShelfData[].class);

        // create products
        Product bread = new Product("Bread", Shelf.ShelfType.WOOD, 10);
        Product pizza = new Product("Pizza", Shelf.ShelfType.METAL, 10);
        Product meat  = new Product("Meat", Shelf.ShelfType.COOL, 10);
        Box box = new Box();

        // create register
        Register register1 = new Register(590, 531);
        Register register2 = new Register(738, 531);

        // create text test
        FloatingNumber priceTotal = new FloatingNumber("100€", 637, 589);

        // create shelf and products
        for(ShelfData d : shelfL){
            Shelf s = new Shelf(d);
            for(int i = 0; i < 9; i ++){
                s.AddProduct(bread);
                s.AddProduct(pizza);
                s.AddProduct(meat);
                s.AddProduct(box);
            }
            // add shelf to the game
            game.addObject(s);
        }
        // add the register
        game.addObject(register1);
        game.addObject(register2);
        game.addObject(priceTotal);
    }


    @Override
    public void update(double delta, long now) {

    }
}
