package objects.register;

import core.Renderable;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import java.awt.*;

public class register implements Renderable {

    private Image sprite = new Image(getClass()
            .getResource("/images/register/Register.png")
            .toExternalForm()) {
    };



    @Override
    public void render(GraphicsContext g) {

    }

    @Override
    public float getX() {
        return 0;
    }

    @Override
    public float getY() {
        return 0;
    }
}
