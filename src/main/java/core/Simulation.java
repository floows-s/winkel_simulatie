package core;

import core.logger.Logger;
import core.logger.TagLogger;
import core.orientation.Coordinates;
import core.timer.IntervalTimer;
import objects.FloatingText.FloatingNumber;
import objects.StockManager;
import objects.customer.Customer;
import objects.customer.CustomerFactory;
import objects.graph.Vertex.Vertex;
import objects.graph.Vertex.VertexID;
import objects.groceryList.GroceryListFactory;
import objects.lookupKioskStand.LookupKioskStand;
import objects.product.Product;
import objects.product.ProductType;
import objects.register.Register;
import objects.graph.*;
import objects.path.pathfinder.dijkstracache.DijkstraCachePathFinderStrategy;
import objects.shelf.ShelfManager;
import objects.background.BackgroundLoader;
import objects.stats.Stats;
import objects.stocker.Stocker;
import objects.truck.Truck;
import utilities.Randomizer;

public class Simulation implements Updatable{
    // ==== Variables ====

    // Constants
    private final int MAX_CUSTOMER_COUNT = 10;

    private final Game game;
    private Graph graph;
    private IntervalTimer customerSpawnTimer = new IntervalTimer(0, this::spawnNewCustomer);

    private int woodCount       = 0;
    private int metalCount      = 0;
    private int freezerCount    = 0;
    private int customerCount   = 0;

    // Managers
    private ShelfManager shelfManager;
    private final StockManager stockManager = new StockManager();

    // Factories
    private GroceryListFactory groceryListFactory;
    private CustomerFactory customerFactory;

    // Objects
    private LookupKioskStand lookupKioskStand;
    private Register register1;
    private Register register2;
    private Truck truck;
    private Stocker stocker;

    private final Logger log = new TagLogger(this.getClass().getSimpleName());

    // ==== constructor ====
    Simulation(Game game){
        this.game = game;
        setup();
    }


    private void setup(){
        // add background to the game as a gameObject
        game.addObject(new BackgroundLoader());

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
                s.addItem(new Product(productType));
            }
        });

        // add the text
        game.addObject(priceTotal);
        game.addObject(productTotal);

        // Create graph
        GraphData graphData = GraphData.fromJson("/json/graph/graph.json");

        graph = new Graph(
                graphData,
                new DijkstraCachePathFinderStrategy()
        );

        game.addObject(graph);

        // create register
        Vertex register1EndOfQueue = graph.get(VertexID.REGISTER_1_QUEUE_END);
        Vertex register2EndOfQueue = graph.get(VertexID.REGISTER_2_QUEUE_END);
        register1 = new Register(738, 531, VertexID.REGISTER_1_QUEUE_START, register1EndOfQueue);
        register2 = new Register(590, 531, VertexID.REGISTER_2_QUEUE_START, register2EndOfQueue);
        game.addObject(register1);
        game.addObject(register2);

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
        stockManager.ChangeProductStock(ProductType.BREAD, -40);


        // checks if something is lower than half of the stock if so spawn a truck
        truck = new Truck();
        game.addObject(truck);
        truck.SpawnTruck(stockManager.GetRestockOrder());

        // Create stocker
        stocker = new Stocker(graph.get(VertexID.STOCKROOM_MIDDLE));
        game.addObject(stocker);
//        game.addObject(
//                new Customer(
//                        graph.get(VertexID.SPAWN_POINT),
//                        new GroceryList(
//                                List.of(new GroceryListItem(ProductType.BREAD, null), new GroceryListItem(ProductType.APPLE, null), new GroceryListItem(ProductType.MEAT, null))
//                        )
//                )
//        );
    }

    public void spawnNewCustomer(){
        if(customerCount >= MAX_CUSTOMER_COUNT) return;

        log.logInfo("Spawning in new customer!");
        game.addObject(
                customerFactory.create()
        );
        customerCount++;
    }


    @Override
    public void update(double delta, long now, Simulation s) {
        customerSpawnTimer.tick(now);
    }

    public LookupKioskStand getLookupKioskStand() {
        return lookupKioskStand;
    }

    public Register getRegisterToCheckout(){
        int register = Randomizer.nextInt(1, 3);

        return register == 1 ? register1 : register2;
    }

    /// Removes given customer from the game.
    /// @param c to despawn
    public void despawnCustomer(Customer c){
        game.removeObject(c);
        customerCount--;
    }

    public StockManager getStockManager() {
        return stockManager;
    }

    public ShelfManager getShelfManager() {
        return shelfManager;
    }

    public Graph getGraph() {
        return graph;
    }

    public Truck getTruck() {
        return truck;
    }
}
