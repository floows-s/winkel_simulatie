package objects.customer.States;

import core.Simulation;
import core.logger.Logger;
import core.logger.TagLogger;
import objects.LookupKioskStand.LookupKioskStand;
import objects.customer.Customer;
import objects.graph.Vertex.VertexID;
import objects.path.Path;
import objects.product.ProductType;

public class LookupProductAtKiosk implements CustomerState{
    ProductType productToLookup;
    Path pathToKiosk = null;
    Logger log = new TagLogger(LookupKioskStand.class.getSimpleName());

    public LookupProductAtKiosk(ProductType productToLookup){
        this.productToLookup = productToLookup;
    }

    @Override
    public void update(Customer customer, double delta, long now, Simulation s) {
        LookupKioskStand kioskStand = s.getLookupKioskStand();

        if(pathToKiosk == null){
            pathToKiosk = s.graph.getShortestPath(
                    customer.getCurrentVertexID(),
                    kioskStand.getAccessPoint()
            );
        }

        boolean atDestination = customer.followPath(pathToKiosk, s.graph, delta, now);
        if(!atDestination) return;

        // Get product location from kiosk
        VertexID shelfContainingProduct = kioskStand.lookupProduct(productToLookup);

        // If no shelf contains the product
        if(shelfContainingProduct == null){
            log.logInfo("No shelf containing product " + productToLookup + ". Skipping grocery list item.");

            customer.setState(new ExploringStore()); // TODO: Change this to: Skip item on grocery list (customer state)
            return;
        }


        // TODO: grab product (customer state)
    }
}
