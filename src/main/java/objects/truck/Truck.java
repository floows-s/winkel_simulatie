package objects.truck;

import objects.box.Box;
import objects.product.Product;
import objects.product.ProductType;

import java.util.ArrayDeque;
import java.util.EnumMap;
import java.util.Queue;

public class Truck {
    // ==== VARIABLES ====
    public boolean isTruckSpawned;

    // automatically returns first available box and null when no box is available
    private final Queue<Box> boxes = new ArrayDeque<>();

    // ==== CONSTRUCTOR ====
    public Truck(){

    }

    // ==== FUNCTIONS ====
    /// using the queue system it automatically takes the first box in the queue and if it is null returns null
    /// @return the first box in the queue
    public Box GetBox() {
        return boxes.poll();
    }

    /// adds a box to boxes
    /// @param b Box
    public void AddBox(Box b){
        boxes.add(b);
    }

    // should spawn a truck
    public void SpawnTruck(EnumMap<ProductType, Integer> order){

        for(ProductType type : order.keySet()){
            int orderAmount = order.get(type);
            if (orderAmount > 0){

                Box box = new Box();                // new box to put items in
                int boxCapacity = box.capacity;     // capacity of a box
                Product p = new Product(type);      // current product that needs to be restocked

                // check if the box can be filled
                while(orderAmount > 0){
                    if(orderAmount > boxCapacity){
                        box.AddProduct(p, boxCapacity);
                        AddBox(box);                    // add the box to the Truck
                        box = new Box();                // create a new box
                        orderAmount -= boxCapacity;     // remove the order amount
                    }
                    else
                    {
                        box.AddProduct(p, orderAmount); // add the products
                        AddBox(box);                    // add the box to the truck
                        orderAmount -= orderAmount;
                    }
                }   // while not 0 loop
            }   // if not 0 loop
        }   // for loop ends

    }
    public void RemoveTruck(){

    }

}


