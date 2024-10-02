/* Ahyoung Hwang - I have neither given nor received unauthorized aid on this program. */
package project3;
/**
 * A TranslationAnimation is a translation applied to a ComboPolygon that takes moves the ComboPolygon
 * a certain distance in the x and y directions, and starts and ends at specific moments in time.
 * Note: this code assumes the animation runs at 50 frames/second, which means each frame
 * is displayed for 20 milliseconds.
 */
public class TranslationAnimation {
    private int changeX;  // total change in x direction
    private int changeY;  // total change in y direction
    private int startMs;  // when the animation begins (ms)
    private int endMs;    // when the animation ends (ms)
    private ComboPolygon polygon;  // the polygon we are animating

    /** Constructor for the translation animation. */
    public TranslationAnimation(int changeX, int changeY, int startMs, int endMs, ComboPolygon polygon) {
        this.changeX = changeX;
        this.changeY = changeY;
        this.startMs = startMs;
        this.endMs = endMs;
        this.polygon = polygon;
    }

    /** returns the current polygon. **/
    public ComboPolygon getPolygon() { return polygon; }

    /** returns the current polygon's start time in milliseconds. **/
    public int getStartTimeMillis() { return startMs; }

    /** returns the current polygon's end time in milliseconds. **/
    public int getEndTimeMillis() { return endMs; }

    /** Updates the current polygon to what it should look like
     * after 20 milliseconds have elapsed since the previous update.*/
    public void advanceToNextFrame() {
        int diffMs = Math.abs(startMs - endMs); //difference bt start and finish
        int frames = diffMs / 20; //20 is the length of each frame

        double frameX = (double) changeX / frames; //x difference in each frame
        double frameY = (double) changeY / frames; //y difference in each frame

        polygon.translate(frameX, frameY);
    }

    /** Display the animation on the given canvas. */
    public void playOn(SimpleCanvas canvas) {
        polygon.drawOn(canvas); // draw the polygon initially
        canvas.update();        // show it on the canvas

        for(int time = 0; time < endMs; time += 20) { //from time = 0 to the end of the animation, counting by 20ms increments
            canvas.clear();
            if (time >= startMs) { //if it is within the start-end time frame
                advanceToNextFrame();
            }
            polygon.drawOn(canvas);
            canvas.update(); //to actually show the polygon
            canvas.pause(20); //pause for 20ms
        }

    }
}
