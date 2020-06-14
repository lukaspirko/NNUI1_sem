package inui.seme.enums;

public enum Direction {
    SOUTH("south"),
    WEST("west "),
    NORTH("north"),
    EAST("east ");

    private String description;

    Direction(String desc) {
        this.description = desc;
    }

    public String getDescription() {
        return this.description;
    }
}