package core;

import javafx.scene.canvas.GraphicsContext;

public interface GameObject {
    void Update(double delta);
    void Render(GraphicsContext g);
}
