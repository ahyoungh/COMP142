/* Ahyoung Hwang - I have neither given nor received unauthorized aid on this program. */
package project3;
/**
 * A RotationAnimation is a rotation applied to a ComboPolygon that takes rotates the ComboPolygon around
 * an (a, b) point in the plane, rotates a certain number of degrees, and starts and ends at specific moments in time.
 *  Note that this code assumes the animation runs at 50 frames/second, which means each frame
 *  is displayed for 20 milliseconds.
 */
public class RotationAnimation {
    private int rotateAroundA; //x coordinate of point being rotated around
    private int rotateAroundB; //y coordinate of point being rotated around
    private int degrees; // total change in degrees
    private int startMs; //when the animation begins (ms)
    private int endMs; //when the animation ends (ms)
    private ComboPolygon polygon; //the polygon we are animating

    public RotationAnimation(int rotateAroundA, int rotateAroundB, int degrees, int startMs, int endMs, ComboPolygon polygon) {
        this.rotateAroundA = rotateAroundB;
        this.rotateAroundB = rotateAroundA;
        this.degrees = degrees;
        this.startMs = startMs;
        this.endMs = endMs;
        this.polygon = polygon;
    }

    /** Returns the current polygon. */
    public ComboPolygon getPolygon() {
        return polygon;
    }

    /** Returns the end time for the current polygon. */
    public int getEndTimeMillis() {
        return endMs;
    }

    /** Returns the start time for the current polygon. */
    public int getStartTimeMillis() {
        return startMs;
    }

    /** Update this polygon to what it should look like
     * after 20 milliseconds have elapsed since the previous update. */
    public void advanceToNextFrame() {
        int diffMs = Math.abs(startMs-endMs); //diff bt start and finish times (total duration of animation)
        int frames = diffMs / 20; //20 is the length of each frame

        double diffDeg = (double) degrees / frames; //degree difference in each frame

        polygon.rotateAround(rotateAroundA, rotateAroundB, diffDeg); //rotate based on calculated numbers
    }

    /** Display the animation on a canvas. */
    public void playOn(SimpleCanvas canvas) {
        polygon.drawOn(canvas);
        canvas.update();

        for(int time = 0; time < endMs; time += 20){
            canvas.clear();
            if(time >= startMs) {
                advanceToNextFrame();
            }
            polygon.drawOn(canvas);
            canvas.update();
            canvas.pause(20);
        }
    }
}

