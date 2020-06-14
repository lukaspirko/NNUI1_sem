package inui.seme.structure;

import inui.seme.enums.Actions;
import inui.seme.enums.Direction;

import java.awt.Point;

public class Node {
    private Node parent = null;
    private Actions action = Actions.START;
    private Direction direction = Direction.SOUTH;
    private int pricePath = action.getPrice();
    private int estimatePath;
    private Point position;

    public Node(Node parent, Actions action, Direction direction, int pricePath, int estimatePath, int x, int y) {
        this.parent = parent;
        this.action = action;
        this.direction = direction;
        this.pricePath = pricePath + action.getPrice();
        this.estimatePath = estimatePath;
        this.position = new Point(x, y);
    }

    public Node(int estimatePath, Point point) {
        this.estimatePath = estimatePath;
        this.position = point;
    }

    public Node getParent() {
        return parent;
    }

    public Actions getAction() {
        return action;
    }

    public int getPricePath() {
        return pricePath;
    }

    public int getEstimatePath() {
        return estimatePath;
    }

    public Point getPosition() {
        return position;
    }

    public Direction getDirection() {
        return direction;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("X:").append(getPosition().x)
                .append(" Y:").append(getPosition().y)
                .append(", direction: ").append(getDirection().getDescription())
                .append(", action: ").append(getAction().getDescription())
                .append(", price: ").append(getPricePath());
        return builder.toString();
    }
}
