package core;

import core.logger.Logger;
import core.logger.TagLogger;
import core.orientation.Coordinates;
import core.timer.IntervalTimer;
import objects.FloatingText.FloatingNumber;
import objects.StockManager;
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
import objects.stats.Stats;
import objects.truck.Truck;

import java.util.ArrayList;
import java.util.List;

public class Simulation implements Updatable{
    public final Game game;

    // ==== Variables ====
    private final Game game;
    public Graph graph;

    // keep track how many shelves are filled
    private int woodCount       = 0;
    private int metalCount      = 0;
    private int freezerCount    = 0;

    // TEST for checking out
    private List<Product> productsList = new ArrayList<>();

    // Managers
    public ShelfManager shelfManager;
    public final StockManager stockManager = new StockManager();

    // Factories
    public GroceryListFactory groceryListFactory;
    public CustomerFactory customerFactory;

    // Objects
    private LookupKioskStand lookupKioskStand;

    private final Logger log = new TagLogger(this.getClass().getSimpleName());

    // ==== constructor ====
    Simulation(Game game){
        this.game = game;
        setup();
    }


    private void setup(){
        // add background to the game as a gameObject
        game.addObject(new BackgroundLoader());

        // TEST PRODUCT LIST FOR REGISTER
        productsList.add(new Product(ProductType.APPLE));
        productsList.add(new Product(ProductType.PIZZA));
        productsList.add(new Product(ProductType.BREAD));

        // create register
        Register register1 = new Register(590, 531);
        Register register2 = new Register(738, 531);

        // create test text
        FloatingNumber priceTotal   = new FloatingNumber(() ->"money earned = " + String.valueOf(Stats.GetMoneyEarned() + " €"), 1153, 52);
        FloatingNumber productTotal = new FloatingNumber(()->"products = " + String.valueOf(Stats.GetProductsSold()), 1153, 72);

        // Create shelf manager
        shelfManager = new ShelfManager("/json/shelfs/shelfs.json", game);

        // Fill shelves with products according to plan
        shelfManager.forEach((s) -> {

            ProductType productType = null;
            switch (s.getType()){

                case WOOD -> { // load 6 shelves with apples and 6 with bread
                    productType = woodCount < 6 ? ProductType.APPLE : ProductType.BREAD;
                    woodCount ++;

                }
                case METAL -> { // load 3 shelves with meat and 3 with milk
                    productType = metalCount < 3 ? ProductType.MEAT : ProductType.MILK;
                    metalCount ++;

                }
                case COOL -> {  // load 3 shelves with pizza and 3 shelves with icescream
                    productType = freezerCount < 3 ? ProductType.PIZZA : ProductType.ICE_SCREAM;
                    freezerCount ++;
                }
                // dont add anything to storage
                case STORAGE -> {return;}
            }

            for(int i = 0; i < 9; i ++){
                s.AddProduct(new Product(productType));
            }
        });


        // add the register
        game.addObject(register1);
        game.addObject(register2);
        // add the text
        game.addObject(priceTotal);
        game.addObject(productTotal);

        // test if checkout works
        register1.CheckOut(productsList);
        register2.CheckOut(productsList);

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
        lookupKioskStand = new LookupKioskStand(
                new Coordinates(696, 26),
                shelfManager
        );
        game.addObject(lookupKioskStand);

        // add stock manager and remove stock
        stockManager.ChangeProductStock(ProductType.APPLE, -40);
        // checks if something is lower than half of the stock if so spawn a truck
        Truck truck = new Truck();
        game.addObject(truck);

        truck.SpawnTruck(stockManager.GetRestockOrder());
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

    public LookupKioskStand getLookupKioskStand() {
        return lookupKioskStand;
    }
}
