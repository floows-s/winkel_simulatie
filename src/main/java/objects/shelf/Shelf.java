package objects.shelf;

import core.Renderable;
import core.Updatable;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Shelf implements Renderable, Updatable {
    private static Image sprite = null;

    private float x;
    private float y;
    private final int width = 240;
    private final int height = 255;

    public Shelf(ShelfData data){
        if(sprite == null){
            sprite = new Image(getClass().getResource("/images/shelf.png").toExternalForm());
        }

        x = data.getX();
        y = data.getY();
    }

    @Override
    public void render(GraphicsContext g) {
        g.drawImage(sprite, x, y, width, height);
    }

    @Override
    public void update(double delta, long now) {

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
