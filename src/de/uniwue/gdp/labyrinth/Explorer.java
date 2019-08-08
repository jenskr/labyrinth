package de.uniwue.gdp.labyrinth;

import de.uniwue.gdp.labyrinth.model.Maze;

import java.util.*;
import java.util.function.Predicate;

public class Explorer {
    private final Predicate<Maze> canGoLeft = maze -> !maze.isWall(Maze.Direction.LEFT);
    private final Predicate<Maze> canGoRight = maze -> !maze.isWall(Maze.Direction.RIGHT);
    private final Predicate<Maze> canGoAhead = maze -> !maze.isWall(Maze.Direction.AHEAD);
    private final Predicate<Maze> isPath = canGoLeft.or(canGoRight).or(canGoAhead);

    public String exploreMaze(Maze z) {
        StringBuilder mazeSb = new StringBuilder();
        mazeSb.append("#".repeat(z.width()));

        while (true) {
            // test whether next step is a junction
            if (!isPath(z)) {
                z.mark(Maze.Direction.BACK);
                // not been here before (no marks in any direction)
                if (z.marks(Maze.Direction.LEFT) == 0 && z.marks(Maze.Direction.RIGHT) == 0 && z.marks(Maze.Direction.AHEAD) == 0 && z.marks(Maze.Direction.BACK) == 1) {
                    int rand = (int) (Math.random() * getAvailableDirections(z).size());
                    int rndDirection = getAvailableDirections(z).get(rand);
                    z.walk(rndDirection);
                    z.mark(Maze.Direction.BACK);
                }

                // been here before, but the direction i am coming from is not marked
                else if (z.marks(Maze.Direction.BACK) == 0) {
                    z.walk(getDirectionWithLeastMarks(z));
                    z.mark(Maze.Direction.BACK);
                }
                // all possible paths have more than 1 (=2) marks --> algorithm finished, break out of loop
                else if (z.marks(Maze.Direction.LEFT) == 2 && z.marks(Maze.Direction.RIGHT) == 2 && z.marks(Maze.Direction.AHEAD) == 2) {
                    System.out.println("Tremaux's Algorithm finished!");
                    break;
                }
                // else take path with least amount of marks
                else {
                    z.walk(getDirectionWithLeastMarks(z));
                    z.mark(Maze.Direction.BACK);
                }
            } else if (isPath(z)) {
                mazeSb.append(" ");
                int dir = getAvailableDirections(z).get(0);
                z.walk(dir);
            } else { //Dead-End
                mazeSb.append("#");
                do {
                    z.walk(Maze.Direction.BACK);
                } while (isPath(z));
            }
        }
        return mazeSb.toString();
    }

    private boolean isPath(Maze maze) {
        return isLeftTurn(maze) || isRightTurn(maze) || isCorridor(maze) || isDeadEnd(maze);
    }

    private boolean isDeadEnd(Maze maze) {
        return maze.isWall(Maze.Direction.RIGHT) && maze.isWall(Maze.Direction.LEFT) && maze.isWall(Maze.Direction.AHEAD);
    }

    private boolean isLeftTurn(Maze maze) {
        return !maze.isWall(Maze.Direction.LEFT) && maze.isWall(Maze.Direction.RIGHT) && maze.isWall(Maze.Direction.AHEAD);
    }

    private boolean isRightTurn(Maze maze) {
        return maze.isWall(Maze.Direction.LEFT) && !maze.isWall(Maze.Direction.RIGHT) && maze.isWall(Maze.Direction.AHEAD);
    }

    private boolean isCorridor(Maze maze) {
        return maze.isWall(Maze.Direction.LEFT) && maze.isWall(Maze.Direction.RIGHT) && !maze.isWall(Maze.Direction.AHEAD);
    }

    private List<Integer> getAvailableDirections(Maze maze) {
        List<Integer> directions = new ArrayList<>();
        if (!maze.isWall(Maze.Direction.LEFT)) {
            directions.add(Maze.Direction.LEFT);
        }
        if (!maze.isWall(Maze.Direction.RIGHT)) {
            directions.add(Maze.Direction.RIGHT);
        }
        if (!maze.isWall(Maze.Direction.AHEAD)) {
            directions.add(Maze.Direction.AHEAD);
        }
        return directions;
    }

    private int getDirectionWithLeastMarks(Maze maze) {
        int leftMarks = maze.marks(Maze.Direction.LEFT);
        int rightMarks = maze.marks(Maze.Direction.RIGHT);
        int aheadMarks = maze.marks(Maze.Direction.AHEAD);

        return Math.min(Math.min(leftMarks, rightMarks), aheadMarks);

    }

}
