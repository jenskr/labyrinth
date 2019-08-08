package de.uniwue.gdp.labyrinth;

import de.uniwue.gdp.labyrinth.model.Maze;

import java.util.function.Predicate;

public class Explorer2 {
    private final Predicate<Maze> canGoLeft = maze -> !maze.isWall(Maze.Direction.LEFT);
    private final Predicate<Maze> canGoRight = maze -> !maze.isWall(Maze.Direction.RIGHT);
    private final Predicate<Maze> canGoAhead = maze -> !maze.isWall(Maze.Direction.AHEAD);
    final int[] directions = {Maze.Direction.LEFT, Maze.Direction.AHEAD, Maze.Direction.RIGHT};

    public String exploreMaze(Maze z) {
        while (true) {
            if (isJunction().test(z)) {
                z.mark(Maze.Direction.BACK);
                if (junctionAlreadyVisited(z)) {
                    if (z.marks(Maze.Direction.BACK) == 1) {
                        do {
                            z.walk(Maze.Direction.BACK);
                        } while (isPath().test(z));
                    } else {
                        if (canGoLeft.test(z) && z.marks(Maze.Direction.LEFT) == 0) {
                            z.walk(Maze.Direction.LEFT);
                        } else if (canGoRight.test(z) && z.marks(Maze.Direction.RIGHT) == 0) {
                            z.walk(Maze.Direction.RIGHT);
                        } else if (canGoAhead.test(z) && z.marks(Maze.Direction.AHEAD) == 0) {
                            z.walk(Maze.Direction.AHEAD);
                        } else {
                            for (int dir : directions) {
                                if (z.marks(dir) == 1 && canGoInDirection(dir).test(z)) {
                                    z.walk(dir);
                                    break;
                                }
                            }
                        }
                    }
                }
                else {
                    // junction not already visited

                }
            }
        }
    }

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
        return canGoLeft.and(canGoAhead.negate()).and(canGoRight.negate()).or(canGoRight.and(canGoLeft.negate()).and(canGoAhead.negate())).or(canGoAhead.and(canGoLeft.negate()).and(canGoRight.negate()));
    }

    private Predicate<Maze> isDeadEnd() {
        return canGoLeft.and(canGoRight).and(canGoAhead).negate();
    }

    private boolean junctionAlreadyVisited(Maze maze) {
        int[] directions = {Maze.Direction.LEFT, Maze.Direction.RIGHT, Maze.Direction.AHEAD};
        for (int dir : directions) {
            if (maze.marks(dir) > 0) {
                return true;
            }
        }
        return false;
    }


}
