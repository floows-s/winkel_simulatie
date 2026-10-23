package objects.product;

import core.logger.Logger;
import core.logger.TagLogger;
import objects.shelf.Shelf.ShelfType;
import utilities.Randomizer;

import java.util.Map;

public class ProductFactory {
    private static final Logger log = new TagLogger(ProductFactory.class.getSimpleName());

    private static final Map<ProductName, Product> products = Map.of(
            // BREAD
            ProductName.BREAD, new Product(ProductName.BREAD, ShelfType.WOOD, 10.00),

            // MEAT
            ProductName.MEAT, new Product(ProductName.MEAT, ShelfType.COOL, 10.00),

            // MILK
            ProductName.MILK, new Product(ProductName.MILK, ShelfType.METAL, 10.00),

            // PIZZA
            ProductName.PIZZA, new Product(ProductName.PIZZA, ShelfType.METAL, 10.00)
    );


    /// Get the product associated with the {@link ProductName}.
    /// @param name of products to get
    /// @return the found product, or null if not found
    public static Product getProduct(ProductName name){
        if(name == null){
            log.logWarning("Failed to get product. Null value passed as name.");
            return null;
        }

        Product foundProduct = products.get(name);
        if(foundProduct == null){
            log.logError("Failed to get product. Tried to get a product which is not part of the internal products map. Given ProductName: " + name);
            return null;
        }

        return new Product(foundProduct);
    }


    /// Chooses a random {@link ProductName} which is part of the internal products map. The corresponding {@link Product} can be retrieved with {@code getProduct(name)}.
    /// @return a random {@link ProductName}.
    public static ProductName getRandomProductName(){
        // Get all ProductName's from map
        ProductName[] keys = new ProductName[products.size()];
        products.keySet().toArray(keys);

        int randomIndex = Randomizer.nextInt(0, keys.length);

        return keys[randomIndex];
    }
}
