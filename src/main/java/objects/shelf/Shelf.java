package objects.shelf;

import Product.Product;
import core.Renderable;
import core.Updatable;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Shelf implements Renderable, Updatable {
    private Image sprite = null;

    private float x;
    private float y;
    private String type;
    private final int width = 100;
    private final int height = 100;

    private Product[] products = new Product[9];

    public Shelf(ShelfData data){

        x = data.getX();
        y = data.getY();
        type = data.getType();

        if (type != null){
            sprite = new Image(getClass().getResource("/images/shelfs/Shelf" + type + ".png").toExternalForm());
        }
        else
        {
            sprite = new Image(getClass().getResource("/images/shelfs/ShelfWood.png").toExternalForm());
        }


    }

    /// Adds a product to the shelf, if the shelf is full prints error message
    /// @param p is the product that needs to be added
    public void AddProduct(Product p){
        for(int i = 0; i < products.length; i++){

            if (products[i] == null){
                products[i] = p;
                break;
            }
            else{
                System.out.println("space: " + i + " is full");
            }
        }
    }

    /// removes a product from the list products
    /// TODO needs to remove the current sprite
    /// @param i is the location of the current product in the shelf starting at 0
    public void RemoveProduct(int i){
        if(products[i] != null){
            products[i] = null;
        }
        else{
            System.out.println("This location is already empty");
        }
    }

public void SetRowOne(float startX, float startY, Product p){


}


    @Override
    public void render(GraphicsContext g) {
        // Draw shelf first
        g.drawImage(sprite, x, y, width, height);

        for (int i = 0; i < products.length; i++) {

            // Continue rendering sprites even though one is empty
            if (products[i] == null) {
                continue;
            }

            // Which row? 0, 1, or 2
            int row = i / 3;

            // Which column? 0, 1, or 2
            int column = i % 3;

            double productX = x + width * 0.12 + column * width * 0.30;
            double productY = (y + height * 0.22 + row * height * 0.30);

            // Calculate product size
            double productWidth = width * 0.20;
            double productHeight = height * 0.20;

            g.drawImage(
                    products[i].GetSprite(),
                    productX,
                    productY,
                    productWidth,
                    productHeight
            );
        }
    }

    @Override
    public void update(double delta, long now) {

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
