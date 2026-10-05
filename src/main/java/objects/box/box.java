package objects.box;

import objects.product.Product;
import objects.shelf.Shelf;

import java.util.List;

public class box extends Product{

    // ==== VARIABLES ====
    private final Product[] products = new Product[30];
    public Shelf.ShelfType type;

    /// Creates a box with the name Box and type STORAGE and price 0
    ///
    public box() {
        super("Box", Shelf.ShelfType.STORAGE, 0);
    }


    /// adds a product to the box
    /// @param p
    public void AddProduct(Product p){
        if (p.type == type) {
            for(int i = 0; i < products.length; i ++){
                if (products[i] == null){products[i] = p;}
            }
        }
    }

    /// returns the list of products inside the box
    /// @return
    public Product[] getProducts() {
        return products;
    }

    /// returns the first item in the box that is not null
    /// @return
    public Product GetProduct(){
        for(int i = 0; i < products.length; i ++){
            if (products[i] != null) return products[i];
        }
        return null;
    }
}
