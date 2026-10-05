package objects.shelf;

import java.awt.*;

public class Shelf_Cooling extends Shelf{
    // ==== VARIABLES ====
private Image sprite = null;

// location
private float x = 0;
private float y = 0;

    /// Constructor reads the data from shelfs.json
    ///
    /// @param data
    public Shelf_Cooling(ShelfData data) {
        super(data);
    }
}
