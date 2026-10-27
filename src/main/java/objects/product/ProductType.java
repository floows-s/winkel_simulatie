package objects.product;

import objects.shelf.Shelf;

public enum ProductType{
    // enum values (can hold more information)
    BREAD("Bread", Shelf.ShelfType.WOOD, 2),
    MEAT("Meat", Shelf.ShelfType.METAL, 5),
    MILK("Milk", Shelf.ShelfType.METAL, 3),
    PIZZA("Pizza", Shelf.ShelfType.COOL, 10),
    ICE_SCREAM("IceScream", Shelf.ShelfType.COOL, 5),
    APPLE("Apple", Shelf.ShelfType.WOOD, 1);

    // variables
    public final String name;
    public final Shelf.ShelfType shelfType;
    public final int price;

    // constructor
    ProductType(String name, Shelf.ShelfType shelfType, int price) {
        this.name = name;
        this.shelfType = shelfType;
        this.price = price;
    }
}