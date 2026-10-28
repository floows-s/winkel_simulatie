package objects.customer.States;

import core.Simulation;
import core.logger.Logger;
import core.logger.TagLogger;
import core.timer.OneShotTimer;
import objects.customer.Customer;
import objects.graph.Vertex.VertexID;
import objects.path.Path;
import objects.product.Product;
import objects.product.ProductType;
import objects.shelf.Shelf;

public class GrabProductFromShelf implements CustomerState{
    private VertexID shelfAccessPoint;
    private Path pathToShelf = null;
    private ProductType productType;

    private OneShotTimer grabbingWaitTimer = new OneShotTimer(700);

    private Logger log = new TagLogger(GrabProductFromShelf.class.getSimpleName());

    public GrabProductFromShelf(ProductType productType, VertexID shelfAccessPoint){
        this.productType = productType;
        this.shelfAccessPoint = shelfAccessPoint;
    }

    @Override
    public void update(Customer c, double delta, long now, Simulation s) {
        if(pathToShelf == null){
            pathToShelf = s.graph.getShortestPath(
                    c.getCurrentVertexID(),
                    shelfAccessPoint
            );
        }

        boolean atDestination = c.followPath(pathToShelf, s.graph, delta, now);
        if(!atDestination) return;

        c.setSpriteToIdle();

        Shelf shelf = s.shelfManager.getShelf(shelfAccessPoint);
        if(!shelf.hasProduct(productType)){
            log.logWarning("Couldn't grab product. Not in shelf.");
            c.setState(new PickNextGroceryListItem());
            return;
        }

        // Wait before grabbing so it doesn't "instantly" grab the item.
        if(!grabbingWaitTimer.tick(now)) return;

        // Grab product
        Product product = shelf.grabProduct(productType);
        if(product != null){
            boolean isShoppingCardFull = !c.addToCart(product);
            if(isShoppingCardFull) {
                log.logWarning("Couldn't add item to shopping cart. It is full. Putting item back on shelf and checking out at register.");

                // Put product back on shelf
                shelf.AddProduct(product);
                c.setState(new CheckoutItemsAtRegister());
                return;
            }
        }

        c.setState(new PickNextGroceryListItem());
    }
}
