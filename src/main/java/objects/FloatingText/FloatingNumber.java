package objects.FloatingText;

import core.Renderable;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;


public class FloatingNumber implements Renderable {

    private String text;
    private float x;
    private float y;

    public FloatingNumber(String text, float x, float y) {
        this.text = text;
        this.x = x;
        this.y = y;
    }

    @Override
    public void render(GraphicsContext g) {
        g.setFont(new Font("Arial", 10));
        g.setFill(Color.WHITE);
        g.fillText(text, x + 2, y + 2);

    }
    public void SetText(String text){this.text = text;
    }

    @Override
    public float getX() {
        return x;
    }

    @Override
    public float getY() {
        return y;
    }
}
