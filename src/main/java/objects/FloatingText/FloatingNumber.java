package objects.FloatingText;

import core.Renderable;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.util.function.Supplier;

public class FloatingNumber implements Renderable {

    // ==== Variables =====
    private Supplier<String> text;
    private float x;
    private float y;

    // ==== constructor ====
    public FloatingNumber(Supplier<String> text, float x, float y) {
        this.text = text;
        this.x = x;
        this.y = y;
    }

    // ==== interface renderable ====
    /// renders the text and sets the font + color
    /// @param g    the canvas
    @Override
    public void render(GraphicsContext g) {
        g.setFont(new Font("Arial", 10));
        g.setFill(Color.WHITE);

        g.fillText(text.get(), x + 2, y + 2);
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
