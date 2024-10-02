package project4;

/* Ahyoung Hwang - I have neither given nor received unauthorized aid on this program. */

import java.util.Scanner;

public class RunTournament {

    public static void main(String[] args){
        //testFileReading(); //DONE
        //testDrawing(); //DONE
        //testDrawingPatronus(); //DONE
        //testBooleanSolver(); //DONE
        //testDirectionalSolver(); //DONE

        fullProgram();
    }

    public static void fullProgram(){
        Scanner input = new Scanner(System.in);

        //PROMPT USER:
        System.out.print("File name: ");
        String filename = input.nextLine(); //filename
        System.out.print("Pause time: ");
        int inputPause = input.nextInt(); //take pause time
        System.out.print("Solver 1 (boolean) or 2 (directional): ");
        int solver = input.nextInt(); //which solver to use

        //MAZE: create and print
        Maze maze = new Maze(filename);
        maze.printMaze(); //before solved (empty)
        maze.setPauseTime(inputPause); //set pause time

        //SOLVE MAZE
        if(solver == 1){
            boolean solved = maze.canSolve();
            if(!solved){
                System.out.println("The maze cannot be solved.");
            }
        } else if(solver == 2){
            String solution = maze.directionalSolve();
            if(solution.contains("C")){
                System.out.println("The solution is " + solution + " and has " + solution.length() + " steps.");
            } else {
                System.out.println("The maze cannot be solved.");
            }
        }
        System.out.println("Number of calls: " + maze.getNumCalls()); //prints final number of calls
        maze.printMaze(); //after solved (should have breadcrumbs)
    }


    public static void testFileReading() {
        Maze m = new Maze("maze0.txt");
        m.printMaze(); //print the maze
    }

    public static void testDrawing() {
        Maze m = new Maze("maze2.txt");
        m.printMaze();
        m.drawMaze();
    }

    public static void testDrawingPatronus() {
        Maze m = new Maze("maze1.txt");
        m.printMaze();
        m.drawMazeWithPatronus(0, 2);
    }

    public static void testBooleanSolver() {
        Maze m = new Maze("maze0.txt");
        m.canSolve();
    }

    public static void testDirectionalSolver() {
        Maze m = new Maze("maze2.txt");
        String solution = m.directionalSolve(); //store the solution.
        System.out.println("The solution is " + solution + " and has " + solution.length() + " steps.");
    }
}
