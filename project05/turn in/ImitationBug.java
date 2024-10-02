package project5;
/* Ahyoung Hwang - I have neither given nor received unauthorized aid on this program. */

public class ImitationBug extends DancingBug{
    /** The leader bug, of whom imitation bug will copy. */
    protected LeaderBug leader;

    /** Constructor. */
    public ImitationBug(int row, int col, LeaderBug leaderBug){
        super(row, col, "pink");
        leader = leaderBug;
    }

    /** Follows the leader's step in-sync. */
    public void step(){
        DanceStep leaderStep = leader.getLastStep();
        doStep(leaderStep); //copy the leader's step!
    }

}
