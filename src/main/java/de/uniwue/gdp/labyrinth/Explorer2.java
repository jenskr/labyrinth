package de.uniwue.gdp.labyrinth;

import de.uniwue.gdp.labyrinth.model.Maze;

import java.util.*;
import java.util.function.Predicate;

public class Explorer2 {
    private final Predicate<Maze> canGoLeft = maze -> !maze.isWall(Maze.Direction.LEFT) && maze.marks(Maze.Direction.LEFT) < 2;
    private final Predicate<Maze> canGoRight = maze -> !maze.isWall(Maze.Direction.RIGHT) && maze.marks(Maze.Direction.RIGHT) < 2;
    private final Predicate<Maze> canGoAhead = maze -> !maze.isWall(Maze.Direction.AHEAD) && maze.marks(Maze.Direction.AHEAD) < 2;
    private final Predicate<Maze> canGoBack = maze -> !maze.isWall(Maze.Direction.BACK) && maze.marks(Maze.Direction.BACK) < 2;
    final int[] directions = {Maze.Direction.LEFT, Maze.Direction.AHEAD, Maze.Direction.RIGHT};
    int currentDir = 1;
    int currentX = 1;
    int currentY = 1;
    boolean pointingDown = true;
    boolean pointingRight = false;
    int counter = 0;

    public String exploreMaze(Maze z) {
        String[][] arr = initializeDefaultMaze(z);
        z.walk(Maze.Direction.AHEAD);
        while (true) {
            counter++;
            if (isJunction().test(z)) {
                z.mark(Maze.Direction.BACK);
                if (junctionAlreadyVisited(z)) {
                    if (z.marks(Maze.Direction.BACK) == 1) {
                        walkToLastJunction(z);
                    } else {
                        if (allDirectionsMoreThanOneMark(z)) {
                            System.out.println("Tremaux's Algorithm finished!");
                            break;
                        } else {
                            int leastMarkDir = getDirectionWithLeastMarks(z);
                            z.mark(leastMarkDir);
                            z.walk(leastMarkDir);
                        }
                    }
                } else {
                    // junction not already visited
                    // no randomness needed, just pick ANY available direction
                    // int rnd = new Random().nextInt(getAvailableDirections(z).size());
                    // int dir = getAvailableDirections(z).get(rnd);
                    int dir = getAvailableDirections(z).get(0);
                    z.mark(dir);
                    z.walk(dir);
                }
            } else {
                // no junction (--> path), only one direction that can be taken
                int dir = getAvailableDirections(z).get(0);
                z.walk(dir);
            }
        }
        return "";
    }

    // can be deleted after redefining the class fields canGoLeft, canGoRight and canGoAhead
    private Predicate<Maze> canGoInDirection(int direction) {
        if (direction == 0) {
            return canGoLeft.and(maze -> maze.marks(direction) < 2);
        } else if (direction == 1) {
            return canGoAhead.and(maze -> maze.marks(direction) < 2);
        } else if (direction == 2) {
            return canGoRight.and(maze -> maze.marks(direction) < 2);
        } else return maze -> maze.marks(direction) < 2;
    }

    private Predicate<Maze> isJunction() {
        return canGoLeft.and(canGoRight).or(canGoLeft.and(canGoAhead)).or(canGoRight.and(canGoAhead)).or(canGoLeft.and(canGoRight).and(canGoAhead));
    }

    private Predicate<Maze> isPath() {
        // or return simply isJunction().negate() ???
        return canGoLeft.and(canGoAhead.negate()).and(canGoRight.negate()).or(canGoRight.and(canGoLeft.negate()).and(canGoAhead.negate())).or(canGoAhead.and(canGoLeft.negate()).and(canGoRight.negate()));
    }

    private Predicate<Maze> isDeadEnd() {
        return canGoLeft.negate().and(canGoRight.negate()).and(canGoAhead.negate());
    }

    private boolean junctionAlreadyVisited(Maze maze) {
        // could also use getAvailableDirections() here
        //int[] directions = {Maze.Direction.LEFT, Maze.Direction.RIGHT, Maze.Direction.AHEAD};
        for (int dir : this.directions) {
            if (maze.marks(dir) > 0) {
                return true;
            }
        }
        return false;
    }

    private void walkInDirectionWithOneMark(Maze maze) {
        for (int dir : directions) {
            if (maze.marks(dir) == 1 && canGoInDirection(dir).test(maze)) {
                maze.walk(dir);
                maze.mark(Maze.Direction.BACK);
                break;
            }
        }
    }

    private int getDirectionWithLeastMarks(Maze maze) {
        Map<Integer, Integer> allDirs = new HashMap<>();
        for (int d : getAvailableDirections(maze)) {
            allDirs.put(d, maze.marks(d));
        }

        return allDirs.get(Collections.min(allDirs.values()));

    }

    private List<Integer> getAvailableDirections(Maze maze) {
        List<Integer> directions = new ArrayList<>();
        if (canGoLeft.test(maze)) {
            directions.add(Maze.Direction.LEFT);
        }
        if (canGoRight.test(maze)) {
            directions.add(Maze.Direction.RIGHT);
        }
        if (canGoAhead.test(maze)) {
            directions.add(Maze.Direction.AHEAD);
        }
        if (canGoBack.test(maze)) {
            directions.add(Maze.Direction.BACK);
        }
        return directions;
    }

    private void walkToLastJunction(Maze maze) {
        maze.walk(Maze.Direction.BACK);
        do {
            maze.walk(Maze.Direction.AHEAD);
        } while (maze.marks(Maze.Direction.BACK) == 0);
        maze.mark(Maze.Direction.BACK);
    }

    private boolean allDirectionsMoreThanOneMark(Maze maze) {
        for (int dir : getAvailableDirections(maze)) {
            if (maze.marks(dir) < 2) {
                return false;
            }
        }
        return true;
    }

    private String[][] initializeDefaultMaze(Maze maze) {
        String[][] mazeArray = new String[maze.width()][maze.height()];
        for (int i = 0; i < maze.width(); i++) {
            for (int j = 0; j < maze.height(); j++) {
                mazeArray[i][j] = "#";
            }
        }
        return mazeArray;
    }

    private void updateCurrentPos(int direction) {
        if (direction == Maze.Direction.LEFT) {
            if (pointingDown) {
                this.currentX++;
            } else if (pointingRight) {
                this.currentY++;
            } else if (!pointingRight) {
                this.currentY--;
            } else this.currentX--;
        }
    }


}
