package core;

import core.logger.Logger;
import core.logger.TagLogger;
import core.orientation.Coordinates;
import core.timer.IntervalTimer;
import objects.FloatingText.FloatingNumber;
import objects.customer.CustomerFactory;
import objects.graph.Vertex.VertexID;
import objects.groceryList.GroceryListFactory;
import objects.LookupKioskStand.LookupKioskStand;
import objects.product.Product;
import objects.product.ProductType;
import objects.register.Register;
import objects.graph.*;
import objects.path.pathfinder.dijkstracache.DijkstraCachePathFinderStrategy;
import objects.shelf.Shelf;
import objects.shelf.ShelfManager;
import objects.background.BackgroundLoader;

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
                    s.AddProduct(new Product(ProductType.APPLE));
                }

                if(s.getType() == Shelf.ShelfType.METAL){
                    s.AddProduct(new Product(ProductType.MEAT));
                }

                if(s.getType() == Shelf.ShelfType.COOL){
                    s.AddProduct(new Product(ProductType.PIZZA));
                }
            }
        });

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

        // Create factories
        groceryListFactory = new GroceryListFactory(shelfManager);
        customerFactory = new CustomerFactory(groceryListFactory, graph.get(VertexID.SPAWN_POINT));

        // Add lookup kiosk stand
        LookupKioskStand lookupKioskStand = new LookupKioskStand(
                new Coordinates(696, 26),
                shelfManager
        );
        game.addObject(lookupKioskStand);
    }


    private IntervalTimer customerSpawnTimer = new IntervalTimer(2000, this::spawnNewCustomer); // TODO: remove, right now its just for demo purposes.
    public void spawnNewCustomer(){ // TODO: maybe revisit, right now its just for demo purposes.
        log.logInfo("Spawning in new customer!");

        game.addObject(
                customerFactory.create()
        );
    }

    @Override
    public void update(double delta, long now, Simulation s) {
        customerSpawnTimer.tick(now); // TODO: remove, right now its just for demo purposes.
    }
}
