package objects.product;

import javafx.scene.image.Image;

public class Product {
    private ProductType type;
    private Image sprite;

    Product(ProductData data){

    }

    public ProductType getType() {
        return type;
    }


    public Image getSprite() {
        return sprite;
    }

}
