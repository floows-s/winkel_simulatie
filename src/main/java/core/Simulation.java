package core;

import objects.box.box;
import objects.product.Product;
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
        box box = new box();

        // create shelf and products
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

            s.AddProduct(box);
            s.AddProduct(box);
            s.AddProduct(box);
            s.AddProduct(box);
            s.AddProduct(box);
            s.AddProduct(box);
            s.AddProduct(box);
            s.AddProduct(box);
            s.AddProduct(box);


            game.addObject(s);
        }
    }


    @Override
    public void update(double delta, long now) {

    }
}
