package project5;
/* Ahyoung Hwang - I have neither given nor received unauthorized aid on this program. */

import java.util.ArrayList;

public class RoutineBug extends DancingBug implements LeaderBug{
    /** The current step the bug is on. Increments after each step in
     * step() method. */
    protected int currentStep = 0;

    /** Array that holds the dance routine. */
    protected ArrayList<DanceStep> steps = new ArrayList<DanceStep>();

    /** CONSTRUCTOR: for RoutineBug. Sets bug color red. */
    public RoutineBug(int row, int col){
        super(row, col, "red");
    }

    /** Implements LeaderBug. Returns the dance step that is at the current
     * step count in the steps array. */
    public DanceStep getLastStep(){
        if(currentStep == 0){ //meaning leaderbug has restarted
            return steps.get(steps.size()-1); //return the last in the array
        } else {
            return steps.get(currentStep-1); //need to subtract one so that the imitation bug follows the current step, not the one routineBug is on right now.
        }
    }

    /** Executes current step, increments count. If at the end of the array,
     * starts the dance all over again! */
    public void step(){
        doStep(steps.get(currentStep));
        if(currentStep == steps.size()-1){ //reached the end!
            currentStep = 0; //start over
        } else { //next step!
            currentStep++;
        }
    }

    /** Sets routine. */
    protected void setSteps(){
        steps.add(DanceStep.STEP_RIGHT);
        steps.add(DanceStep.STEP_RIGHT);
        steps.add(DanceStep.STEP_RIGHT);
        steps.add(DanceStep.PAUSE);
        steps.add(DanceStep.STEP_LEFT);
        steps.add(DanceStep.STEP_LEFT);
        steps.add(DanceStep.STEP_LEFT);
        steps.add(DanceStep.PAUSE);
        steps.add(DanceStep.BACKWARD);
        steps.add(DanceStep.BACKWARD);
        steps.add(DanceStep.BACKWARD);
        steps.add(DanceStep.PAUSE);
        steps.add(DanceStep.FORWARD);
        steps.add(DanceStep.FORWARD);
        steps.add(DanceStep.FORWARD);
        steps.add(DanceStep.TURN_LEFT);
    }
}
