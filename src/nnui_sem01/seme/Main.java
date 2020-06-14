package inui.seme;

import inui.seme.structure.Maze;
import inui.seme.structure.Node;

import static inui.seme.structure.Maze.FINISH;
import static inui.seme.structure.Maze.START;

public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        final String filePath = "../image/MAZE1.bmp";
        Maze maze = new Maze(filePath);

        //maze.setFinish(new Point(1, 1));
        //maze.setStart(new Point(2, 5));
        maze.generateStartFinishPosition();

        MazeRunner ms = new MazeRunner(maze);
        Node result = ms.AStarAlgorithm();

        if (result != null) {
            do {
                System.out.println(result.toString());
                maze.setCharAtPosition(result.getPosition(), 'O');

                result = result.getParent();
            } while (result != null);
        }
        maze.setCharAtPosition(maze.getStart(), FINISH);
        maze.setCharAtPosition(maze.getFinish(), START);

        System.out.println(maze.toString());
    }
}
