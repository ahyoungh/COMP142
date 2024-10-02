package project5;
/* Ahyoung Hwang - I have neither given nor received unauthorized aid on this program. */

/** A BoredBug stands in one place and doesn't move. */
public class BoredBug extends DancingBug {

    /** Construct a BoredBug at a given row & column. */
    public BoredBug(int row, int col) {
        super(row, col, "gray");
    }

    /** Implements the (abstract) step() method in DancingBug.
     * Note that you do not have to print the bug, but it's nice
     * for debugging.
     */
    public void step() {
        System.out.println("Here is the bored bug: " + this);
        doStep(DanceStep.PAUSE);
    }
}
