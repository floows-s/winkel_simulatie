package objects.product;

import javafx.scene.image.Image;
import objects.shelf.Shelf;

public class Product {
    // ==== VARIABLES ====
    private Image sprite;
    private ProductName name;
    private Shelf.ShelfType type;
    private double price;

    /// Constructor
    /// @param name string the same name as the sprite name or else no sprite will load
    /// @param type shelfs.Type
    /// @param price double
    public Product(ProductName name, Shelf.ShelfType type, double price)
    {
        this.name   = name;
        this.type   = type;
        this.price  = price;

        // type the name correctly as the image folder suggests
        sprite = new javafx.scene.image.Image(getClass().getResource("/images/products/" + this.name.getImageName() + ".png").toExternalForm());

    }

    /// Copy constructor
    /// @param product to copy values from
    public Product(Product product){
        this.sprite = product.sprite;
        this.name = product.name;
        this.type = product.type;
        this.price = product.price;
    }


    /// returns the name of the product
    /// @return the name
    public ProductName getName() {
        return name;
    }

    /// returns the sprite as an Image
    /// @return
    public Image GetSprite(){
        return sprite;
    }

    /// returns the price of the product as a double
    /// @return
    public double GetPrice(){
        return price;
    }

    /// returns the type as a ShelfType
    /// @return
    public Shelf.ShelfType getType() {
        return type;
    }
}
