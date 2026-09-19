package core;

import javafx.scene.canvas.GraphicsContext;

public interface Renderable {
    public void render(GraphicsContext g);
    public float getX();
    public float getY();
}
