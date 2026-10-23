package objects.register;

import core.Renderable;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import objects.FloatingText.FloatingNumber;
import objects.product.Product;

import java.util.List;


public class Register implements Renderable {

    // ==== VARIABLES ====
    private int x;
    private int y;
    float width  = 78;
    float height = 135;

    private Image sprite = new Image(getClass()
            .getResource("/images/register/Register.png")
            .toExternalForm()) {
    };

    /// Constructor
    /// @param x coordinate on map
    /// @param y coordinate on map
    public Register(int x, int y){
        this.x = x;
        this.y = y;
    }

    private double DetermineTotalPrice(List<Product> products){
        double totalPrice = 0;
        // location of text is not yet correct
        FloatingNumber priceText = new FloatingNumber("0", x, y);
        // how to add the floating number to gameObject? or cast the function render with the priceText?

        for(Product p : products){
            // set the text to the correct price total
            totalPrice += p.price;
            priceText.SetText(String.valueOf(totalPrice));

            // make animation happen
            Image productSprite = p.GetSprite();
        }
        return totalPrice;
    }

    @Override
    public void render(GraphicsContext g) {
        g.drawImage(sprite, x, y, width, height);
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
