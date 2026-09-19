package objects.customer;

import core.Renderable;
import core.Updatable;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Customer implements Renderable, Updatable {
    private final int walkingSpeed = 30;
    private static Image sprite = null;

    private float x;
    private float y;

    public Customer(int x, int y){
        if(sprite == null){
            sprite = new Image(getClass().getResource("/images/customer.png").toExternalForm());
        }

        this.x = x;
        this.y = y;
    }

    @Override
    public void update(double delta, long now){
    }

    @Override
    public void render(GraphicsContext g){
        g.drawImage(sprite, x, y, 200, 200);

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
