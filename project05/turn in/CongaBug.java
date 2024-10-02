package project5;
/* Ahyoung Hwang - I have neither given nor received unauthorized aid on this program. */

public class CongaBug extends DancingBug implements LeaderBug{
    /** The leader bug, who CongaBug will copy. */
    protected LeaderBug leader;

    /** Leaderbug's last step, which CongaBug copies.
     * Changed in step. */
    private DanceStep currentStep = DanceStep.FORWARD; //initalize to moving forward

    /** The step just executed by CongaBug. For returning in
     * getLastStep(). */
    private DanceStep savedStep = DanceStep.FORWARD;

    /** Constructor. */
    public CongaBug(int row, int col, LeaderBug leaderBug){
        super(row, col, "green");
        leader = leaderBug;
    }

    /** Follows the leader's last step, then modifies currentStep
     * and savedStep. */
    public void step(){
        doStep(currentStep); //copy the leader's step!

        savedStep = currentStep; //before changing the current step from leader's last step, save congabug's current step so that it can be returned for its own getLastStep

        currentStep = leader.getLastStep(); //get the current step
    }

    /** Returns CongaBug (this bug)'s last step. */
    @Override
    public DanceStep getLastStep() {
        return savedStep; //return saved step so that when imitating other CongaBugs, it does not end up following the leaderBug.
    }
}
