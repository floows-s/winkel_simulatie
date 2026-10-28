package objects.groceryList;

import objects.graph.Vertex.VertexID;
import objects.product.ProductType;

public record GroceryListItem(ProductType product, VertexID location){

    /// Makes a deep-copy of the given {@code GroceryListEntry}
    /// @param g entry to copy from
    public GroceryListItem(GroceryListItem g){
        this(g.product, g.location);
    }

    @Override
    public String toString() {
        return "GroceryListEntry{" +
                "product=" + product +
                ", location=" + location +
                '}';
    }
}
