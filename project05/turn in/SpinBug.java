package project5;
/* Ahyoung Hwang - I have neither given nor received unauthorized aid on this program. */

public class SpinBug extends DancingBug implements LeaderBug{
    /** Constructor. Sets color yellow. */
    public SpinBug(int row, int col){
        super(row, col, "yellow");
    }

    /** Executes step for this bug. In this case, TURN_RIGHT. */
    public void step(){
        doStep(DanceStep.TURN_RIGHT);
    }

    /** Returns the last step this bug took. In this case, it
     * is just TURN_RIGHT. */
    public DanceStep getLastStep(){
        return DanceStep.TURN_RIGHT;
    }

}
