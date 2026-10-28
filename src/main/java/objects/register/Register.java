package objects.register;

import core.Renderable;
import core.Simulation;
import core.Updatable;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import objects.FloatingText.FloatingNumber;
import objects.movement.Movement;
import objects.product.Product;
import objects.stats.Stats;

import java.util.ArrayDeque;
import java.util.List;
import java.util.Queue;


public class Register implements Renderable, Updatable {

    // ==== VARIABLES ====
    private int x;
    private int y;
    float width  = 78;
    float height = 135;

    private final Queue<Product> products = new ArrayDeque<>();
    private Product currentProduct = null;

    // create an object for movement and give it its speed
    private final Movement movement = new Movement(50);

    // sprite animation logic
    private int offsetXStartPosition    = 16;       // the amount of pixels to the right from the start position top left
    private int offsetYStartPosition    = 2;        // amount of pixels down from the top right
    private int offsetXEndPosition      = 16;       // amount of pixes right from the start position
    private int offsetYEndPosition      = 106;      // amount of pixels down from the start position

    // animation locations


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

    /// Add al items to queue products
    /// @param items is a list of products contained inside the shopping cart
    public void CheckOut(List<Product> items){
        products.addAll(items);
    }


    @Override
    public void render(GraphicsContext g) {
        g.drawImage(sprite, x, y, width, height);

        if (currentProduct != null){
            g.drawImage(currentProduct.GetSprite(), movement.getX(), movement.getY(), 16, 16);
        }

    }

    @Override
    public float getX() {
        return 0;
    }

    @Override
    public float getY() {
        return 0;
    }

    @Override
    public void update(double delta, long now, Simulation s) {

        if (currentProduct == null){
            currentProduct = products.poll();
            if(currentProduct == null){
                return;
            }
            else{
                movement.start( x + offsetXStartPosition, y + offsetYStartPosition, x + offsetXEndPosition, y + offsetYEndPosition);
            }
        }

        // if the animation is done
        if(movement.update(delta)){
            Stats.IncrementProductSold(1);
            Stats.IncrementMoneyEarned(currentProduct.GetPrice());
            currentProduct = null;
            System.out.println("current product has been checked out");
        }
    }
}
