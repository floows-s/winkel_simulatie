package objects.shelf;

import core.ShelfItem;
import core.logger.Logger;
import core.logger.TagLogger;
import objects.box.Box;
import objects.graph.Vertex.VertexID;
import objects.product.Product;
import core.Renderable;
import core.Simulation;
import core.Updatable;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import objects.product.ProductType;

public class Shelf implements Renderable, Updatable {

    // ==== VARIABLES ====
    private Image sprite = null;
    private final float x;
    private final float y;
    private final ShelfType type;
    private final VertexID accessPoint;

    // width and height of the sprites
    private final int width = 64;
    private final int height = 80;

    // list items per shelf
    public final int MAX_ITEM_AMOUNT = 9;
    private final ShelfItem[] shelfItems = new ShelfItem[MAX_ITEM_AMOUNT];

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
        accessPoint = data.getAccessPoint();

        // Setup logger
        log = new TagLogger(this.getClass().getSimpleName() + " [" + accessPoint + "]");

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
    /// @return type of shelf
    public ShelfType getType() {
        return type;
    }

    public Product grabProduct(ProductType type){
        for(int i = 0; i< shelfItems.length; i++){
            ShelfItem item = shelfItems[i];
            if(item == null) continue;

            if(item instanceof Product p){

                if(p != null && p.getpType() == type){
                    removeItem(i);
                    return p;
                }
            }
        }

        return null;
    }

    /// Adds a item to the shelf, if the shelf is full prints error message
    /// @param item is the item that needs to be added
    public void addItem(ShelfItem item){
        if(isFull()){
            log.logWarning("Could not add item, shelf is full.");
            return;
        }

        if(item instanceof Product p){
            if(p.getType() != getType()){
                log.logWarning("Could not add product, type does not match");
                return;
            }
        }

        if(item instanceof Box b){
            if(getType() != ShelfType.STORAGE){
                log.logWarning("Could not add box, shelf is not of type store");
                return;
            }
        }

        for(int i = 0; i < shelfItems.length; i++){
            if (shelfItems[i] == null){
                shelfItems[i] = item;
                return;
            }
        }

    }

    /// removes a product from the list products and returns said product
    /// TODO needs to remove the current sprite
    /// @param i is the location of the current product in the shelf starting at 0
    public ShelfItem removeItem(int i){
        if(shelfItems[i] != null){
            shelfItems[i] = null;
            return shelfItems[i];
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

        for (int i = 0; i < shelfItems.length; i++) {

            // Continue rendering sprites even though one is empty
            if (shelfItems[i] == null) {
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
                    shelfItems[i].GetSprite(),
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

    public VertexID getAccessPoint() {
        return accessPoint;
    }

    public boolean hasProduct(ProductType name){
        for(ShelfItem item : shelfItems){
            if(item == null) continue;
            
            if(item instanceof Product p){
                if(p.getpType() == name){
                    return true;
                }
            }
        }

        return false;
    }

    public boolean hasBox(){
        for(ShelfItem item : shelfItems){
            if(item == null) continue;
            if(item instanceof Box) return true;
        }

        return false;
    }

    /// @return first encountered box, or null if there is no box
    public Box grabBox(){
        for(int i = 0; i<shelfItems.length; i++){
            ShelfItem item = shelfItems[i];
            if(item == null) continue;

            if(item instanceof Box b){
                return b;
            }

            removeItem(i);
        }

        return null;
    }

    /// Amount of items on shelf
    /// @return Amount of items on shelf
    public int amountOfItems(){
        int counter = 0;

        for(ShelfItem i : shelfItems){
            if(i != null) counter++;
        }

        return counter;
    }

    public boolean isFull(){
        return amountOfItems() >= MAX_ITEM_AMOUNT;
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
