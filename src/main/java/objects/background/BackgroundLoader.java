package objects.background;
import core.Renderable;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class BackgroundLoader implements Renderable {
    // ==== VARIABLES ====
    Image backGround = new Image(getClass()
            .getResource("/images/background/StoreFloor2.png")
            .toExternalForm());

    @Override
    public void render(GraphicsContext g){
        g.drawImage(backGround , 0, 0, 1280, 720);
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
