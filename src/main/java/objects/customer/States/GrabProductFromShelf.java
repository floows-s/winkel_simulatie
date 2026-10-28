package objects.customer.States;

import core.Simulation;
import core.logger.Logger;
import core.logger.TagLogger;
import objects.customer.Customer;
import objects.graph.Vertex.VertexID;
import objects.path.Path;
import objects.product.Product;
import objects.product.ProductType;
import objects.shelf.Shelf;

public class GrabProductFromShelf implements CustomerState{
    VertexID shelfAccessPoint;
    Path pathToShelf = null;
    ProductType productType;

    Logger log = new TagLogger(GrabProductFromShelf.class.getSimpleName());

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

        Shelf shelf = s.shelfManager.getShelf(shelfAccessPoint);

        Product product = shelf.grabProduct(productType);
        if(product != null){
            boolean isShoppingCardFull = c.addProductToShoppingCart(product);

            log.logInfo("Grabbed product [" + product.getpType() + "]");

            if(isShoppingCardFull) log.logWarning("Couldn't add item to shopping cart. It is full.");
        }else{
            log.logWarning("Couldn't grab product. Not in shelf.");
        }

        c.setState(new PickNextGroceryListItem());
    }
}
