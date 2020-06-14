package inui.seme.enums;

public enum Actions {
    RIGHT("90 right", 1),
    LEFT("90 left", 1),
    AROUND("180 around", 2),
    FORWARD("forward ", 3),
    START("start   ", 0);

    private int price;
    private String description;

    Actions(String desc, int price) {
        this.description = desc;
        this.price = price;
    }

    public String getDescription() {
        return this.description;
    }

    public int getPrice() {
        return this.price;
    }
}
