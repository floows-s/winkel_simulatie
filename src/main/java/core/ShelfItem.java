package core;

import javafx.scene.image.Image;
import objects.shelf.Shelf;

// this means that every class that inherits this Interface promises to have this function available.
public interface ShelfItem {
    Image GetSprite();
    Shelf.ShelfType GetShelfType();
}
