package objects.product;

import core.ShelfItem;
import javafx.scene.image.Image;
import objects.shelf.Shelf;

public class Product implements ShelfItem {
    // ==== VARIABLES ====
    private Image sprite;
    private String name;
    public Shelf.ShelfType type;
    public double price;

    private ProductType pType;

    // ==== constructor ====
    /// Constructor
    /// @param pType is an enum based on a product containing the necessary information
    public Product(ProductType pType)
    {
        this.name   = pType.name;
        this.type   = pType.shelfType;
        this.price  = pType.price;
        this.pType  = pType;

        // type the name correctly as the image folder suggests
        sprite = new Image(getClass().getResource("/images/products/" + this.name + ".png").toExternalForm());

    }


    // ==== Interface ShelfItem
    /// returns the sprite as an Image
    /// @return Image sprite of the current product
    @Override
    public Image GetSprite(){
        return sprite;
    }

    ///
    /// @return current type of shelf the product belongs to
    @Override
    public Shelf.ShelfType GetShelfType() {
        return type;
    }

    // ==== FUNCTIONS ====
    /// returns the price of the product as a double
    /// @return current double price
    public double GetPrice(){
        return price;
    }

    public ProductType getpType() {
        return pType;
    }

    /// returns the type as a ShelfType
    /// @return current type
    public Shelf.ShelfType getType() {
        return type;
    }
}
