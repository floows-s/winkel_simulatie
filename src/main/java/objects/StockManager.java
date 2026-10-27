package objects;

import objects.product.Product;

import java.util.EnumMap;

public class StockManager {
    // 12 shelves wood      -- 54 apples | 54 Bread
    //  6 shelves cool      -- 27 meat   | 27 milk
    //  6 shelves freeze    -- 27 pizza  | 27 ice_scream
    //  8 shelves Storage   box

    // total stock of products assumed that each shelf is filled from the start
    EnumMap<Product.ProductType, Integer> stock =
            new EnumMap<>(Product.ProductType.class);

    private final EnumMap<Product.ProductType, Integer> maxStock =
            new EnumMap<>(Product.ProductType.class);

    private int appleAmount         = 54;
    private int breadAmount         = 54;
    private int meatAmount          = 27;
    private int milkAmount          = 27;
    private int pizzaAmount         = 27;
    private int ice_screamAmount    = 27;



    public StockManager(){

        // set the current stock
        maxStock.put(Product.ProductType.APPLE     , appleAmount); // Has to happen inside the constructor to work
        maxStock.put(Product.ProductType.BREAD     , breadAmount);
        maxStock.put(Product.ProductType.MEAT      , meatAmount);
        maxStock.put(Product.ProductType.MILK      , milkAmount);
        maxStock.put(Product.ProductType.PIZZA     , pizzaAmount);
        maxStock.put(Product.ProductType.ICE_SCREAM, ice_screamAmount);

        stock.putAll(maxStock); // puts everything from maxStock into stock

    }

    /// Adds or removes products from stock
    /// @param p        the product you want to change
    /// @param amount   the amount the stock will increase or decrease
    public void ChangeProductStock(Product p, int amount){
        // add amount of product to stock (can remove products by using negative numbers)
        stock.merge(p.pType, amount, Integer::sum);
}

    /// Checks on product type both enum maps stock and max stock and uses this to check if more than half of the products are sold, if so adds this to a new enum map
    /// @return an enum map containing the amount of products that need to get restocked
    public EnumMap<Product.ProductType, Integer> GetRestockOrder() {

        // new enum map
        EnumMap<Product.ProductType, Integer> order =
                new EnumMap<>(Product.ProductType.class);
        // foreach product in maxStock get the product type in order
        for (Product.ProductType type : maxStock.keySet()) {

            // use the current type to receive information on the current stock and max stock amounts
            int max = maxStock.get(type);
            int current = stock.getOrDefault(type, 0);

            // if the current stock is less then half add it to the order
            if (current * 2 <= max) {
                order.put(type, max - current);
            }
        }
        return order;
    }




}
