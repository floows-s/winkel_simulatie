package objects.shelf;

import core.logger.Logger;
import core.logger.TagLogger;
import objects.graph.Vertex.VertexID;
import objects.product.Product;
import core.Renderable;
import core.Simulation;
import core.Updatable;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Shelf implements Renderable, Updatable {

    // ==== VARIABLES ====
    private Image sprite = null;
    private final float x;
    private final float y;
    private final ShelfType type;
    private final VertexID accessPointVertexID;

    // width and height of the sprites
    private final int width = 64;
    private final int height = 80;

    // list products per shelf
    private final Product[] products = new Product[9];

    // using a type per shelf
    public enum ShelfType {
        WOOD,
        METAL,
        COOL,
        STORAGE
    }

    private Logger log;

    /// Constructor reads the data from shelfs.json
    /// @param data
    public Shelf(ShelfData data){
        x                   = data.getX();
        y                   = data.getY();
        type                = data.getType();
        accessPointVertexID = data.getAccessPointVertexID();

        // Setup logger
        log = new TagLogger(this.getClass().getSimpleName() + " [" + accessPointVertexID + "]");

        // Load sprite
        if (type != null){
            sprite = new Image(getClass().getResource("/images/shelfs/Shelf" + type + ".png").toExternalForm());
        }
        else
        {
            log.logWarning("No type give. Loading wood sprite.");
            sprite = new Image(getClass().getResource("/images/shelfs/ShelfWOOD.png").toExternalForm());
        }
    }

    /// returns type of current shelf
    /// @return
    public ShelfType getType() {
        return type;
    }

    /// Adds a product to the shelf, if the shelf is full prints error message
    /// @param p is the product that needs to be added
    public void AddProduct(Product p){
        for(int i = 0; i < products.length; i++){
            if (products[i] == null && type == p.getType()){
                products[i] = p;
                return;
            }
        }

        log.logWarning("Could not add sprite, either space is full or type does not match");
    }

    /// removes a product from the list products and returns said product
    /// TODO needs to remove the current sprite
    /// @param i is the location of the current product in the shelf starting at 0
    public Product RemoveProduct(int i){
        if(products[i] != null){
            products[i] = null;
            return products[i];
        }
        else{
            log.logWarning("Failed to remove a product. Location is already empty.");
            return null;
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
    public void update(double delta, long now, Simulation s) {

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
