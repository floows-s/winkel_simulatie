package objects.truck;

import core.Renderable;
import core.Simulation;
import core.Updatable;
import javafx.scene.canvas.GraphicsContext;
import objects.box.Box;
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
    private boolean isMoving = false;
    private double speed = 50;

    // width and height of the sprite
    private double width = 84;
    private double height = 261;

    // automatically returns first available box and null when no box is available
    private final Queue<Box> boxes = new ArrayDeque<>();

    // ==== CONSTRUCTOR ====
    public Truck(){

    }

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

    /// should spawn a truck
    public void SpawnTruck(EnumMap<ProductType, Integer> order){

        // set the correct sprite

        isTruckSpawned = true;

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

        // move the sprite to the correct spot
        MoveTo(23, 64);
    }

    /// removes the truck and changes its appearance
    public void RemoveTruck(){
        if(boxes.poll() == null){
            // if there are no more boxes change the sprite and move away
            SetSpriteToEmpty();
            MoveTo(23,-300);
            isTruckSpawned = false;

            // move the truck away
        }
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

    /// sets the moving variables
    /// @param x    location to travel to x
    /// @param y    location to travel to y
    public void MoveTo(double x, double y){
        this.targetX = x;
        this.targetY = y;
        this.isMoving = true;
    }


    // ==== Interface Renderable ====
    @Override
    public void render(GraphicsContext g) {
        if (sprite != null){
            g.drawImage(sprite, x, y, width, height);
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

    @Override
    public void update(double delta, long now, Simulation s) {

        if (!isMoving) return;

        double dx = targetX - x;
        double dy = targetY - y;

        // Distance to destination
        double distance = Math.sqrt(dx * dx + dy * dy);

        // How far to travel this frame
        double step = speed * delta;

        // Arrived at destination
        if (distance <= step) {
            x = targetX;
            y = targetY;
            isMoving = false;

            // when destination is reached change sprite
            SetSpriteToFull();
            return;
        }

        // Move in the direction of the destination
        x += (dx / distance) * step;
        y += (dy / distance) * step;

        if (isMoving == false && isTruckSpawned == true && GetBox() == null){
            RemoveTruck();
        }
    }
}


