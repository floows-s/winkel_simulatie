package objects.customer;

import core.*;
import core.logger.Logger;
import core.logger.TagLogger;
import core.orientation.Coordinates;
import core.orientation.Direction;
import core.timer.AnimationTimer;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import objects.graph.Graph;
import objects.groceryList.GroceryList;
import objects.path.Path;
import objects.graph.Vertex.Vertex;
import objects.graph.Vertex.VertexID;
import utilities.ImageLoader;
import utilities.Randomizer;

import java.util.List;

public class Customer implements Renderable, Updatable {
    // Constants
    private final int WALKING_SPEED = 60;
    private final int WALKING_FRAME_TIME_MS = 100;

    // Fields
    private float x;
    private float y;
    private GroceryList groceryList;

    // Images
    private static Image idleSprite = null;
    private static List<Image> walkingFrames = null;
    private static List<Image> walkingShoppingCartFrames = null;

    // Animations
    private AnimationTimer walkingAnimation;
    private AnimationTimer walkingShoppingCartAnimation;


    // State variables
    private Image currentSprite;
    private Direction currentDirection = Direction.EAST;
    private CustomerState currentState = CustomerState.IDLE;
    private Path currentPath;
    private VertexID currentVertexID = VertexID.SPAWN_POINT;

    private final Logger log = new TagLogger(this.getClass().getSimpleName());


    public Customer(Coordinates startPoint, GroceryList groceryList){
        this.x = startPoint.x();
        this.y = startPoint.y();
        this.groceryList = groceryList;

        // Setup
        setup();
    }

    // Setup
    private void setup(){
        loadImages();
        currentSprite = idleSprite;

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
        switch(currentState){
            case IDLE: decideNewRandomPath(s); break;
            case WALKING_PATH: walkOnPath(currentPath, delta, now, s.graph); break;
        }
    }


    // Actions

    // TODO: this can be removed, just for testing
    private void decideNewRandomPath(Simulation s) {
        VertexID random = VertexID.values()[Randomizer.nextInt(1, VertexID.values().length)];
        currentPath = s.graph.getShortestPath(currentVertexID, random);

        log.logInfo("Next target: [" + random + "]");

        currentState = CustomerState.WALKING_PATH;
    }

    private void walkOnPath(Path path, double delta, long now, Graph graph){
        // TODO Handle walking animation
        //      - Update current direction
        //      - Get next frame
        // Also update render function to handle current direction correctly
        currentSprite = walkingShoppingCartAnimation.getFrame(now);

        // Update this.x this.y
        currentDirection = moveTowardsCurrentStepOnPath(path, graph, delta);
    }

    private Direction moveTowardsCurrentStepOnPath(Path path, Graph graph, double delta){
        if(path.isFinished()){
            currentState = CustomerState.IDLE;
            return currentDirection;
        }

        VertexID currentStep = path.currentOrNext();

        Vertex v = graph.get(currentStep);
        if(v == null){
            log.logWarning("Vertex with ID [" + currentStep + "] not found in given Graph. Skipping step.");
            path.next();
            return currentDirection;
        }

        Coordinates p = v.location();

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
            return currentDirection;
        }

        double dirX = dx / distance;
        double dirY = dy / distance;

        x += dirX * WALKING_SPEED * delta;
        y += dirY * WALKING_SPEED * delta;

        // Return walking direction
        if(dirX > 0){
            return Direction.EAST;
        }else{
            return Direction.WEST;
        }
    }


    // Render
    @Override
    public void render(GraphicsContext g){
        float x_center, y_center;
        int width, height;

        // Correct for direction
        if(currentDirection == Direction.EAST){
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

        g.drawImage(currentSprite, x_center, y_center, width, height);
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
        return (int) currentSprite.getWidth();
    }

    /// @return the current height of the sprite
    public int getHeight(){
        return (int) currentSprite.getHeight();
    }

    public CustomerState getState() {
        return currentState;
    }

    public GroceryList getGroceryList() {
        return new GroceryList(groceryList);
    }
}
