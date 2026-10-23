package objects.groceryList;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

/// An immutable list of {@link GroceryListItem GroceryListEntries}
public class GroceryList {
    private List<GroceryListItem> groceries;
    private Iterator<GroceryListItem> iterator;

    public GroceryList(List<GroceryListItem> groceries){
        this.groceries = groceries.stream().map(GroceryListItem::new).collect(Collectors.toList());
        this.iterator = this.groceries.iterator();
    }

    /// Gets the next item on the grocery list, or null if there isn't a next item.
    /// @return the next item on the grocery list, or null if there isn't a next item.
    public GroceryListItem getNext(){
        if(!iterator.hasNext()){
            return null;
        }

        return iterator.next();
    }

    /// Creates a list of all the {@link GroceryListItem}
    /// @return A list of all the {@link GroceryListItem}
    public List<GroceryListItem> toList(){
        return groceries.stream().map(GroceryListItem::new).collect(Collectors.toList());
    }
}
