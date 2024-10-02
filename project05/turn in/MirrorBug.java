package project5;
/* Ahyoung Hwang - I have neither given nor received unauthorized aid on this program. */

public class MirrorBug extends DancingBug{
    /** The leader bug, who MirrorBug will copy. */
    protected LeaderBug leader;

    /** Constructor. */
    public MirrorBug(int row, int col, LeaderBug leaderBug){
        super(row, col, "blue");
        leader = leaderBug;
    }

    /** Follows the leader's step in-sync, but turns right for
     * left turns and vice versa. */
    public void step(){
        DanceStep leaderStep = leader.getLastStep();

        //if turning right or left, turn the opposite way.
        if(leaderStep == DanceStep.TURN_RIGHT){
            doStep(DanceStep.TURN_LEFT);
        } else if(leaderStep == DanceStep.TURN_LEFT){
            doStep(DanceStep.TURN_RIGHT);
        } else { //otherwise,
            doStep(leaderStep); //copy the leader's step!
        }
    }
}
