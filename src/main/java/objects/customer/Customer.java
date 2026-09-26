package objects.customer;

import core.Point;
import core.Renderable;
import core.Updatable;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import objects.graph.Path;
import objects.graph.Vertex;

public class Customer implements Renderable, Updatable {
    private final int walkingSpeed = 300;
    private static Image sprite = null;

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

    @Override
    public void update(double delta, long now){

        Vertex v;
        if(path.current() == null){
            v = path.next();
        }else{
            v = path.current();
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
        g.drawImage(sprite, x, y, 50, 50);

        // Draw image for shopping cart x amount of pixels in front of customer
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
