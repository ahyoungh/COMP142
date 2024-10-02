package project5;
/* Ahyoung Hwang - I have neither given nor received unauthorized aid on this program. */

public class SquareBug extends RoutineBug{
    /** The size of the square the bug will trace. */
    private int sqsize;

    /** Constructor. Adds squaresize on top of super's
     * constructor. */
    public SquareBug(int row, int col, int size){
        super(row, col); //no need for color, because this is inherited from RoutineBug, not DancingBug
        sqsize = size; //set square size
    }

    /** Sets square routine. */
    protected void setSteps(){
        for(int sides = 0; sides < 4; sides++){
            for(int i = 0; i < sqsize; i++){
                steps.add(DanceStep.FORWARD);
            }
            steps.add(DanceStep.TURN_RIGHT);
        }
    }

}
