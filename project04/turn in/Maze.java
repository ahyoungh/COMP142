package project4;

/* Ahyoung Hwang - I have neither given nor received unauthorized aid on this program. */

import java.awt.*;
import java.io.InputStream;
import java.util.Scanner;

public class Maze {
    /** The canvas the maze will be drawn on. */
    private SimpleCanvas canvas;

    /** Stores the maze information in a 2D array. Read in within loadMaze(filename) based on file name. */
    public char[][] mazeMap;

    /** The dimensions of each square. */
    public final int SQSIZE = 30;

    /** The width of the maze. adjusted to match number of columns and square size. */
    public int mazeWidth;

    /** The height of the maze. adjusted to match number of rows and square size. */
    public int mazeHeight;

    /** Row Harry starts in; patronus's starting position. */
    public int harryRow;

    /** Row Harry starts in; patronus's starting position. */
    public int harryCol;

    /** If using directional solver, keeps track of directions. */
    public String directions = "";

    /** The canvas pause time. Set in RunTournament based on user input. */
    public int pauseTime;

    /** The number of times the recursive call is called. Incremented every time canSolve() or directionalSolve runs. */
    public int numCalls;

    /** Color of the dropped patronuses. */
    Color LIGHT_BLUE = new Color(151, 215, 255);

    /** constructor. takes filename, loads the file, creates canvas */
    public Maze(String filename) { //ADD PARAMETERS AS NEEDED
        loadMaze(filename);

        canvas = new SimpleCanvas(mazeWidth, mazeHeight); //width and height changed (to reflect numRows/cols) in loadMaze
        canvas.show();
    }

    /** Load a maze (read it in) from the given text file and store into appropriate
     * instance variables. */
    public void loadMaze(String filename) {
        InputStream is = Maze.class.getResourceAsStream(filename);
        if (is == null) {
            System.err.println("Could not open file: " + filename);
            System.exit(1);
        }
        Scanner scan = new Scanner(is);

        // from the first line in text file, read the number of rows / cols
        int rows = scan.nextInt();
        int cols = scan.nextInt();

        //set the height and width based on the num of rows and cols.
        mazeWidth = cols * SQSIZE;
        mazeHeight = rows * SQSIZE;

        //allocate memory space for the given number of rows and columns
        mazeMap = new char[rows][cols];

        scan.nextLine(); // skips enter keypress

        //CONVERTING FILE INTO MAP

        int row = 0; //initialize counter variable

        while (scan.hasNextLine()) {
            //scan in the next line (full string)
            String line = scan.nextLine();

            for(int col = 0; col < line.length(); col++){ //line.length() is equal to the number of columns)
                char curr = line.charAt(col); //the current character
                mazeMap[row][col] = curr; //assign to space in the map
            }
            row++; //next row
        }
    }

    /** Prints the maze as stored in text files. */
    public void printMaze() {
        for(int row = 0; row < mazeMap.length; row++){
            for(int col = 0; col < mazeMap[row].length; col++){
                System.out.print(mazeMap[row][col]);
            }
            System.out.println();
        }
    }

    /** Draw the maze on the canvas. */
    public void drawMaze() {
        canvas.clear();

        //initialize x and y coordinates
        int x = 0;
        int y = 0;

        //maze drawing code
        for(int row = 0; row < mazeMap.length; row++){
            for(int col = 0; col < mazeMap[0].length; col++){
                x = col * SQSIZE; // coordinates
                y = row * SQSIZE;

                if(mazeMap[row][col] == '#'){
                    drawPart(x, y,"wall");
                } else if (mazeMap[row][col] == ' '){
                    drawPart(x, y,"space");
                } else if (mazeMap[row][col] == 'H'){
                    drawPart(x, y,"harry");
                } else if (mazeMap[row][col] == 'C'){
                    drawPart(x, y,"cup");
                }
            }
        }
        canvas.update();
    }

    /** Draws a square based on what part of the maze it is. */
    public void drawPart(int x, int y, String type) {
        if(type.equals("wall")){ canvas.setPenColor(Color.GRAY); }
        else if (type.equals("space")){ canvas.setPenColor(Color.WHITE); }
        else if (type.equals("harry")){ canvas.setPenColor(Color.RED); }
        else if (type.equals("cup")){ canvas.setPenColor(Color.YELLOW); }
        canvas.drawFilledRectangle(x, y, SQSIZE, SQSIZE);
    }

    /** Draw the maze on the canvas, including the current position of the patronus. */
    public void drawMazeWithPatronus(int patronusRow, int patronusCol) {
        drawMaze(); // draw maze first

        //draw the initial patronus
        canvas.setPenColor(Color.BLUE);
        canvas.drawFilledCircle((patronusCol*SQSIZE)+(SQSIZE/2), (patronusRow*SQSIZE)+(SQSIZE/2), SQSIZE/6); //calculations so that it is centered

        //draws breadcrumbs at coordinates given in the map
        for(int row = 0; row < mazeMap.length; row++){
            for(int col = 0; col < mazeMap[row].length; col++){
                if(mazeMap[row][col] == '.') {
                    canvas.setPenColor(LIGHT_BLUE);
                    canvas.drawFilledCircle((col*SQSIZE)+(SQSIZE/2), (row*SQSIZE)+(SQSIZE/2), SQSIZE/6);
                }
            }
        }
        canvas.update();
    }

    /** Initial function to get the recursion started for recursive formulation 1:
     * Is it possible to solve this maze? */
    public boolean canSolve() {
        numCalls = 0;

        //find and print Harry's location
        findHarry();
        System.out.println("Harry is at: (" + harryRow + ", " + harryCol + ")");

        drawMaze(); //draw initial maze
        canvas.waitForClick();

        return canSolve(harryRow, harryCol);
    }

    /** Helper function for canSolve that takes the current position of the patronus
     * as parameters. */
    private boolean canSolve(int patronusRow, int patronusCol) {
        numCalls++; //increment number of calls

        //for shorter + easier code
        int row = patronusRow;
        int col = patronusCol;

        System.out.println("The patronus has arrived at: (" + row + ", " + col + ")"); //print that the patronus has arrived
        drawMazeWithPatronus(row, col);//draw maze with patronus
        canvas.pause(pauseTime); //pause canvas for a moment

        char curr = mazeMap[row][col]; //patronus's current place in the maze

        if(curr == 'C'){ //base case: patronus at cup; cup found
            System.out.println("The maze can be solved.");
            return true;
        } else {
            mazeMap[row][col] = '.'; //drop breadcrumb at current location
            //check north
            if (row > 0 && (mazeMap[row-1][col] == ' ' || mazeMap[row-1][col] == 'C')) { //can patronus move north?
                if (canSolve(row-1, col)) {
                    return true; //solution found
                }
            }
            //check south
            if (row < mazeMap.length && (mazeMap[row+1][col] == ' ' || mazeMap[row+1][col] == 'C')) { //can patronus move south?
                if (canSolve(row+1, col)) {
                    return true; //solution found
                }
            }
            //check east
            if (col < mazeMap[row].length && (mazeMap[row][col+1] == ' ' || mazeMap[row][col+1] == 'C')) { //can patronus move east?
                if (canSolve(row, col+1)) {
                    return true; //solution found
                }
            }
            //check west
            if (col > 0 && (mazeMap[row][col-1] == ' ' || mazeMap[row][col-1] == 'C')) { //can patronus move west?
                if (canSolve(row, col-1)) {
                    return true; //solution found
                }
            }

            //DEAD END
            //if we get here, patronus cannot find cup from current position.
            System.out.println("Dead end at: (" + row + ", " + col + "). Backtracking.");
            mazeMap[row][col] = ' '; //pick up breadcrumb. they only mark the solution

            //BACKTRACKING
            drawMazeWithPatronus(row, col);//draw the maze with the patronus.
            canvas.pause(pauseTime);//pause the canvas for a moment.
            return false; //indicating the patronus failed, and should backtrack if possible.
        }
    }

    /** Initial function to get the recursion started for recursive formulation 2:
     * What are the directions (sequence of N/S/E/W steps) to solve the maze?
     * Return: string containing all the steps to the cup. (eg. "ESW" = "east-south-west") */
    public String directionalSolve() {
        numCalls = 0; //initialize recursive calls

        //find and print Harry's location
        findHarry();
        System.out.println("Harry is at: (" + harryRow + ", " + harryCol + ")");

        drawMaze(); //initial maze
        canvas.waitForClick();

        String finalDirections = directionalSolve(harryRow, harryCol);
        return finalDirections.substring(0, finalDirections.length()-1); //remove last character bc it is 'C'
    }

    /** Helper function for directionalSolve(). */
    private String directionalSolve(int patronusRow, int patronusCol) {
        numCalls++;

        int row = patronusRow;
        int col = patronusCol;

        System.out.println("The patronus has arrived at: (" + row + ", " + col + ")");         // Print that the patronus has arrived here.
        drawMazeWithPatronus(row, col);// Draw the maze with the patronus.
        canvas.pause(pauseTime); // Pause the canvas for a moment.

        char curr = mazeMap[row][col]; //keeps track of current position and value

        if(curr == 'C'){ //base case: is search over?
            System.out.println("Cup found at: (" + row + ", " + col + ")");

            directions = "C" + directions;
            return directions; //the patronus is at the cup, cup found
        } else {
            //drop breadcrumb at current location
            mazeMap[row][col] = '.';

            //check north
            if (row > 0 && (mazeMap[row-1][col] == ' ' || mazeMap[row-1][col] == 'C')) { //can patronus move north?
                if (directionalSolve(row-1, col) != "X") { //yes, and path has solution
                    directions = "N" + directions;
                    return directions; //solution found north
                }
            }
            //check south
            if (row < mazeMap.length && (mazeMap[row+1][col] == ' ' || mazeMap[row+1][col] == 'C')) { //can patronus move south?
                if (directionalSolve(row+1, col) != "X") { //if solvable
                    directions = "S" + directions;
                    return directions; //solution found south
                }
            }
            //check east
            if (col < mazeMap[row].length && (mazeMap[row][col+1] == ' ' || mazeMap[row][col+1] == 'C')) { //can patronus move east?
                if(directionalSolve(row, col+1) != "X"){
                    directions = "E" + directions;
                    return directions; //solution found east
                }
            }
            //check west
            if (col > 0 && (mazeMap[row][col-1] == ' ' || mazeMap[row][col-1] == 'C')) { //can patronus move west?
                if (directionalSolve(row, col-1) != "X") { //if solvable
                    directions = "W" + directions;
                    return directions; //solution found west
                }
            }

        }

        //DEAD END
        //if we get here, patronus cannot find cup from current position.
        System.out.println("Dead end at: (" + row + ", " + col + "). Backtracking.");
        mazeMap[row][col] = ' '; //pick up breadcrumb. they only mark the solution

        //BACKTRACKING
        drawMazeWithPatronus(row, col);// Draw the maze with the patronus.
        canvas.pause(pauseTime);// Pause the canvas for a moment.
        return "X"; // indicating the patronus failed, and should backtrack if possible.
    }

    /** Sets instance variables harryRow and harryCol to Harry's current location in the maze. */
    public void findHarry(){
        harryRow = 0;
        harryCol = 0;
        for(int row = 0; row < mazeMap.length; row++){
            for(int col = 0; col < mazeMap[row].length; col++){
                char ch = mazeMap[row][col];
                if(ch == 'H'){
                    harryRow = row;
                    harryCol = col;
                }
            }
        }
    }

    /** Used to set pause time to a user-inputted number. */
    public void setPauseTime(int time){
        pauseTime = time;
    }

    /** Used in RunTournament to get the number of times the recursive method was called. */
    public int getNumCalls(){
        return numCalls;
    }
}


