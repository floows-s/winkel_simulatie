package objects.box;

import javafx.scene.image.Image;
import objects.product.Product;
import objects.product.ProductType;
import objects.shelf.Shelf;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.stream.Collectors;

public class Box implements core.ShelfItem {

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
            if (products[i] == null) continue;

            Product p = new Product(products[i]);
            products[i] = null;

            return p;
        }

        return null;
    }

    /// Looks at the first product in the box WITHOUT removing it.
    /// @return the type of the first product, or null if the box is empty
    public ProductType getProductType(){
        for(Product p : products){
            if(p != null) return p.getpType();
        }

        return null;
    }

    /// adds a product if there is space
    /// @param p product
    public void AddProduct(Product p, int amount){
        for (int i = 0; i < products.length && amount > 0; i++){
            if (products[i] == null){
                products[i] = p;
                amount--;
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
