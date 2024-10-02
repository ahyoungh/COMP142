/* Ahyoung Hwang - I have neither given nor received unauthorized aid on this program. */
package project3;

import java.util.Arrays;

/**
 * An AnimationSequence is a collection of animations.  Each animation is either a translation or
 * rotation applied to a ComboPolygon.  Because each animation starts and ends at a specific moment
 * in time, we can use this class to begin and end animations whenever we want, running them simultaneously
 * or in a sequence.
 */
public class AnimationSequence {
    private TranslationAnimation[] translations;
    private RotationAnimation[] rotations;
    private int nextTransIndex, nextRotIndex;

    /** constructor for the AnimationSequence class. */
    public AnimationSequence(int numTranslations, int numRotations) {
        translations = new TranslationAnimation[numTranslations];
        rotations = new RotationAnimation[numRotations];
        nextTransIndex = 0;
        nextRotIndex = 0;
    }

    /** Add a new TranslationAnimation to this sequence. */
    public void addTranslation(TranslationAnimation t) {
        translations[nextTransIndex] = t;
        nextTransIndex++; //advance to next position in the array
    }

    /** Add a new RotationAnimation to this sequence. */
    public void addRotation(RotationAnimation t) {
        rotations[nextRotIndex] = t;
        nextRotIndex++; //advance
    }

    /** Retrieve the total time this AnimationSequence will last. */
    private int getMaxTime() {
        int max = 0;
        //check translations list
        for(int i = 0; i < translations.length; i++){
            int curr = translations[i].getEndTimeMillis();
            if(curr > max){
                max = curr;
            }
        }
        //check rotations list
        for(int i = 0; i < rotations.length; i++){
            int curr = rotations[i].getEndTimeMillis();
            if(curr > max){
                max = curr;
            }
        }
        return max;
    }

    /** Displays the animation sequence on the given canvas. */
    public void playOn(SimpleCanvas canvas) {
        // Display all the initial polygons, before they start moving.
        for (int i = 0; i < translations.length; i++) {
            translations[i].getPolygon().drawOn(canvas);
        }
        for (int i = 0; i < rotations.length; i++) {
            rotations[i].getPolygon().drawOn(canvas);
        }
        canvas.update();
        canvas.waitForClick();

        // Make a loop that advances through each frame of the animation draws it on the canvas.

        for(int time = 0; time < getMaxTime(); time += 20) { //Loop over a time variable that counts from time = 0 to time = total time of all the animations, counting by 20ms increments.
            canvas.clear(); //    Clear the canvas.
            for (int curr = 0; curr < translations.length; curr++) { //Loop over each translation animation:
                int start = translations[curr].getStartTimeMillis();
                int end = translations[curr].getEndTimeMillis();

                if (time >= start && time < end) { //if time is within the timeframe, animation should run.
                    translations[curr].advanceToNextFrame(); //advance the animation to the next frame
                }
                translations[curr].getPolygon().drawOn(canvas); //draw this frame on canvas
            }
            for (int curr = 0; curr < rotations.length; curr++) { //Loop over each rotation animation:
                int start = rotations[curr].getStartTimeMillis();
                int end = rotations[curr].getEndTimeMillis();

                if(time >= start && time < end){ //if the time is within the start/end timeframe, animation should run.
                    rotations[curr].advanceToNextFrame(); //advance the animation to the next frame.
                }
                rotations[curr].getPolygon().drawOn(canvas); //draw this frame on canvas

            }
            canvas.update(); //call canvas.update() (to actually show the polygons)
            canvas.pause(20); //pause for 20ms.
        }

    }

}

