package objects.shelf;

import core.Renderable;
import core.Simulation;
import core.Updatable;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Shelf implements Renderable, Updatable {
    private static Image sprite = null;

    private float x;
    private float y;
    private final int WIDTH = 70;
    private final int HEIGHT = 85;

    public Shelf(ShelfData data){
        if(sprite == null){
            sprite = new Image(getClass().getResource("/images/shelf.png").toExternalForm());
        }

        x = data.getX();
        y = data.getY();
    }

    @Override
    public void render(GraphicsContext g) {
        float x_center = x - WIDTH / 2;
        float y_center = y - HEIGHT / 2;

        g.drawImage(sprite, x_center, y_center, WIDTH, HEIGHT);
    }

    @Override
    public void update(double delta, long now, Simulation s) {

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
