package objects.customer;

import core.Point;
import core.Renderable;
import core.Simulation;
import core.Updatable;
import core.logger.Logger;
import core.logger.TagLogger;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import objects.graph.Graph;
import objects.path.Path;
import objects.graph.Vertex;
import objects.graph.VertexID;

public class Customer implements Renderable, Updatable {
    private final int walkingSpeed = 100;
    public final int WIDTH = 60;
    public final int HEIGHT = 60;
    private static Image sprite = null;

    private final Logger log = new TagLogger(this.getClass().getSimpleName());

    private float x;
    private float y;
    private Path path;

    public Customer(int x, int y, Path path){
        if(sprite == null){
            sprite = new Image(getClass().getResource("/images/customer.png").toExternalForm());
        }

        this.x = x;
        this.y = y;

        this.path = path;
    }

    private void followPath(Path path, Graph graph, double delta){
        if(path.isFinished()){
            log.logInfo("No more steps to take. Path completed!");
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
    public void update(double delta, long now, Simulation s){
        followPath(path, s.graph, delta);
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

}
