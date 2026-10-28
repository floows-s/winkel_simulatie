package objects.shoppingCart;

import objects.product.Product;

import java.util.ArrayList;
import java.util.List;

public class ShoppingCart {
    public final int MAX_CAPACITY = 10;
    private List<Product> storage = new ArrayList<>();

    public ShoppingCart(){}

    public ShoppingCart(ShoppingCart shoppingCart){
        this.storage = shoppingCart.storage;
    }

    public boolean add(Product p){
        if(storage.size() >= MAX_CAPACITY){
            return false;
        }

        storage.add(p);
        return true;
    }


    ///TODO: say: remove all items from shopping cart and return them.
    public List<Product> takeOutAllProducts(){
        List<Product> takenProducts = storage.stream().toList();
        storage.clear();

        return takenProducts;
    }

    /// @return the amount of products in this shopping cart
    public int amountOfProducts(){
        return this.storage.size();
    }
}
