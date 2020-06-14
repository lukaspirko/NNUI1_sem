package inui.seme.structure;

import inui.seme.enums.Direction;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.Random;

public class Maze {

    private final char[][] maze;
    private final int dimension;
    private final Point start;
    private final Point finish;
    private static final char WALL = 'X';
    private static final char PATH = ' ';

    public static final char START = 'F';
    public static final char FINISH = 'S';

    public Maze(String filePath) {
        int dimensionTmp = 0;
        char[][] mazeTmp = null;
        try {
            URL url = this.getClass().getResource(filePath);
            BufferedImage image = ImageIO.read(url);

            dimensionTmp = image.getWidth();
            mazeTmp = new char[dimensionTmp][dimensionTmp];

            for (int i = 0; i < dimensionTmp; i++) {
                for (int j = 0; j < dimensionTmp; j++) {

                    if (Color.BLACK.equals(new Color(image.getRGB(j, i)))) {
                        mazeTmp[j][i] = WALL;
                    } else {
                        mazeTmp[j][i] = PATH;
                    }
                }
            }

        } catch (IllegalArgumentException ex) {
            System.err.println("Missing picture on PATH! " + ex);
        } catch (IOException ex) {
            System.err.println("Exception " + ex);
        }

        this.maze = mazeTmp;
        this.dimension = dimensionTmp;
        start = new Point();
        finish = new Point();
    }

    public void generateStartFinishPosition() {

        for (int i = 0; i < 10000; i++) {
            start.x = new Random().nextInt(dimension - 1) + 1;
            start.y = new Random().nextInt(dimension - 1) + 1;

            if (Maze.PATH == getField(start)) {
                break;
            }
        }

        for (int i = 0; i < 10000; i++) {
            finish.x = new Random().nextInt(dimension - 1) + 1;
            finish.y = new Random().nextInt(dimension - 1) + 1;

            if (Maze.PATH == getField(finish) && !start.equals(finish)) {
                break;
            }
        }
    }

    private char getField(Point point) {
        return maze[point.x][point.y];
    }

    public void setCharAtPosition(Point point, char c) {
        maze[point.x][point.y] = c;
    }

    public Point getStart() {
        return start;
    }

    public void setStart(Point point) {
        start.setLocation(point.x, point.y);
    }

    public Point getFinish() {
        return finish;
    }

    public void setFinish(Point point) {
        finish.setLocation(point.x, point.y);
    }

    public int getDimension() {
        return dimension;
    }

    public boolean shouldMoveAtPoint(Point point, Direction direction) {
        if (null == direction) {
            return false;
        } else {
            switch (direction) {
                case EAST:
                    return (point.x + 1 < dimension - 1) && (maze[point.x + 1][point.y] != WALL);
                case WEST:
                    return point.x - 1 > 0 && (maze[point.x - 1][point.y] != WALL);
                case NORTH:
                    return point.y - 1 > 0 && (maze[point.x][point.y - 1] != WALL);
                case SOUTH:
                    return (point.y + 1 < dimension - 1) && (maze[point.x][point.y + 1] != WALL);
                default:
                    return false;
            }
        }
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();

        builder.append("\n");
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                builder.append(this.getField(new Point(j, i))).append(" ");
            }
            builder.append("\n");
        }
        return builder.toString();
    }
}