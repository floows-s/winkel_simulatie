package objects.storeItems.shelf;

import core.ShelfItem;
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

    // width and height of the sprites
    private final int width = 64;
    private final int height = 80;

    // list products per shelf
    private final ShelfItem[] items = new ShelfItem[9];

    // using a type per shelf
    public enum ShelfType {
        WOOD,
        METAL,
        COOL,
        STORAGE
    }

    // ==== CONSTRUCTOR ====
    /// Constructor reads the data from shelfs.json
    /// @param data is the .Json file with locations, types of shelves
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

    // ==== FUNCTIONS ====
    /// returns type of current shelf
    /// @return current type of the shelf
    public ShelfType getType() {
        return type;
    }

    /// Adds a product to the shelf, if the shelf is full prints error message
    /// @param p is the product that needs to be added
    public void AddItem(ShelfItem p){
        for(int i = 0; i < items.length; i++){
            if (items[i] == null && type == p.GetShelfType() )
            {
                items[i] = p;
                break;
            }
            else{
                System.out.println("Could not add sprite, either space is full or type does not match");
            }
        }
    }

    /// removes a product from the list products and returns said product
    /// TODO needs to remove the current sprite
    /// @param i is the location of the current product in the shelf starting at 0
    public ShelfItem RemoveItem(int i){
        if(items[i] != null){
            items[i] = null;
            return items[i];
        }
        else{
            System.out.println("This location is already empty");
            return null;
        }
    }


    // ==== Interface Renderable  + updatable ====
    /// Draws the shelf based on jason file
    /// Draws product per products list on the correct location based on scale and position of the shelf
    /// @param g is the canvas
    @Override
    public void render(GraphicsContext g) {
        // Draw shelf first
        g.drawImage(sprite, x, y, width, height);

        for (int i = 0; i < items.length; i++) {

            // Continue rendering sprites even though one is empty
            if (items[i] == null) {
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
                    items[i].GetSprite(),
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
