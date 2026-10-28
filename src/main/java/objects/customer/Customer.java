package objects.customer;

import core.*;
import core.logger.Logger;
import core.logger.TagLogger;
import core.orientation.Coordinates;
import core.orientation.Direction;
import core.timer.AnimationTimer;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import objects.customer.states.CustomerState;
import objects.customer.states.SpawnedIn;
import objects.groceryList.GroceryListItem;
import objects.product.Product;
import objects.shoppingCart.ShoppingCart;
import objects.graph.Graph;
import objects.groceryList.GroceryList;
import objects.path.Path;
import objects.graph.Vertex.Vertex;
import objects.graph.Vertex.VertexID;
import utilities.ImageLoader;

import java.util.List;

public class Customer implements Renderable, Updatable {
    // Constants
    private final int WALKING_SPEED = 200;
    private final int WALKING_FRAME_TIME_MS = 100;

    // Fields
    private float x;
    private float y;
    private GroceryList groceryList;

    private ShoppingCart shoppingCart = null;

    // Images
    private static Image idleSprite = null;
    private static Image idleShoppingCartSprite = null;
    private static List<Image> walkingFrames = null;
    private static List<Image> walkingShoppingCartFrames = null;

    // Animations
    private AnimationTimer walkingAnimation;
    private AnimationTimer walkingShoppingCartAnimation;

    // State variables
    private CustomerState state = new SpawnedIn();

    private Image sprite;
    private Direction direction = Direction.EAST;
    private VertexID currentVertexID = VertexID.SPAWN_POINT;

    private final Logger log = new TagLogger(this.getClass().getSimpleName());

    public Customer(Vertex startVertex, GroceryList groceryList){
        this.x = startVertex.location().x();
        this.y = startVertex.location().y();
        this.currentVertexID = startVertex.id();
        this.groceryList = groceryList;

        setup();
    }

    // Setup
    private void setup(){
        loadImages();
        sprite = idleSprite;

        setupAnimationTimers();
    }

    private void loadImages(){
        String baseFolder = "/images/customer/";
        String animationFolder = baseFolder + "animations/";

        // Sprites
        if(idleSprite == null){
            idleSprite = new Image(getClass().getResource(baseFolder + "idle.png").toExternalForm());
        }

        if(idleShoppingCartSprite == null){
            idleShoppingCartSprite = new Image(getClass().getResource(baseFolder + "idle_shopping_cart.png").toExternalForm());
        }

        // Animations (frames)
        if(walkingFrames == null){
            walkingFrames = ImageLoader.fromFolder(animationFolder + "walking");
        }

        if(walkingShoppingCartFrames == null){
            walkingShoppingCartFrames = ImageLoader.fromFolder(animationFolder + "walking_shopping_cart");
        }
    }

    private void setupAnimationTimers(){
        walkingAnimation = new AnimationTimer(
                walkingFrames,
                WALKING_FRAME_TIME_MS,
                "customer_walking"
        );

        walkingShoppingCartAnimation = new AnimationTimer(
                walkingShoppingCartFrames,
                WALKING_FRAME_TIME_MS,
                "customer_walking_shopping_cart"
        );
    }


    @Override
    public void update(double delta, long now, Simulation s){
        state.update(this, delta, now, s);
    }


    // Actions

    /// TODO here is a explanation of this method
    /// <p>
    /// Side effects:
    /// - Updates {@link x} & {@link y} to move towards the current step
    /// - Updates {@link currentVertexID} to the last vertex it has been
    /// - Updates {@link direction} based on the moving direction
    /// - Updates {@link sprite} to accommodate for the walking animation
    /// @param path to get the current step to move towards.
    /// @param graph to move on
    /// @param delta between this and the last frame
    /// @return if the last step on the path is reached
    public Boolean followPath(Path path, Graph graph, double delta, long now){
        if(path.isFinished()) return true;

        // Get vertex of current step from graph
        VertexID currentStep = path.currentOrNext();
        Vertex v = graph.get(currentStep);
        if(v == null){
            log.logError("Vertex with ID [" + currentStep + "] not found in given Graph. Skipping step. This will possibly lead to not following the edges of the graph properly.");
            path.next();
        }

        // Move in the direction of the current step
        boolean atDestination = moveTo(v.location(), delta, now);
        if(atDestination){
            currentVertexID = currentStep;

            if(path.isFinished()) return true;

            // Advance to next step in path
            path.next();
        }

        return false;
    }


    public void handleWalkingAnimation(long now){
        if(hasShoppingCart()){
            sprite = walkingShoppingCartAnimation.getFrame(now);
        }else{
            sprite = walkingAnimation.getFrame(now);
        }
    }

    // Render
    @Override
    public void render(GraphicsContext g){
        float x_center, y_center;
        int width, height;

        // Correct for direction
        if(direction == Direction.EAST){
            x_center = x - getWidth() / 2;
            y_center = y - getHeight() / 2;;

            width = getWidth();
            height = getHeight();
        }else{
            x_center = x + getWidth() / 2;
            y_center = y - getHeight() / 2;

            width = -getWidth();
            height= getHeight();
        }

        g.drawImage(sprite, x_center, y_center, width, height);
    }


    // Getters and setters

    @Override
    public float getX() {
        return this.x;
    }

    @Override
    public float getY() {
        return this.y;
    }


    /// @return the current width of the sprite
    public int getWidth(){
        return (int) sprite.getWidth();
    }

    /// @return the current height of the sprite
    public int getHeight(){
        return (int) sprite.getHeight();
    }

    public CustomerState getState() {
        return state; // TODO: copy?
    }

    public void setState(CustomerState state) {
        this.state = state; // TODO: copy?
    }


    public VertexID getCurrentVertexID() {
        return currentVertexID;
    }

    public void setCurrentVertexID(VertexID value){
        currentVertexID = value;
    }

    // --- Grocery list ---
    public GroceryList getGroceryList() {
        return new GroceryList(groceryList);
    }

    public GroceryListItem getNextGroceryListItem(){
        GroceryListItem item = groceryList.getNext();
        return item == null ? null : new GroceryListItem(item);
    }


    public void setSpriteToIdle(){
        if(hasShoppingCart()){
            sprite = idleShoppingCartSprite;
        }else{
            sprite = idleSprite;
        }
    }


    // --- Shopping cart ---
    public boolean hasShoppingCart(){
        return shoppingCart != null;
    }

    public boolean addToCart(Product p){
        if(!hasShoppingCart()){
            throw new IllegalStateException("Customer doesn't have shopping cart.");
        }

        return shoppingCart.add(p);
    }

    public List<Product> takeProductsOutOfCart(){
        if(!hasShoppingCart()){
            throw new IllegalStateException("Customer doesn't have shopping cart.");
        }

        return shoppingCart.takeOutAllProducts();
    }


    /// Returns a copy of this customer's shopping cart.
    /// <p>The returned cart is created via the {@code ShoppingCart} copy constructor.
    /// @return a copy of the cart, or {@code null} if this customer has no cart
    public ShoppingCart getShoppingCart(){
        if(hasShoppingCart()){
            return new ShoppingCart(shoppingCart);
        }

        return null;
    }

    /// Sets this customer's shopping cart.
    /// <p>
    /// Stores a copy of the given cart, created via the {@code ShoppingCart} copy constructor.
    /// @param shoppingCart the cart to copy and assign to this customer
    public void setShoppingCart(ShoppingCart shoppingCart) {
        this.shoppingCart = new ShoppingCart(shoppingCart);
    }

    /// Moves this customer toward the given location.
    /// @param location the target coordinates
    /// @param delta the elapsed time between frames
    /// @return {@code true} if the location was reached, {@code false} otherwise
    public boolean moveTo(Coordinates location, double delta, long now){
        if(x == location.x() && y == location.y()) return true;

        // Calculate delta and distance
        float dx = (location.x() - x);
        float dy = (location.y() - y);
        double distance = Math.sqrt(dx * dx + dy * dy);

        // If close enough to target -> snap
        double snappingThreshold = 1;
        if(distance < snappingThreshold){
            x = location.x();
            y = location.y();

            return true;
        }

        double dirX = dx / distance;
        double dirY = dy / distance;

        x += dirX * WALKING_SPEED * delta;
        y += dirY * WALKING_SPEED * delta;

        // Determine and set current facing direction
        if(dirX > 0){
            direction = Direction.EAST;
        }else{
            direction = Direction.WEST;
        }

        // Update sprite
        handleWalkingAnimation(now);

        return false;
    }
}
