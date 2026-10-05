package objects.Product;

import javafx.scene.image.Image;
import objects.shelf.Shelf;

public class Product {
    // ==== VARIABLES ====
    private Image sprite;
    private String name;
    public Shelf.ShelfType type;
    private double price;

    /// Constructor
    /// @param name string the same name as the sprite name or else no sprite will load
    /// @param type shelfs.Type
    /// @param price double
    public Product(String name, Shelf.ShelfType type, double price)
    {
        this.name   = name;
        this.type   = type;
        this.price  = price;

        // type the name correctly as the image folder suggests
        sprite = new javafx.scene.image.Image(getClass().getResource("/images/products/" + this.name + ".png").toExternalForm());

    }

    public Image GetSprite(){
        return sprite;
    }


}
