package objects.groceryList;

import objects.graph.Vertex.Vertex;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

/// An immutable list of {@link GroceryListEntry GroceryListEntries}
public class GroceryList {
    private List<GroceryListEntry> groceries = new ArrayList<>();
    private final Iterator<GroceryListEntry> iterator = groceries.listIterator();

    public GroceryList(List<GroceryListEntry> groceries){
        this.groceries = groceries.stream().map(GroceryListEntry::new).collect(Collectors.toList());
    }

    /// Gets the next item on the grocery list, or null if there isn't a next item.
    /// @return the next item on the grocery list, or null if there isn't a next item.
    public GroceryListEntry getNext(){
        if(!iterator.hasNext()){
            return null;
        }

        return iterator.next();
    }

    /// Creates a list of all the {@link GroceryListEntry}
    /// @return A list of all the {@link GroceryListEntry}
    public List<GroceryListEntry> toList(){
        return groceries.stream().map(GroceryListEntry::new).collect(Collectors.toList());
    }
}
