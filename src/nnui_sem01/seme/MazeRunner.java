package inui.seme;

import inui.seme.enums.Actions;
import inui.seme.enums.Direction;
import inui.seme.structure.Maze;
import inui.seme.structure.Node;

import java.awt.Point;
import java.util.ArrayList;

public class MazeRunner {

    private final Maze maze;
    private final boolean[][] explored;
    private final Point start, finish;

    public MazeRunner(Maze maze) {
        this.maze = maze;
        explored = new boolean[maze.getDimension()][maze.getDimension()];
        start = maze.getStart();
        finish = maze.getFinish();
    }

    public Node AStarAlgorithm() {
        Node actual;

        ArrayList<Node> listOfPaths = new ArrayList<>();
        listOfPaths.add(new Node(estimationOfRemainingRoad(start), start));

        while (true) {

            actual = getCheapestPath(listOfPaths);

            if (actual.getPosition().x == finish.x && actual.getPosition().y == finish.y) {
                System.out.println("\nPath find! -------> Price: " + actual.getPricePath() + "\n");
                return actual;
            }

            if (!explored[actual.getPosition().x][actual.getPosition().y]) {
                explored[actual.getPosition().x][actual.getPosition().y] = true;

                if (maze.shouldMoveAtPoint(actual.getPosition(), Direction.WEST)) {
                    int x = actual.getPosition().x - 1;
                    int y = actual.getPosition().y;

                    if (!explored[x][y]) {
                        Node tmp;
                        switch (actual.getDirection()) {
                            case SOUTH:
                                tmp = createNode(actual, Actions.RIGHT, Direction.WEST);
                                break;
                            case NORTH:
                                tmp = createNode(actual, Actions.LEFT, Direction.WEST);
                                break;
                            case EAST:
                                tmp = createNode(actual, Actions.AROUND, Direction.WEST);
                                break;
                            default:
                                tmp = actual;
                                break;
                        }

                        listOfPaths.add(new Node(tmp, Actions.FORWARD, Direction.WEST, tmp.getPricePath(), estimationOfRemainingRoad(new Point(x, y)), x, y));
                    }
                }

                if (maze.shouldMoveAtPoint(actual.getPosition(), Direction.EAST)) {
                    int x = actual.getPosition().x + 1;
                    int y = actual.getPosition().y;

                    if (!explored[x][y]) {
                        Node tmp;
                        switch (actual.getDirection()) {
                            case NORTH:
                                tmp = createNode(actual, Actions.RIGHT, Direction.EAST);
                                break;
                            case SOUTH:
                                tmp = createNode(actual, Actions.LEFT, Direction.EAST);
                                break;
                            case WEST:
                                tmp = createNode(actual, Actions.AROUND, Direction.EAST);
                                break;
                            default:
                                tmp = actual;
                                break;
                        }

                        listOfPaths.add(new Node(tmp, Actions.FORWARD, Direction.EAST, tmp.getPricePath(), estimationOfRemainingRoad(new Point(x, y)), x, y));
                    }
                }

                if (maze.shouldMoveAtPoint(actual.getPosition(), Direction.SOUTH)) {
                    int x = actual.getPosition().x;
                    int y = actual.getPosition().y + 1;

                    if (!explored[x][y]) {
                        Node tmp;
                        switch (actual.getDirection()) {
                            case WEST:
                                tmp = createNode(actual, Actions.LEFT, Direction.SOUTH);
                                break;
                            case EAST:
                                tmp = createNode(actual, Actions.RIGHT, Direction.SOUTH);
                                break;
                            case NORTH:
                                tmp = createNode(actual, Actions.AROUND, Direction.SOUTH);
                                break;
                            default:
                                tmp = actual;
                                break;
                        }

                        listOfPaths.add(new Node(tmp, Actions.FORWARD, Direction.SOUTH, tmp.getPricePath(), estimationOfRemainingRoad(new Point(x, y)), x, y));
                    }
                }

                if (maze.shouldMoveAtPoint(actual.getPosition(), Direction.NORTH)) {
                    int x = actual.getPosition().x;
                    int y = actual.getPosition().y - 1;

                    if (!explored[x][y]) {
                        Node tmp;
                        switch (actual.getDirection()) {
                            case EAST:
                                tmp = createNode(actual, Actions.LEFT, Direction.NORTH);
                                break;
                            case WEST:
                                tmp = createNode(actual, Actions.RIGHT, Direction.NORTH);
                                break;
                            case SOUTH:
                                tmp = createNode(actual, Actions.AROUND, Direction.NORTH);
                                break;
                            default:
                                tmp = actual;
                                break;
                        }

                        listOfPaths.add(new Node(tmp, Actions.FORWARD, Direction.NORTH, tmp.getPricePath(), estimationOfRemainingRoad(new Point(x, y)), x, y));
                    }
                }
            }

            if (listOfPaths.isEmpty()) {
                System.out.println("Failed! Cannot find way.");
                break;
            }
        }

        return null;
    }

    private Node getCheapestPath(ArrayList<Node> explored) {
        double min = explored.get(0).getPricePath() + explored.get(0).getEstimatePath();
        Node cheapestNode = explored.get(0);

        for (Node node : explored) {
            double totalPrice = node.getPricePath() + node.getEstimatePath();
            if (totalPrice < min) {
                min = totalPrice;
                cheapestNode = node;
            }
        }

        explored.remove(cheapestNode);
        return cheapestNode;
    }

    private int estimationOfRemainingRoad(Point actual) {
        return (Math.abs(finish.x - actual.x) + Math.abs(finish.y - actual.y)) * 4;
    }

    private Node createNode(Node actual, Actions action, Direction direction) {
        return new Node(actual, action, direction, actual.getPricePath(), actual.getEstimatePath(), actual.getPosition().x, actual.getPosition().y);
    }

}
