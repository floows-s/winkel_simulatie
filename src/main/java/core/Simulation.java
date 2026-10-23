package core;

import core.logger.Logger;
import core.logger.TagLogger;
import core.timer.IntervalTimer;
import objects.FloatingText.FloatingNumber;
import objects.box.Box;
import objects.customer.Customer;
import objects.customer.CustomerFactory;
import objects.graph.Vertex.Vertex;
import objects.graph.Vertex.VertexID;
import objects.groceryList.GroceryList;
import objects.groceryList.GroceryListFactory;
import objects.path.Path;
import objects.product.Product;
import objects.product.ProductFactory;
import objects.product.ProductName;
import objects.register.Register;
import objects.graph.*;
import objects.path.pathfinder.dijkstracache.DijkstraCachePathFinderStrategy;
import objects.shelf.Shelf;
import objects.shelf.ShelfData;
import objects.shelf.ShelfManager;
import utilities.JsonFileParser;
import objects.background.BackgroundLoader;

import java.lang.reflect.Array;
import java.util.List;

public class Simulation implements Updatable{
    private final Game game;
    public Graph graph;

    // Managers
    public ShelfManager shelfManager;

    // Factories
    public GroceryListFactory groceryListFactory;
    public CustomerFactory customerFactory;

    private final Logger log = new TagLogger(this.getClass().getSimpleName());

    Simulation(Game game){
        this.game = game;
        setup();
    }


    private void setup(){
        // add background to the game as a gameObject
        game.addObject(new BackgroundLoader());

        // create register
        Register register1 = new Register(590, 531);
        Register register2 = new Register(738, 531);

        // create text test
        FloatingNumber priceTotal = new FloatingNumber("100€", 637, 589);

        // Create shelf manager
        shelfManager = new ShelfManager("/json/shelfs/shelfs.json", game);

        // Fill shelves with "dummy" products for testing
        shelfManager.forEach((s) -> {
            for(int i = 0; i < 9; i ++){

                if(s.getType() == Shelf.ShelfType.WOOD){
                    s.AddProduct(ProductFactory.getProduct(ProductName.BREAD));
                }

                if(s.getType() == Shelf.ShelfType.METAL){
                    s.AddProduct(ProductFactory.getProduct(ProductName.PIZZA));
                }

                if(s.getType() == Shelf.ShelfType.COOL){
                    s.AddProduct(ProductFactory.getProduct(ProductName.MEAT));
                }

                if(s.getType() == Shelf.ShelfType.STORAGE){
                    s.AddProduct(new Box());
                }
            }
        });

        // Create factories
        groceryListFactory = new GroceryListFactory(shelfManager);
        customerFactory = new CustomerFactory(groceryListFactory);

        // add the register
        game.addObject(register1);
        game.addObject(register2);
        game.addObject(priceTotal);

        // Create graph
        GraphData graphData = GraphData.fromJson("/json/graph/graph.json");

        graph = new Graph(
                graphData,
                new DijkstraCachePathFinderStrategy(false)
        );

        game.addObject(graph);


        Point spawnPointLocation = graph.get(VertexID.SPAWN_POINT).location();
        Customer c = customerFactory.create(spawnPointLocation);
        game.addObject(c);
    }


    private IntervalTimer customerSpawnTimer = new IntervalTimer(2000, this::spawnNewCustomer); // TODO: remove, right now its just for demo purposes.

    public void spawnNewCustomer(){ // TODO: maybe revisit, right now its just for demo purposes.
        log.logInfo("Spawning in new customer!");

        Point spawnPoint = graph.get(VertexID.SPAWN_POINT).location();
        game.addObject(
                customerFactory.create(spawnPoint)
        );
    }

    @Override
    public void update(double delta, long now, Simulation s) {
        customerSpawnTimer.tick(now); // TODO: remove, right now its just for demo purposes.
    }
}
