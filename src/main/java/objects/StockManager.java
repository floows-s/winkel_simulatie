package objects;

import core.Simulation;
import core.Updatable;
import objects.product.ProductType;

import java.util.EnumMap;

public class StockManager implements Updatable {
    // 12 shelves wood      -- 54 apples | 54 Bread
    //  6 shelves cool      -- 27 meat   | 27 milk
    //  6 shelves freeze    -- 27 pizza  | 27 ice_scream
    //  8 shelves Storage   box

    // total stock of products assumed that each shelf is filled from the start
    EnumMap<ProductType, Integer> stock =
            new EnumMap<>(ProductType.class);

    // total maximum per product
    private final EnumMap<ProductType, Integer> maxStock =
            new EnumMap<>(ProductType.class);

    private int appleAmount         = 54;   // based on the amount of shelves * 9
    private int breadAmount         = 54;
    private int meatAmount          = 27;
    private int milkAmount          = 27;
    private int pizzaAmount         = 27;
    private int ice_screamAmount    = 27;


    // ==== constructor ====
    public StockManager(){

        // set the current stock
        maxStock.put(ProductType.APPLE     , appleAmount); // Has to happen inside the constructor to work
        maxStock.put(ProductType.BREAD     , breadAmount);
        maxStock.put(ProductType.MEAT      , meatAmount);
        maxStock.put(ProductType.MILK      , milkAmount);
        maxStock.put(ProductType.PIZZA     , pizzaAmount);
        maxStock.put(ProductType.ICE_SCREAM, ice_screamAmount);

        stock.putAll(maxStock); // puts everything from maxStock into stock

    }

    /// Adds or removes products from stock
    /// @param pType    the type of product you want to change
    /// @param amount   the amount the stock will increase or decrease
    public void ChangeProductStock(ProductType pType, int amount){
        // add amount of product to stock (can remove products by using negative numbers)
        stock.merge(pType, amount, Integer::sum);
    }

    /// Checks on product type both enum maps stock and max stock and uses this to check if more than half of the products are sold, if so adds this to a new enum map
    /// @return an enum map containing the amount of products that need to get restocked
    public EnumMap<ProductType, Integer> GetRestockOrder() {

        // new enum map
        EnumMap<ProductType, Integer> order =
                new EnumMap<>(ProductType.class);
        // foreach product in maxStock get the product type in order
        for (ProductType type : maxStock.keySet()) {

            // use the current type to receive information on the current stock and max stock amounts
            int max     = maxStock.get(type);
            int current = stock.getOrDefault(type, 0);

            // if the current stock is less then half add it to the order
            if (current * 2 <= max) {
                order.put(type, max - current);
            }
        }
        return order;
    }


    @Override
    public void update(double delta, long now, Simulation s) {
        if(!s.getTruck().isReadyToUnload() && !s.getTruck().isMoving()){
            s.getTruck().SpawnTruck(GetRestockOrder());
        }
    }
}
