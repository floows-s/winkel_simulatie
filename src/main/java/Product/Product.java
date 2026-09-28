package Product;

import javafx.scene.image.Image;
import objects.shelf.Shelf;

import java.util.ArrayList;

public class Product {
    // ==== VARIABLES ====
    private Image sprite;
    private String name;
    private String type;

    private int shelfLocation;
    private Shelf shelf;

    public Product(String name)
    {
        this.name  = name;

        // type the name correctly as the image folder suggests

        sprite = new javafx.scene.image.Image(getClass().getResource("/images/products/" + this.name + ".png").toExternalForm());

    }

    public Image GetSprite(){
        return sprite;
    }


}
