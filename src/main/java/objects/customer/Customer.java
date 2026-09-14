package objects.customer;

import core.GameObject;
import core.Sprites;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public class Customer extends GameObject {
    public static int walkingSpeed = 200;

    public Customer(int x, int y){
        this.x = x;
        this.y = y;
    }

    @Override
    public void Update(double delta){
        x += walkingSpeed * delta;
        System.out.println(x);
        System.out.println(delta);

    }

    @Override
    public void Render(GraphicsContext g){
        Image img = new Image(getClass().getResource("/images/customer.png").toExternalForm());
        g.drawImage(img, x, y, 200, 200);
        // Draw image for shopping cart x amount of pixels in front of customer
    }

}
