package objects.customer.States;

import core.Simulation;
import core.logger.Logger;
import core.logger.TagLogger;
import core.timer.OneShotTimer;
import objects.lookupKioskStand.LookupKioskStand;
import objects.customer.Customer;
import objects.graph.Vertex.VertexID;
import objects.path.Path;
import objects.product.ProductType;

public class LookupProductAtKiosk implements CustomerState{
    ProductType productToLookup;
    Path pathToKiosk = null;
    OneShotTimer lookupWaitTimer = new OneShotTimer(3000);

    Logger log = new TagLogger(LookupKioskStand.class.getSimpleName());


    public LookupProductAtKiosk(ProductType productToLookup){
        this.productToLookup = productToLookup;
    }

    @Override
    public void update(Customer c, double delta, long now, Simulation s) {
        LookupKioskStand kioskStand = s.getLookupKioskStand();

        if(pathToKiosk == null){
            pathToKiosk = s.graph.getShortestPath(
                    c.getCurrentVertexID(),
                    kioskStand.getAccessPoint()
            );
        }

        boolean atDestination = c.followPath(pathToKiosk, s.graph, delta, now);
        if(!atDestination) return;

        // Wait before looking up so it doesn't "instantly" find the item.
        c.setSpriteToIdle();
        if(!lookupWaitTimer.tick(now)) return;

        // Get product location from kiosk
        VertexID shelfContainingProduct = kioskStand.lookupProduct(productToLookup);

        // If no shelf contains the product
        if(shelfContainingProduct == null){
            log.logInfo("No shelf containing product [" + productToLookup + "]. Skipping grocery list item.");

            c.setState(new PickNextGroceryListItem());
            return;
        }

        c.setState(
                new GrabProductFromShelf(
                        productToLookup,
                        shelfContainingProduct
                )
        );
    }
}
