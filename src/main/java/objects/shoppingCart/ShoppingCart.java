package objects.shoppingCart;

import objects.product.Product;

import java.util.ArrayList;
import java.util.List;

public class ShoppingCart {
    public final int MAX_CAPACITY = 10;
    private List<Product> storage = new ArrayList<>(MAX_CAPACITY);

    public boolean add(Product p){
        if(storage.size() >= MAX_CAPACITY){
            return false;
        }

        storage.add(p);
        return true;
    }
}
