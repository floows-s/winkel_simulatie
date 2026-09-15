package objects.customer;

import core.GameObject;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Customer extends GameObject {
    private final int walkingSpeed = 2;
    private static Image sprite = null;

    public Customer(int x, int y){
        if(sprite == null){
            sprite = new Image(getClass().getResource("/images/customer.png").toExternalForm());
        }

        this.x = x;
        this.y = y;
    }

    @Override
    public void Update(double delta){
        x += walkingSpeed;

        System.out.println(x);
        System.out.println(delta);

    }

    @Override
    public void Render(GraphicsContext g){
        g.drawImage(sprite, x, y, 200, 200);

        // Draw image for shopping cart x amount of pixels in front of customer
    }

}
