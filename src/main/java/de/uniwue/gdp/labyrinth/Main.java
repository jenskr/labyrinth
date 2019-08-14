package de.uniwue.gdp.labyrinth;

import de.uniwue.gdp.labyrinth.examples.Examples;
import de.uniwue.gdp.labyrinth.model.Maze;

import java.nio.file.Path;
import java.nio.file.Paths;

public class Main {
    public static void main(String[] args) {
        Maze maze1 = Examples.example01();
        Maze maze2 = Examples.example02();
        final String mazeOne = "##############           ## ############ # #       ## # # ##### ## #   #   # ## ### # # # ##   # # # # #### # # # # ##   #     # #### ####### ##           ##############";
        final String mazeTwo = "################################                             ## ########################### ## # #             #         # ## # # ##### ### # # # ##### # ## # # #   # #   # # #     # # ## #   # # # # ### # ##### # # ## ### # # # #   #   #     # # ##   # # # # ######### ### # # #### # # # # #     #   #   # # ##   # # # # #       ### ### # #### #     # #     #   #     # ## # ####### ################# ## #                 #       # ## # ####### ####### # ##### # ## #       #       # # #     # ## ####### ####### # # # ##### ##         #       # #         ## ######### ####### ######### ##         #                   ################################";

        Explorer2 mazeExplorer = new Explorer2();
        mazeExplorer.exploreMaze(maze1);
        // Check if mazeOne and mazeExplorer.explore(maze1) are equal
        boolean test1 = mazeOne.equals(mazeExplorer.exploreMaze(maze1));
        mazeExplorer.exploreMaze(maze2);
        boolean test2 = mazeTwo.equals(mazeExplorer.exploreMaze(maze2));
        System.out.println("Finished!");
    }
}
