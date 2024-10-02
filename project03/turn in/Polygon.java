/* Ahyoung Hwang - I have neither given nor received unauthorized aid on this program. */
package project3;
import java.awt.*;
import java.util.Arrays;

/**
 * A Polygon represents a shape drawn with a sequence of connected line segments.
 */
public class Polygon {
    /** The array of x-coordinates for this Polygon. */
    private double[] xpoints;

    /** The array of y-coordinates for this Polygon. */
    private double[] ypoints;

    /** The color of this Polygon. */
    private Color color;

    /** The next index in the arrays of x/y coordinates that will be filled by addPoint().
     *  Increments every time a new point is added. */
    private int nextIndex;

    /** Create a new polygon, given how many points it has and its color. */
    public Polygon(int numPoints, Color c) {
        xpoints = new double[numPoints];
        ypoints = new double[numPoints];
        color = c;
        nextIndex = 0;
    }

    /** Add a point to this polygon. */
    public void addPoint(double x, double y) {
        xpoints[nextIndex] = x;
        ypoints[nextIndex] = y;

        nextIndex++; //increase so that the next point will be added to the next location in the array.
    }

    /** Translate this polygon in the direction given by distx and disty. */
    public void translate(double distx, double disty) {
        //go through both arrays (x and y points) and add the distances.
        for(int i = 0; i < xpoints.length; i++){
            xpoints[i] += distx;
            ypoints[i] += disty;
        }

    }

    /** Rotate this polygon around the point (a, b) by the degrees given by angle.
     * Note: +degree = clockwise, -degree = counter-clockwise (automatically with the math)
     *
     * @a = centerx
     * @b = centery
     * @angle = angle to rotate by (given in degrees)
     * */
    public void rotateAround(double a, double b, double angle) {
        double rad = angle * (Math.PI/180); //the angle in radians
        for(int i = 0; i < xpoints.length; i++){ //for each point of the polygon
            //translate (x,y) by (-a, -b)
            xpoints[i] -= a;
            ypoints[i] -= b;

            //calculate rotated points
            double x2 = (xpoints[i] * Math.cos(rad)) - (ypoints[i] * Math.sin(rad));
            double y2 = (xpoints[i] * Math.sin(rad)) + (ypoints[i] * Math.cos(rad));

            //translate (x2, y2) by (a, b)
            x2 += a;
            y2 += b;

            //set new rotated points in arrays
            xpoints[i] = x2;
            ypoints[i] = y2;

        }


    }

    /** Draw this polygon on a canvas.  The canvas should already be constructed and shown. */
    public void drawOn(SimpleCanvas canvas) {
        if (xpoints.length != ypoints.length) {  // sanity check: these should be the same.
            System.err.println("Warning: Length of x and y arrays don't match.");
        }
        if (nextIndex != xpoints.length) { // sanity check: can't draw until these are full.
            System.err.println("Warning: x & y coordinate arrays are not full.");
        }

        // Convert xpoints and ypoints to integers because that's what SimpleCanvas needs.
        int[] newX = new int[xpoints.length];
        int[] newY = new int[ypoints.length];
        for (int i = 0; i < xpoints.length; i++) {
            newX[i] = (int)xpoints[i];
            newY[i] = (int)ypoints[i] ;
        }
        canvas.setPenColor(color);
        canvas.drawFilledPolygon(newX, newY);
    }

    /** Return a String representation of this Polygon.
     * ex. "Polygon: [0, 0, 0][0, 0, 0]"
     * Note: Use Arrays.toString(array) method to turn array into displayable string */
    public String toString() {

        String str = "";
        String xString = Arrays.toString(xpoints);
        String yString = Arrays.toString(ypoints);

        //concatenate all the components
        str = str.concat("Polygon: ").concat(xString).concat(" ").concat(yString);

        return str;
    }
}
