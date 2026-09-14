package core;

import javafx.scene.canvas.GraphicsContext;

public abstract class GameObject {
    protected int x = 0;
    protected int y = 0;

    abstract public void Update(double delta);
    abstract public void Render(GraphicsContext g);


    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
