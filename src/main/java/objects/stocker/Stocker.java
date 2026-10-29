package objects.stocker;

import core.Renderable;
import core.Simulation;
import core.Updatable;
import core.logger.Logger;
import core.logger.TagLogger;
import core.orientation.Coordinates;
import core.orientation.Direction;
import core.timer.AnimationTimer;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import objects.box.Box;
import objects.customer.states.CustomerState;
import objects.graph.Graph;
import objects.graph.Vertex.Vertex;
import objects.graph.Vertex.VertexID;
import objects.path.Path;
import objects.stocker.states.DecideNextTask;
import objects.stocker.states.StockerState;
import utilities.ImageLoader;

import java.util.List;

public class Stocker implements Renderable, Updatable {
    // Constants
    private final int WALKING_SPEED = 1000;
    private final int WALKING_FRAME_TIME_MS = 100;

    // Fields
    private float x;
    private float y;

    // Images
    private static Image idleSprite = null;
    private static Image idleBoxSprite = null;
    private static List<Image> walkingFrames = null;
    private static List<Image> walkingBoxFrames = null;

    // Animations
    private AnimationTimer walkingAnimation;
    private AnimationTimer walkingBoxAnimation;

    // State variables
    private StockerState state = new DecideNextTask();

    private Image sprite;
    private Direction direction = Direction.EAST;
    private VertexID currentVertexID;
    // Objects
    Box heldBox = null;

    private final Logger log = new TagLogger(this.getClass().getSimpleName());

    public Stocker(Vertex startVertex){
        this.x = startVertex.location().x();
        this.y = startVertex.location().y();
        this.currentVertexID = startVertex.id();

        setup();
    }

    // Setup
    private void setup(){
        loadImages();
        sprite = idleSprite;

        setupAnimationTimers();
    }

    private void loadImages(){
        String baseFolder = "/images/stocker/";
        String animationFolder = baseFolder + "animations/";

        // Sprites
        if(idleSprite == null){
            idleSprite = new Image(getClass().getResource(baseFolder + "idle.png").toExternalForm());
        }

        if(idleBoxSprite == null){
            idleBoxSprite = new Image(getClass().getResource(baseFolder + "idle_box.png").toExternalForm());
        }

        // Animations (frames)
        if(walkingFrames == null){
            walkingFrames = ImageLoader.fromFolder(animationFolder + "walking");
        }

        if(walkingBoxFrames == null){
            walkingBoxFrames = ImageLoader.fromFolder(animationFolder + "walking_box");
        }
    }

    private void setupAnimationTimers(){
        walkingAnimation = new AnimationTimer(
                walkingFrames,
                WALKING_FRAME_TIME_MS,
                "stocker_walking"
        );

        walkingBoxAnimation = new AnimationTimer(
                walkingBoxFrames,
                WALKING_FRAME_TIME_MS,
                "stocker_walking_box"
        );
    }


    @Override
    public void update(double delta, long now, Simulation s){
        state.update(this, delta, now, s);
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
        if(isHoldingBox()){
            sprite = walkingBoxAnimation.getFrame(now);
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
            y_center = y - getHeight();

            width = getWidth();
            height = getHeight();
        }else{
            x_center = x + getWidth() / 2;
            y_center = y - getHeight();

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

    public int getWidth(){
        return (int) sprite.getWidth();
    }

    public int getHeight(){
        return (int) sprite.getHeight();
    }

    public boolean isHoldingBox(){
        return heldBox != null;
    }

    public StockerState getState() {
        return state; // TODO: copy?
    }

    public void setState(StockerState state) {
        this.state = state; // TODO: copy?
    }

    public VertexID getCurrentVertexID() {
        return currentVertexID;
    }

    public void setCurrentVertexID(VertexID value){
        currentVertexID = value;
    }

    public void setSpriteToIdle(){
        if(isHoldingBox()){
            sprite = idleBoxSprite;
        }else{
            sprite = idleSprite;
        }
    }

    public void holdBox(Box box) {
        this.heldBox = box;
    }

    public Box removeBox(){
        Box box = this.heldBox;
        this.heldBox = null;

        return box;
    }

}
