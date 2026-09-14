package objects.product;

public enum ProductType {
    BREAD("bread"),
    STEAK("steak"),
    MILK("milk"),
    CEREAL("cereal"),
    FISH("fish");

    private String readableName;
    private ProductType(String text){
        this.readableName = text;
    }

    public String getReadableName(){
        return this.readableName;
    }
}
