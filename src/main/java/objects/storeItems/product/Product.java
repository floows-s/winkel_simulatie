package objects.storeItems.product;

import core.ShelfItem;
import javafx.scene.image.Image;
import objects.storeItems.shelf.Shelf;

public class Product implements ShelfItem {
    // ==== VARIABLES ====
    private Image sprite;
    private String name;
    public Shelf.ShelfType type;
    public double price;
    public ProductType pType;

    public enum ProductType{
        // enum values (can hold more information)
        BREAD("Bread", Shelf.ShelfType.WOOD, 2),
        MEAT("Meat", Shelf.ShelfType.METAL, 5),
        MILK("Milk", Shelf.ShelfType.METAL, 3),
        PIZZA("Pizza", Shelf.ShelfType.METAL, 10),
        ICE_SCREAM("IceScream", Shelf.ShelfType.COOL, 5),
        APPLE("Apple", Shelf.ShelfType.WOOD, 1);

        // variables
        public final String name;
        public final Shelf.ShelfType shelfType;
        public final int price;

        // constructor
        ProductType(String name, Shelf.ShelfType shelfType, int price) {
            this.name = name;
            this.shelfType = shelfType;
            this.price = price;
        }
    }

    /// Constructor
    /// @param pType is an enum based on a product containing the necessary information
    public Product(ProductType pType)
    {
        this.name   = pType.name;
        this.type   = pType.shelfType;
        this.price  = pType.price;
        this.pType = pType;

        // type the name correctly as the image folder suggests
        sprite = new javafx.scene.image.Image(getClass().getResource("/images/products/" + this.name + ".png").toExternalForm());

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

    /// returns the type as a ShelfType
    /// @return current type
    public Shelf.ShelfType getType() {
        return type;
    }
}
