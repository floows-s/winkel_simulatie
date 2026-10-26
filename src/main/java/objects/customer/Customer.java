package objects.customer;

import core.*;
import core.logger.Logger;
import core.logger.TagLogger;
import core.orientation.Coordinates;
import core.orientation.Direction;
import core.timer.AnimationTimer;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import objects.customer.States.CustomerState;
import objects.customer.States.ExploringStore;
import objects.customer.States.GrabbingShoppingCart;
import objects.customer.States.SpawnedIn;
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
    private final int WALKING_SPEED = 60;
    private final int WALKING_FRAME_TIME_MS = 100;

    // Fields
    private float x;
    private float y;
    private GroceryList groceryList;

    private ShoppingCart shoppingCart = null;

    // Images
    private static Image idleSprite = null;
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

        Coordinates p = v.location();

        // Calculate delta and distance
        float dx = (p.x() - x);
        float dy = (p.y() - y);
        double distance = Math.sqrt(dx * dx + dy * dy);

        // If close enough to target -> snap
        double snappingThreshold = 1;
        if(distance < snappingThreshold){
            x = p.x();
            y = p.y();
            currentVertexID = currentStep;

            path.next();
            return false;
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

    public GroceryList getGroceryList() {
        return new GroceryList(groceryList);
    }

    public void setShoppingCart(ShoppingCart shoppingCart) {
        this.shoppingCart = shoppingCart; // TODO: copy?
    }

    public boolean hasShoppingCart(){
        return shoppingCart != null;
    }
}
