package objects.truck;

import core.Renderable;
import core.Simulation;
import core.Updatable;
import javafx.scene.canvas.GraphicsContext;
import objects.StockManager;
import objects.box.Box;
import objects.movement.Movement;
import objects.product.Product;
import objects.product.ProductType;
import javafx.scene.image.Image;

import java.util.ArrayDeque;
import java.util.EnumMap;
import java.util.Queue;

public class Truck implements Renderable, Updatable {
    // ==== VARIABLES ====
    private boolean isTruckSpawned;
    private Image sprite = new Image(getClass().getResource("/images/truck/Truck_Empty.png").toExternalForm());

    // start location animation
    private double x = 23;
    private double y = -300;

    //goal location
    private double targetX;
    private double targetY;

    // moving
    private boolean isMoving    = false;
    private boolean isLeaving   = false;
    private Movement movement   = new Movement(x, y,50);

    // width and height of the sprite
    private final double width  = 84;
    private final double height = 261;

    // automatically returns first available box and null when no box is available
    private final Queue<Box> boxes = new ArrayDeque<>();

    // replenish the current stock to the stock manager
    private EnumMap<ProductType, Integer> restockOrder;
    private boolean stockReplenished = false;

    // ==== FUNCTIONS ====
    /// using the queue system it automatically takes the first box in the queue and if it is null returns null
    /// @return the first box in the queue
    public Box GetBox() {
        return boxes.poll();
    }

    /// adds a box to boxes
    /// @param b Box
    public void AddBox(Box b){
        boxes.add(b);
    }

    /// spawns a truck filled with boxes which are filled with products from the order
    /// @param order a enumMap with product types and the amount needed per product
    public void SpawnTruck(EnumMap<ProductType, Integer> order){
        if(isTruckSpawned || isLeaving) return;

        if(order.isEmpty()) return;

        // save a copy to use for restocking
        this.restockOrder = new EnumMap<>(order);
        // create the order
        for(ProductType type : order.keySet()){
            int orderAmount = order.get(type);
            if (orderAmount > 0){

                Box box = new Box();                // new box to put items in
                int boxCapacity = box.capacity;     // capacity of a box
                Product p = new Product(type);      // current product that needs to be restocked

                // check if the box can be filled
                while(orderAmount > 0){
                    if(orderAmount > boxCapacity){
                        box.AddProduct(p, boxCapacity);
                        AddBox(box);                    // add the box to the Truck
                        box = new Box();                // create a new box
                        orderAmount -= boxCapacity;     // remove the order amount
                    }
                    else
                    {
                        box.AddProduct(p, orderAmount); // add the products
                        AddBox(box);                    // add the box to the truck
                        orderAmount -= orderAmount;
                    }
                }   // while not 0 loop
            }   // if not 0 loop
        }   // for loop ends

        isTruckSpawned = true;
        // move the sprite to the correct spot
        movement.start(x,y,23,64);
        isMoving = true;
    }

    /// removes the truck and changes its appearance
    public void RemoveTruck(){
        if(boxes.isEmpty()){
            // if there are no more boxes change the sprite and move away
            SetSpriteToEmpty();
            movement.start(23, 64, 23, -300);
            isTruckSpawned = false;
            isLeaving = true;
            isMoving = true;
        }
    }

    public boolean isMoving() {
        return isMoving;
    }


    /// replenish the current added products to the stock
    /// @param stockManager the current stock manager inside the simulation
    private void ReplenishStock(StockManager stockManager) {
        for (var entry : restockOrder.entrySet()) {

            ProductType type = entry.getKey();
            int amount = entry.getValue();

            stockManager.ChangeProductStock(type, amount);
            restockOrder = null;
        }
        stockReplenished = true;
    }

    // ==== Sprite setting ====
    ///Set the current sprite to the empty truck
    private void SetSpriteToEmpty(){
        sprite = new Image(getClass().getResource("/images/truck/Truck_Empty.png").toExternalForm());
    }

    /// set the current sprite to the full truck
    private void SetSpriteToFull(){
        sprite = new Image(getClass().getResource("/images/truck/Truck_Full.png").toExternalForm());
    }

    // ==== Interface Renderable ====

    /// renders the sprite of the truck based on the location returned by movement
    /// @param g    the canvas
    @Override
    public void render(GraphicsContext g) {
        if (sprite != null){
            g.drawImage(sprite, movement.getX(), movement.getY(), width, height);
        }
    }

    @Override
    public float getX() {
        return 0;
    }

    @Override
    public float getY() {
        return 0;
    }

    public boolean isReadyToUnload(){
        return !isMoving && isTruckSpawned  && !boxes.isEmpty();
    }

    // ==== interface updatable ====.

    /// checks if the truck is done moving and if the truck can be removed
    /// @param delta    time passed
    /// @param now      time that is now
    /// @param s        the simulation
    @Override
    public void update(double delta, long now, Simulation s) {

        // check if the animation is done
        if (movement.update(delta)){
            isMoving = false;

            if (isLeaving){
                // the truck has driven off screen, a new one may spawn now
                isLeaving = false;
            } else {
                // the truck has arrived
                SetSpriteToFull();
            }
        }

        // remove the truck if the truck is completely empty and is there
        if (isMoving == false && isTruckSpawned == true && boxes.isEmpty()){
            ReplenishStock(s.getStockManager());
            RemoveTruck();
            System.out.println("the truck is leaving");
        }
    }

    public boolean isTruckSpawned() {
        return isTruckSpawned;
    }
}


