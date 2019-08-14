package de.uniwue.gdp.labyrinth;

import de.uniwue.gdp.labyrinth.model.Field;
import de.uniwue.gdp.labyrinth.model.Maze;

import java.util.function.Predicate;

public class Solver {
    private final Predicate<Maze> canGoLeft = maze -> !maze.isWall(Maze.Direction.LEFT) && maze.marks(Maze.Direction.LEFT) < 2;
    private final Predicate<Maze> canGoRight = maze -> !maze.isWall(Maze.Direction.RIGHT) && maze.marks(Maze.Direction.RIGHT) < 2;
    private final Predicate<Maze> canGoAhead = maze -> !maze.isWall(Maze.Direction.AHEAD) && maze.marks(Maze.Direction.AHEAD) < 2;
    private final Predicate<Maze> canGoBack = maze -> !maze.isWall(Maze.Direction.BACK) && maze.marks(Maze.Direction.BACK) < 2;

    private int direction = Maze.Direction.AHEAD;

    public String solveMaze(Maze z) {
        Field[][] maze = initializeMaze(z);
        while (!z.isWall(Maze.Direction.AHEAD)) {
            z.walk(direction);

        }
        return "";
    }

    private Field[][] initializeMaze(Maze maze) {
        Field[][] m = new Field[maze.width()][maze.height()];
        for (int i = 0; i < m.length; i++) {
            for (int j = 0; j < m[i].length; j++) {
                m[i][j] = new Field(i,j);
            }
        }
        return m;
    }

    private Predicate<Maze> isJunction(Maze maze) {
        return null;
    }
}
