package objects.box;

import core.ShelfItem;
import javafx.scene.image.Image;
import objects.product.Product;
import objects.shelf.Shelf;

public class Box implements ShelfItem {

    // ==== VARIABLES ====
    public final int capacity = 30;
    private Product[] products = new Product[capacity];
    private Image sprite = new Image(getClass().getResource("/images/products/Box.png").toExternalForm());


    // ==== CONSTRUCTOR ====
    /// creates a box
    /// requires an array of type products
    public Box() {

    }

    // ==== FUNCTIONS ====
    /// returns the products inside the box
    /// @return array of products inside the box
    public Product[] getProducts() {
        return products;
    }

    /// returns the first item in the box
    /// @return the first item in the box that is not null
    public Product GetProduct(){
        for(int i = 0; i < products.length; i ++){
            if (products[i] != null) return products[i];
        }
        return null;
    }

    /// adds a product if there is space
    /// @param p product
    public void AddProduct(Product p, int amount){
        if (amount <= 0){
            // Cannot be zero
        }
        else
        {
            for(Product pro: products){
                if(pro == null){
                    pro = p;
                    amount =- 1;
                    if(amount <= 0) break;
                }
            }
        }

    }


    // ==== Interface ShelfItem ====
    /// returns a sprite of the box
    /// @return Image sprite of Box
    @Override
    public Image GetSprite() {
        return sprite;
    }

    /// returns a shelf type of current box
    /// @return shelf.ShelfType STORAGE
    @Override
    public Shelf.ShelfType GetShelfType() {
        return Shelf.ShelfType.STORAGE;
    }
}
