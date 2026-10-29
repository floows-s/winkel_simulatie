package objects.stocker.states;

import core.Simulation;
import core.logger.Logger;
import core.logger.TagLogger;
import core.timer.OneShotTimer;
import objects.box.Box;
import objects.graph.Vertex.VertexID;
import objects.path.Path;
import objects.product.Product;
import objects.product.ProductType;
import objects.shelf.Shelf;
import objects.stocker.Stocker;

public class UnpackHeldBox implements StockerState {
    VertexID shelfAccessPoint = null;
    Path pathToShelf = null;

    private OneShotTimer placeProductTimer = new OneShotTimer(100);

    Logger log = new TagLogger(getClass().getSimpleName());


    @Override
    public void update(Stocker st, double delta, long now, Simulation s) {
        if(!st.isHoldingBox()){
            log.logWarning("Attempted to unpack box while not holding one!");

            st.setState(new DecideNextTask());
            return;
        }

        // Look at what is in the box (without taking anything out)
        Box box = st.getHeldBox();
        ProductType productType = box.getProductType();

        // Box is empty
        if(productType == null){
            st.removeBox();
            st.setState(new DecideNextTask());
            return;
        }

        // Get location of a shelf of the RIGHT TYPE which has room for products
        if(shelfAccessPoint == null){
            VertexID foundShelf = s.getShelfManager()
                    .getAccessPointOfShelfNeedingRestocking(productType.shelfType);

            // No matching shelf has room, putting the box back into the stockroom
            if(foundShelf == null){
                st.setState(new PutHeldBoxIntoStockroom());
                return;
            }

            shelfAccessPoint = foundShelf;
        }

        // If at shelf
        if(st.getCurrentVertexID() == shelfAccessPoint){
            Shelf shelf = s.getShelfManager().get(shelfAccessPoint);

            // Shelf is full -> look for a new shelf
            if(shelf.isFull()){
                st.setState(new UnpackHeldBox());
                return;
            }

            // Wait before placing, so it doesn't "instantly" put the product on the shelf
            if(!placeProductTimer.tick(now)) return;

            // Remove product from box
            Product p = box.GetProduct();
            if(p == null){
                st.removeBox();
                st.setState(new DecideNextTask());
                return;
            }

            // Put product on shelf. If it is rejected, put it back so it is never lost.
            if(!shelf.addItem(p)){
                log.logWarning("Shelf rejected product of type [" + productType + "]. Returning it to the box.");
                box.AddProduct(p, 1);
                st.setState(new UnpackHeldBox());
            }

            // Start a fresh timer for the next product
            placeProductTimer.reset();
            return;
        }

        // Walk to shelf
        if(pathToShelf == null){
            pathToShelf = s.getGraph().getShortestPath(st.getCurrentVertexID(), shelfAccessPoint);
        }
        st.followPath(pathToShelf, s.getGraph(), delta, now);
    }
}