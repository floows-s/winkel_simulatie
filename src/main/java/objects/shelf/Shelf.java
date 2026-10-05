package objects.shelf;

import objects.Product.Product;
import core.Renderable;
import core.Updatable;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Shelf implements Renderable, Updatable {

    // ==== VARIABLES ====
    private Image sprite = null;
    private final float x;
    private final float y;
    private final ShelfType type;

    // width and height of the sprites
    private final int width = 64;
    private final int height = 80;

    // list products per shelf
    private final Product[] products = new Product[9];

    // using a type per shelf
    public enum ShelfType {
        WOOD,
        METAL,
        COOL
    }

    /// Constructor reads the data from shelfs.json
    /// @param data
    public Shelf(ShelfData data){

        x       = data.getX();
        y       = data.getY();
        type    = data.getType();

        if (type != null){
            sprite = new Image(getClass().getResource("/images/shelfs/Shelf" + type + ".png").toExternalForm());
        }
        else
        {
            sprite = new Image(getClass().getResource("/images/shelfs/ShelfWOOD.png").toExternalForm());
        }


    }

    /// Adds a product to the shelf, if the shelf is full prints error message
    /// @param p is the product that needs to be added
    public void AddProduct(Product p){
        for(int i = 0; i < products.length; i++){

            if (products[i] == null && type == p.type ){
                products[i] = p;
                break;
            }
            else{
                System.out.println("Could not add sprite, either space is full or type does not match");
            }
        }
    }

    /// removes a product from the list products
    /// TODO needs to remove the current sprite
    /// @param i is the location of the current product in the shelf starting at 0
    public void RemoveProduct(int i){
        if(products[i] != null){
            products[i] = null;
        }
        else{
            System.out.println("This location is already empty");
        }
    }

    /// Draws the shelf based on jason file
    /// Draws product per products list on the correct location based on scale and position of the shelf
    /// @param g
    @Override
    public void render(GraphicsContext g) {
        // Draw shelf first
        g.drawImage(sprite, x, y, width, height);

        for (int i = 0; i < products.length; i++) {

            // Continue rendering sprites even though one is empty
            if (products[i] == null) {
                continue;
            }
            float resizeFactorX = width / 64;
            float resizeFactorY = height / 80;

            int startLocationX = (int) (4 * resizeFactorX + x);
            int startLocationY = (int) (22 * resizeFactorY + y);

            int moveRightX = (int) (16 * resizeFactorX);
            int moveDownY  = (int) (20 * resizeFactorY);

            int scale = (int) (16 * resizeFactorX);

            int row = i / 3;
            int column = i % 3;

            int productX = startLocationX + (column * moveRightX);
            int productY = startLocationY + (row * moveDownY);

            g.drawImage(
                    products[i].GetSprite(),
                    productX,
                    productY,
                    scale,
                    scale
            );
        }
    }

    @Override
    public void update(double delta, long now) {

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
