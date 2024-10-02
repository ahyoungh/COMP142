package project5;
/* Ahyoung Hwang - I have neither given nor received unauthorized aid on this program. */

import java.util.ArrayList;
import java.util.Scanner;

public class DanceTester {
    /**The time bugs pause before exectuing next dance move. User
     * input keeps/changes the time to faster/slower. */
    private static int pause = 542; //inital pause time = normal pause time

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        //get INPUT - dance move options
        System.out.println("Choose from the following dance moves: ");
        System.out.print("(1)spin (2)routine (3)square (4)imitation of spin (5)imitation of routine (6)mirror of square (7)conga! ");
        int danceMove = input.nextInt(); //store value

        //get INPUT - pause time option
        System.out.print("Would you like a (1)slow, (2)normal, or (3)fast dance? ");
        int pauseOption = input.nextInt(); //store values
        readPauseOption(pauseOption, input); //sets instance variable to corresponding pause time

        //call dance
        doDance(danceMove, input);
    }

    /** Translates the user-inputted pause option to the corresponding
     * pause time. Called in main. */
    public static void readPauseOption(int pauseOption, Scanner input){
        if(pauseOption < 1 || pauseOption > 3){ //out of range
            System.out.print("Enter a valid number between 1-3: ");
            pauseOption = input.nextInt();
            doDance(pauseOption, input); //check again
        }

        if(pauseOption == 1){ //slow
            pause = 1000;
        } else if (pauseOption == 2){
            pause = 542;
        } else if (pauseOption == 3) {
            pause = 200;
        }
    }
    /** Executes the user-inputted dance option. Input taken from main. */
    public static void doDance(int option, Scanner input){

        if(option < 1 || option > 7){ //out of range
            System.out.print("Enter a valid number between 1-7: ");
            option = input.nextInt();
            doDance(option, input); //check again
        }

        if(option == 1){
            System.out.println("Running spin.");
            testSpinBug();
        } else if (option == 2){
            System.out.println("Running routine.");
            testRoutineBug();
        } else if (option == 3) {
            System.out.println("Running square.");
            testSquareBug();
        } else if (option == 4) {
            System.out.println("Running imitation of spin.");
            testImitationBug();
        } else if (option == 5) {
            System.out.println("Running imitation of routine.");
            testImitationBug2();
        } else if (option == 6) {
            System.out.println("Running mirror of square.");
            testMirrorBug();
        } else if (option == 7) {
            System.out.println("Running conga!!");
            testCongaLine();
        }
    }

    /*DANCES*/
    /** Line of bugs that follow each other. */
    public static void testCongaLine(){
        DanceFloor floor = new DanceFloor(9, 9);
        floor.setPauseTime(pause);

        SquareBug squarebug = new SquareBug(5,1, 4);
        CongaBug congabug = new CongaBug(6,1, squarebug);
        CongaBug congacongabug = new CongaBug(7, 1, congabug);

        squarebug.setSteps(); //the moves!!

        floor.addDancer(squarebug);
        floor.addDancer(congabug);
        floor.addDancer(congacongabug);

        floor.everyoneDance();
    }

    /** Mirrorbug mirrors a squareBug */
    public static void testMirrorBug() {
        DanceFloor floor = new DanceFloor(7, 7);
        floor.setPauseTime(pause);

        //create bugs
        SquareBug squarebug = new SquareBug(5,1, 4);
        MirrorBug mirrorbug = new MirrorBug (5, 5, squarebug);

        squarebug.setSteps(); //the moves!!

        //add to floor
        floor.addDancer(squarebug);
        floor.addDancer(mirrorbug);

        floor.everyoneDance();
    }

    /** Tests imitationbug on routinebug*/
    public static void testImitationBug2() {
        DanceFloor floor = new DanceFloor(15, 15);
        floor.setPauseTime(pause);

        //create bugs. imitationbug should copy spinbug
        RoutineBug routinebug = new RoutineBug(4, 4); //the yellow one
        ImitationBug imitationbug = new ImitationBug(9,9, routinebug); //spinbug is the leader

        routinebug.setSteps(); //set the moves!!

        //add bugs
        floor.addDancer(routinebug);
        floor.addDancer(imitationbug);

        //party!
        floor.everyoneDance();
    }

    /** Tests imitationbug on spinbug. */
    public static void testImitationBug() {
        DanceFloor floor = new DanceFloor(7, 7);
        floor.setPauseTime(pause);

        //create bugs. imitationbug should copy spinbug
        SpinBug spinbug = new SpinBug(2, 2); //the yellow one
        ImitationBug imitationbug = new ImitationBug(4,4, spinbug); //spinbug is the leader

        //add bugs
        floor.addDancer(spinbug);
        floor.addDancer(imitationbug);

        //party!
        floor.everyoneDance();
    }

    /** Creates a SquareBug of size 4. */
    public static void testSquareBug() {
        DanceFloor floor = new DanceFloor(7, 7);
        floor.setPauseTime(pause);

        SquareBug squarebug = new SquareBug(5,1, 4);

        squarebug.setSteps(); //the moves!!

        floor.addDancer(squarebug);
        floor.everyoneDance();
    }

    /** A bug that dances in a routine. */
    public static void testRoutineBug() {
        DanceFloor floor = new DanceFloor(7, 7);
        floor.setPauseTime(pause);

        RoutineBug routinebug = new RoutineBug(3,3);

        routinebug.setSteps(); //the moves!!

        floor.addDancer(routinebug);
        floor.everyoneDance();
    }

    /** A bug that turns continuously in one direction. */
    public static void testSpinBug() {
        DanceFloor floor = new DanceFloor(3, 5);
        floor.setPauseTime(pause);

        SpinBug spinbug = new SpinBug(1, 2);
        floor.addDancer(spinbug);
        floor.everyoneDance();
    }

    public static void testBoredBug() {
        DanceFloor floor = new DanceFloor(3, 5);
        floor.setPauseTime(pause);

        BoredBug boredbug = new BoredBug(1, 2);
        floor.addDancer(boredbug);
        floor.everyoneDance();
    }

}
