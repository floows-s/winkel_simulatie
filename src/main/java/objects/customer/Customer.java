package objects.customer;

import core.GameObject;
import core.Sprites;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public class Customer extends GameObject {
    public static int walkingSpeed = 10;

    public Customer(int x, int y){
        this.x = x;
        this.y = y;
    }

    @Override
    public void Update(double delta){
//        x += walkingSpeed * delta;
    }

    @Override
    public void Render(GraphicsContext g){
//        g.drawImage(sprite, x, y);
        g.drawImage(Sprites.customer, x, y, 200, 200);
        // Draw image for shopping cart x amount of pixels in front of customer

        g.setFill(Color.BLACK);
        g.fillRect(0, 0, 800, 600);
    }

}
