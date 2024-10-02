package project2;

import java.awt.*;
import java.util.Arrays;
import java.util.Scanner;

public class GumdropGatherer {
    //instance variables for colors.
    static Color background = new Color(40, 40, 40); //background color
    static Color color1 = new Color(8, 112, 153);
    static Color color2 =  new Color(104, 171, 186);
    static Color color3 = new Color(157, 183, 39);
    static Color color4 = new Color(92, 120, 41);
    static Color marked = new Color(249, 250, 237); //marked color, shows up for a split second when a cluster is clicked

    /** half-size of the circles: radius */
    static int radius = 20;
    /** full-horizontal size of the circles */
    static int diameter = radius*2;
    /** buffer between the gumdrops and the canvas */
    static int buffer = radius/2;
    /** maximum points that the player will play to. */
    static int totalPoints = 0;
    /** The current amount of points the player has in the game.*/
    static int currentPoints = 0;
    /** multidimensional array that holds gumdrop values. */
    static int[][] board;


    /** The main control function for the game. */
    public static void main(String[] args) {
        //set up Scanner
        Scanner input = new Scanner(System.in);

        // Get input for rows, columns, and points. use the Scanner input
        System.out.print("How many rows? ");
        int boardRows = input.nextInt();
        System.out.print("How many columns? ");
        int boardCols = input.nextInt();
        System.out.print("Total points? ");
        int totalPoints = input.nextInt();

        //create and initialize array
        board = new int[boardRows][boardCols];
        generateRandomBoard(board); //random values in the array

        //determine size of canvas based on number of rows/columns
        int boardWidth = boardCols*diameter + buffer*2;
        int boardHeight = boardRows*diameter + buffer*2;

        //draw canvas
        SimpleCanvas canvas = new SimpleCanvas(boardWidth, boardHeight);
        canvas.setBackgroundColor(background);
        canvas.show(); // make the canvas visible on the screen.

        //draw canvas based on the board
        draw(canvas, board); // Initialize the board to random numbers (gumdrop colors).

        boolean gameOver = false; //initialize gameOver variable

        // As long as the game is not over:
        while(!gameOver) {
            //print current points
            System.out.println("Points: " + currentPoints); //Print out how many points the player has.
            draw(canvas, board); //draw the board

            //handle click. user can only pick gumdrops with at least one neighbor of the same color
            int[] clicked = handleMouseClick(canvas, board); //CAPTURE RETURN which holds board[row][col]
            board[clicked[0]][clicked[1]] *= -1; //"mark" gumdrop by switching it to its negative value.

            //spread the mark. use a loop to see if there are any more spaces to spread to
            boolean spreadOccured; //initialize
            do{
                spreadOccured = spreadMarked(board); //soread the negative number
                draw(canvas, board); //display spread
                canvas.pause(25);
            } while(spreadOccured);
            spreadMarked(board); //"mark" all matching neighbors into negatives.
            draw(canvas, board);

            canvas.pause(100); //pause to show the marked gumdrops in white.

            //replace marked with empty spaces (zeroes)
            removeMarked(board);
            draw(canvas, board);

            //gravity loop: brings down gumdrops that need to come down.
            boolean gravityOccured; //initialize
            do{
                gravityOccured = gravity(board); //are there gumdrops to drop?
                draw(canvas, board); //shows dropped
                canvas.pause(100); //pause to simulate gravity
            } while(gravityOccured); //if there are more to drop, then the loop will continue.

            //fill empty spaces with random gumdrops
            int numRemoved = fillEmptySquares(board); //captures how many were added
            draw(canvas, board); //full board

            //calculate + update score. check if the user has won.
            currentPoints += calcPoints(numRemoved); //Calculate points earned, keep track of it.
            if(currentPoints >= totalPoints){ //if reached the final score,
                gameOver = true; //end the loop.
            }
        } //the game is over.
        //print the final score.
        System.out.println("Congratulations! You won with " + currentPoints + " points.");
    }


    /** Draws the current state of the gumdrop board onto the canvas. */
    public static void draw(SimpleCanvas canvas, int[][] board) {
        canvas.clear();
        //go through each item in the array
        for (int row = 0; row < board.length; row++) {
            for (int col = 0; col < board[row].length; col++) {
                //set centerX and centerY. to find x, add the width of the columns leading up to it (diameter * (col-1)) and add one radius.
                int centerX = (diameter*col) + radius + buffer; //add buffer space around the edges.
                int centerY = (diameter*row) + radius + buffer;

                //draw a colored circle based on the color in that space
                if (board[row][col] == 1) {
                    canvas.setPenColor(color1);
                    canvas.drawFilledCircle(centerX, centerY, radius);
                }
                else if (board[row][col] == 2) {
                    canvas.setPenColor(color2);
                    canvas.drawFilledCircle(centerX, centerY, radius);
                }else if (board[row][col] == 3) {
                    canvas.setPenColor(color3);
                    canvas.drawFilledCircle(centerX, centerY, radius);
                }
                else if (board[row][col] == 4) {
                    canvas.setPenColor(color4);
                    canvas.drawFilledCircle(centerX, centerY, radius);
                } else { //not colors, meaning it is "marked" or empty
                    if (board[row][col] == 0){ //empty
                        continue; //not draw anything.
                    } else { //negative, "marked"
                        canvas.setPenColor(marked);
                        canvas.drawFilledCircle(centerX, centerY, radius);
                    }
                }
            }
        }
        canvas.update();
    }


    /** After “marking” the initial gumdrop by switching the number of the gumdrop to its
     * negative value, this function “spreads” the negative value by checking all the
     * neighbors (up/down/left/right) of any marked gumdrop and marking neighbors with the
     * matching number (color).
     *
     * @param board contains a negative number, which is the gumdrop that has been clicked. */
    public static boolean spreadMarked(int[][] board){
        boolean justSpread = false; //so that if there are no other neighbors to change, the loop will end.

        for(int row = 0; row < board.length; row++){
            for(int col = 0; col < board[row].length; col++){
                if(board[row][col] < 0){
                    //define the color first.
                    int markedColor = -board[row][col];

                    if ((row > 0) && (markedColor == board[row-1][col])) { //above
                        board[row-1][col] *= -1;
                        justSpread = true;
                    }
                    if ((col < board[row].length-1) && (markedColor == board[row][col+1])) { //right
                        board[row][col+1] *= -1;
                        justSpread = true;
                    }
                    if ((row < board.length-1) && (markedColor == board[row+1][col])) { //below
                        board[row+1][col] *= -1;
                        justSpread = true;
                    }
                    if ((col > 0) && (markedColor == board[row][col-1])) { //left
                        board[row][col-1] *= -1;
                        justSpread = true;
                    }
                }
            }
        }
        return justSpread; //main will take this and determine whether to run this function again or not.
    }


    /** Fills the initially empty array with random numbers. Called in main when the board needs to be initialized
     * @param board: is the board. empty when passed through. */
    public static void generateRandomBoard(int[][] board){
        //go through each space in the board
        for(int row = 0; row < board.length; row++){
            for(int col = 0; col < board[row].length; col++){
                int randomNum = (int) ((Math.random() * 4) + 1); //generate random number (between 1-4)
                board[row][col] = randomNum; //add to array
            }
        }
    }

    /** Allows the user to click on a gumdrop. This function will not end until the user
     * selects a gumdrop with at least one neighbor of the same color.
     * Note: clickedRowCol are indices, so they will be one less than the "number" of the row/col.
     * Returns a 2-element integer array of the row and column that was clicked. */
    public static int[] handleMouseClick(SimpleCanvas canvas, int[][] board) {
        //initialize array containing coordinates
        int[] clickedRowCol = new int[2];

        //loop to ensure the user cannot select a gumdrop with no neighbors of the same color
        boolean madeValidMove = false;
        while(!madeValidMove) {
            canvas.waitForClick();

            //get x and y-coordinates of click
            int mouseX = canvas.getMouseClickX();
            int mouseY = canvas.getMouseClickY();

            //convert coordinates to row/col numbers.
            int row = (mouseY / diameter); //y-value determines the row
            int col = (mouseX / diameter); //x-value determines the column

            //store the values
            clickedRowCol[0] = row;
            clickedRowCol[1] = col;

            //determine if move was valid. if not, the loop will run again.
            if(hasMatchingNeighbor(board,row,col)){
                madeValidMove = true; //will exit loop.
            } else { //move invalid
                System.out.println("Click one with a neighbor!"); //prompt user to choose another
            }
        }
        return clickedRowCol; //needed in the main function. contains coordinates of the valid gumdrop. format: [row, col]
    }


    /** After removing marked gumdrops, this function iteratively lowers any "floating" gumdrops
     * until they are resting on top of existing ones.
     * The main function uses the return to determine whether to run gravity again or not.
     *
     * @param board: contains zeros for removed gumdrops
     * @return whether gravity happened or not (true/false) */
    public static boolean gravity(int[][] board){
        boolean gravityOccured = false; //initialize

        for(int row = 0; row < board.length; row++){
            for(int col = 0; col < board[row].length; col++){
                //if the square:
                if(board[row][col] == 0){ //is empty
                    if (row > 0) { //is in range
                        if(board[row - 1][col] != 0){ //has a filled space above it

                            board[row][col] = board[row - 1][col]; // bring the number down
                            board[row - 1][col] = 0; // make the space a zero
                            gravityOccured = true;
                        }
                    }
                }

            }
        }

        return gravityOccured;
    }


    /** This function returns a boolean indicating whether the gumdrop at (row, col)
     * has a neighboring gumdrop of the same color or not. This function will be used in
     * handleMouseClick to detect if a gumdrop that the player clicked on has a matching
     * neighbor of the same color. If there isn’t a matching neighbor, the function will
     * return false, and the game won’t allow the user to select that gumdrop.
     *
     * @param board the array containing the board
     * @param row row number of clicked gumdrop
     * @param col column number of clicked gumdrop
     * @return whether or not the selected gumdrop has a matching neighbor*/
    public static boolean hasMatchingNeighbor(int[][] board, int row, int col) {

        //color of gumdrop that was clicked
        int clickedColor = board[row][col]; //either 1, 2, 3, or 4 (num indicates color)

        //check in all four directions
        if ((row > 0) && (clickedColor == board[row - 1][col])) { return true; }//above
        if ((col < board[row].length - 1) && (clickedColor == board[row][col + 1])) { return true; }//right
        if ((row < board.length - 1) && (clickedColor == board[row + 1][col])) { return true; }//below
        if ((col > 0) && (clickedColor == board[row][col - 1])) { return true; }//left

        return false; //if the code gets this far, none of them match.
    }


    /** This function returns the number of empty squares were filled after the empty spaces were filled.
     *
     * @param board uses code from the random generated board function.
     * @return the number of gumdrops that were added. */
    public static int fillEmptySquares(int[][] board){
        int numAdded = 0; //keeps track of how many empty spaces were filled

        for(int row = 0; row < board.length; row++){
            for(int col = 0; col < board[row].length; col++){
                //if the number is zero
                if(board[row][col] == 0){
                    int randomNum = (int) ((Math.random() * 4) + 1); //generate a random number between 1-4.
                    board[row][col] = randomNum; //add the number to the array of gumdrops
                    numAdded++; //increase added count by 1
                }
            }
        }
        return numAdded; //returns. main captures it and uses it to determine how many gumdrops were removed.
    }


    /** This function calculates how many points the player earns for removing however many gumdrops they removed.
     *
     * @param gumdrops is the number of gumdrops in the group that was clicked. */
    public static int calcPoints(int gumdrops){
        int points = 0; //initialize the points earned from the cluster
        int bonus;

        //based on the number of gumdrops in a location, the bonus increases.
        if(gumdrops < 5){ //2-4 gumdrops
            bonus = 10;
        } else if (gumdrops < 10){ //5-9 gumdrops
            bonus = 50;
        } else { //10+ gumdrops
            bonus = 100;
        }

        //calculate the points by multiplying
        points += (gumdrops*bonus);
        return points;
    }


    /** This function is used to remove marked (negative) gumdrops into empty spaces (zeroes)
     * after everything that needs to be marked has been marked.
     *
     * @param board is the board before negative numbers are "removed"
     * @return the board after the negative numbers are changed to zero. */
    public static int[][] removeMarked(int[][] board){
        //for each item in the board
        for(int row = 0; row < board.length; row++){
            for(int col = 0; col < board[row].length; col++){
                //check if the number is negative.
                if(board[row][col] < 0){ //if it is,
                    board[row][col] = 0; //"remove" the gumdrop by changing it to zero
                }
            }
        }
        return board; //return the board to store in the called function.
    }

}
