import java.util.Scanner; //import the Scanner class

public class Nim {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in); //create a Scanner object

        //The game begins with three piles, labeled A, B, and C.
        // Pile A starts with 5 sticks, B with 4, and C with 3.
        int[] pile = {5, 4, 3}; // create three piles.
        int A = 0, B = 1, C = 2; // Variables A, B, C correspond to each pile index. (A = 0 = first pile, etc.)
        //OR String[] pileNames = {A, B, C} and use parallel lists

        // variable currPlayer keeps track of the current player.
        int currPlayer = 1; // player 1 starts, so initialized to 1.

        boolean gameOver = false; //initialize while loop for continuous back-and-forth gameplay.
        while (!gameOver) {
            //PRINT STATS (currPlayer)
            System.out.println ("It's player " + currPlayer + "'s turn."); //print to indicate which player's turn it is.
            //print current board, " A B C\n# # #". use array pile
            System.out.println ("Here is the board:\nA B C");
            System.out.println (pile[A] + " " + pile[B] + " " + pile[C]);

            //TAKE PILE INPUT (takePile, validPile, currPile)
            //ask user which pile they want to take from
            System.out.print ("Enter the pile: ");
            String takePile = input.next(); //stores which pile the user wants to take from
            boolean validPile = false; //to initialize input while loop
            int currPile = -1; //initialize the pile that the user will take from

            //CHECK PILE INPUT (validPile, takePile)
            //check if input is A, B, or C. require input again if not.
            while (!validPile) {
                if (!takePile.equals("A")) { //input is not A
                    if (!takePile.equals("B")) { //input is not A, not B
                        if (!takePile.equals("C")) { //input is not A, not B, not C. invalid.
                            System.out.print ("Invalid pile. Enter A, B, or C: ");
                            takePile = input.next();
                        } else { //input is C
                            validPile = true;
                            currPile = pile[C];
                        }
                    } else { //input is B
                        validPile = true;
                        currPile = pile[B];
                    }
                } else { //input is A
                    validPile = true;
                    currPile = pile[A];
                }
            }

            //TAKE NUMBER OF STICKS INPUT (takePile, takeSticks, validSticks, currPile)
            // ask user for number of sticks, and check whether that number is valid.
            System.out.print ("How many sticks would you like to remove from pile " + takePile + "? ");
            int takeSticks = input.nextInt();
            boolean validSticks = false;
            //CHECK NUMSTICKS INPUT
            //check if the number is appropriate.
            while (!validSticks) {
                if ((takeSticks > 0) && (takeSticks <= currPile)) { // input works. exit input check while loop
                    validSticks = true;
                } else {
                    if (takeSticks <= 0) { //number is zero, or negative.
                        System.out.println ("You must take at least one stick.");
                    } else { //user is trying to take too many sticks
                        System.out.println ("You cannot take more than " + currPile + " sticks.");
                    }
                    System.out.print ("Enter a number between 1 and " + currPile + ": ");
                    takeSticks = input.nextInt();
                }
            }

            //REMOVE STICKS FROM PILE
            //remove corresponding number of sticks from corresponding pile.
            if (takePile.equals("A")) { //taking from pile A
                pile[A] -= takeSticks; //remove desired number of sticks from A (pile[A])
            } else if (takePile.equals("B")) { //taking from pile B
                pile[B] -= takeSticks;
            } else { //taking from pile C
                pile[C] -= takeSticks;
            }

            //SWITCH PLAYERS
            if (currPlayer == 1) { //player 1 just played.
                currPlayer = 2; //now it is player 2's turn.
            } else { //player 2 just played.
                currPlayer = 1; //now it is player 1's turn.
            }

            //CHECK: IS GAME OVER?
            //add piles.
            int pileSum = 0;
            //goes through each index in the array holding the sticks, adds the corresponding values up.
            for (int i = 0; i < pile.length; i++) {
                pileSum += pile[i];
            } //now pileSum holds the sum of all sticks in all piles.

            System.out.println (); //print a new line to indicate new player's turn.

            //If there are no sticks left, game over!
            if (pileSum == 0) { //if there are no sticks left, then the "current player" wins. bc the prev. player lost.
                gameOver = true;
                System.out.println ("Player " + currPlayer + " wins!");
            }
        }
    }
}