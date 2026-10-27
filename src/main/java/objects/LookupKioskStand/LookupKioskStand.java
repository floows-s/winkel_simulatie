package objects.LookupKioskStand;

import core.Renderable;
import core.orientation.Coordinates;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import objects.graph.Vertex.VertexID;
import objects.product.ProductType;
import objects.shelf.ShelfManager;

public class LookupKioskStand implements Renderable {
    private static Image sprite = null;

    private float x;
    private float y;
    private final ShelfManager shelfManager;

    public LookupKioskStand(Coordinates position, ShelfManager shelfManager){
        x = position.x();
        y = position.y();
        this.shelfManager = shelfManager;

        loadSprite();
    }

    private void loadSprite(){
        if(sprite == null){
            String URI = getClass().getResource("/images/lookup_kiosk_stand/lookup_kiosk_stand.png").toExternalForm();
            sprite = new Image(URI);
        }
    }

    @Override
    public void render(GraphicsContext g) {
        double xCenter = x - sprite.getWidth() / 2;
        double yCenter = y - sprite.getHeight() / 2;

        g.drawImage(sprite, xCenter, yCenter);
    }

    /// Look up which shelf currently contains the given product.
    /// @param type of product to look for
    /// @return access point of the shelf with the product, or null if no shelf contains the product
    public VertexID lookupProduct(ProductType type){
        return shelfManager.getAccessPointOfShelfThatHasProduct(type);
    }

    /// @return VertexID which leads to this lookup kiosk stand
    public VertexID getAccessPoint(){
        return VertexID.LOOKUP_KIOSK_STAND;
    }

    @Override
    public float getX() {
        return x;
    }

    @Override
    public float getY() {
        return y;
    }
}
