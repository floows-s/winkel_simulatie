package objects.customer;

import core.*;
import core.logger.Logger;
import core.logger.TagLogger;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import objects.graph.Graph;
import objects.groceryList.GroceryList;
import objects.path.Path;
import objects.graph.Vertex.Vertex;
import objects.graph.Vertex.VertexID;
import utilities.Randomizer;

public class Customer implements Renderable, Updatable {
    // Constants
    public final int WIDTH = 32;
    public final int HEIGHT = 58;
    private final int walkingSpeed = 60;
    private static Image sprite = null;

    private final Logger log = new TagLogger(this.getClass().getSimpleName());

    // Fields
    private float x;
    private float y;
    private GroceryList groceryList;


    // State variables
    private CustomerState currentState = CustomerState.IDLE;
    private Path currentPath;
    private VertexID currentVertexID;


    public Customer(Point startPoint, GroceryList groceryList){
        if(sprite == null){
            sprite = new Image(getClass().getResource("/images/customer/idle_shopping_cart_1x.png").toExternalForm());
        }

        this.x = startPoint.x();
        this.y = startPoint.y();
        this.groceryList = groceryList;
    }


    @Override
    public void update(double delta, long now, Simulation s){
        switch(currentState){
            case IDLE: decideNewRandomPath(s); break;
        }
    }

    private void decideNewRandomPath(Simulation s) {
        VertexID random = VertexID.values()[Randomizer.nextInt(1, VertexID.values().length)];
//        currentPath =
    }

    private void takeStepOnPath(Path path, Graph graph, double delta){
        if(path.isFinished()){
            return;
        }

        VertexID currentStep = path.currentOrNext();

        Vertex v = graph.get(currentStep);
        if(v == null){
            log.logWarning("Vertex with ID [" + currentStep + "] not found in given Graph. Skipping step.");
            path.next();
            return;
        }

        Point p = v.location();

        float dx = (p.x() - x);
        float dy = (p.y() - y);
        double distance = Math.sqrt(dx * dx + dy * dy);

        if(distance < 1){
            x = p.x();
            y = p.y();
            path.next();
            return;
        }

        double dirX = dx / distance;
        double dirY = dy / distance;

        x += dirX * walkingSpeed * delta;
        y += dirY * walkingSpeed * delta;
    }


    @Override
    public void render(GraphicsContext g){
        float x_center = x - WIDTH / 2;
        float y_center = y - HEIGHT / 2;

        g.drawImage(sprite, x_center, y_center, WIDTH, HEIGHT);
    }

    @Override
    public float getX() {
        return this.x;
    }

    @Override
    public float getY() {
        return this.y;
    }

    public CustomerState getCurrentState() {
        return currentState;
    }
}
