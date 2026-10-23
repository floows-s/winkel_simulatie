package objects.product;

public enum ProductName {
    BOX("Box"),
    BREAD("Bread"),
    MEAT("Meat"),
    MILK("Milk"),
    PIZZA("Pizza");

    private final String imageName;

    ProductName(String imageName){
        this.imageName = imageName;
    }

    public String getImageName(){
        return imageName;
    }
}
