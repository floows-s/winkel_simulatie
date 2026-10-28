package objects.register;

import core.Renderable;
import core.Simulation;
import core.Updatable;
import core.orientation.Coordinates;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import objects.customer.Customer;
import objects.graph.Vertex.Vertex;
import objects.graph.Vertex.VertexID;
import objects.movement.Movement;
import objects.product.Product;
import objects.stats.Stats;

import java.util.ArrayDeque;
import java.util.List;
import java.util.Queue;


public class Register implements Renderable, Updatable {

    // ==== VARIABLES ====
    private int x;
    private int y;
    float width  = 78;
    float height = 135;
    private final VertexID accessPoint;
    private final Vertex endOfQueue;

    // create a que of products for easy access
    private final Queue<Product> products = new ArrayDeque<>();
    private Product currentProduct = null;

    // create an object for movement and give it its speed
    private final Movement movement = new Movement(50);

    // sprite animation logic
    private int offsetXStartPosition    = 16;       // the amount of pixels to the right from the start position top left
    private int offsetYStartPosition    = 2;        // amount of pixels down from the top right
    private int offsetXEndPosition      = 16;       // amount of pixes right from the start position
    private int offsetYEndPosition      = 106;      // amount of pixels down from the start position

    // Queue for customers
    Queue<RegisterQueueEntry> customerQueue = new ArrayDeque<>();
    RegisterQueueEntry currentQueueEntry = null;

    // receive the sprite
    private Image sprite = new Image(getClass()
            .getResource("/images/register/Register.png")
            .toExternalForm()) {
    };

    // ==== FUNCTIONS ====

    /// Constructor
    /// @param x coordinate on map
    /// @param y coordinate on map
    public Register(int x, int y, VertexID accessPoint, Vertex endOfQueue){
        this.x = x;
        this.y = y;
        this.accessPoint = accessPoint;
        this.endOfQueue = endOfQueue;
    }

    /// Adds a customer to this register's queue.
    /// @param c the customer to enqueue
    /// @param afterCheckout the action to run once the customer has checked out
    public void enterQueue(Customer c, Runnable afterCheckout){
        customerQueue.add(new RegisterQueueEntry(c, afterCheckout));
    }

    /// Add all items to queue products
    /// @param items is a list of products contained inside the shopping cart
    private void CheckOut(List<Product> items){
        products.addAll(items);
    }

    // ==== Interface renderable ====
    /// renders all sprites
    /// @param g is the canvas
    @Override
    public void render(GraphicsContext g) {
        g.drawImage(sprite, x, y, width, height);

        if (currentProduct != null){
            g.drawImage(currentProduct.GetSprite(), movement.getX(), movement.getY(), 16, 16);
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


    public VertexID getAccessPoint() {
        return accessPoint;
    }

    // ==== interface updatable ====

    /// updates the sprites each time a sprite should move
    /// @param delta    time that has passed
    /// @param now      time it is now
    /// @param s        the simulation
    @Override
    public void update(double delta, long now, Simulation s) {
        if (currentProduct == null){
            currentProduct = products.poll();

            // Queue is empty
            if(currentProduct == null){

                // Current customer has been checked out
                if(currentQueueEntry != null){
                    Customer customer = currentQueueEntry.customer();
                    boolean atEndOfQueue = moveCustomerTowardsEndOfQueue(customer, delta, now);

                    if(atEndOfQueue){
                        customer.setCurrentVertexID(endOfQueue.id());
                        currentQueueEntry.afterCheckout().run();
                        currentQueueEntry = null;
                    }
                }

                handleCustomerQueue();
                return;
            }

            movement.start( x + offsetXStartPosition, y + offsetYStartPosition, x + offsetXEndPosition, y + offsetYEndPosition);
        }

        // if the animation is done
        if(movement.update(delta)){
            Stats.IncrementProductSold(1);
            Stats.IncrementMoneyEarned(currentProduct.GetPrice());

            // access the stock manager from within the simulation and alter it
            s.stockManager.ChangeProductStock(currentProduct.getpType(), -1);
            currentProduct = null;
            System.out.println("current product has been checked out");
        }
    }

    private boolean moveCustomerTowardsEndOfQueue(Customer c, double delta, long now){
        return c.moveTo(endOfQueue.location(), delta, now);
    }

    private void handleCustomerQueue(){
        if(currentQueueEntry != null || customerQueue.isEmpty()){
            return;
        }

        currentQueueEntry = customerQueue.poll();

        // Add products from customer to product queue
        Customer c = currentQueueEntry.customer();
        products.addAll(c.takeProductsOutOfCart());
    }
}
