package project6;
/* Ahyoung Hwang - I have neither given nor received unauthorized aid on this program. */

/** A Chef represents an object on the game canvas representing the chef, which
 * the player interacts with.
 *
 * It inherits from GameObject, meaning that it has an (x, y) location,
 * an image filename, and a width and height.
 */
public class Chef extends GameObject {
    /** The size of the chef (both width and height). */
    protected final int SIZE = 40;

    /** "Speed" of chef when he moves (how many pixels it changes in x- and y-directions).
     * Set randomly as needed. */
    protected int speedx, speedy;

    /** Whether the chef is shown on screen or not. Start as false.*/
    protected boolean show = false;

    /** Stores Remy for the final celebratory interaction. */
    protected GameObject remy;

    /** Constructor. Height, width, location, filename are overridden from super.
     * remy, speedx, and speedy are unique to Chef. */
    public Chef(SimpleCanvas canvas, GameObject remy) {
        super();
        //override
        height = SIZE;
        width = SIZE;
        this.setLocation(new Location (canvas.getWidth()/2, canvas.getHeight()/2));
        imageFilename = "good_chef.png";

        //unique instance variables
        this.remy = remy;
        //random speed
        speedx = (int) (Math.random() * 5 + 1);
        speedy = (int) (Math.random() * 5 + 1);
    }

    /** Make the chef change directions randomly using arbitrary values. */
    private void pickNewDirection() {
        this.speedx = (int) (Math.random() * 30 - 15); //range of nums is -15 to 30
        this.speedy = (int) (Math.random() * 30 - 15);
    }

    /** Move the chef on the canvas according to the speedx/y variables.
     * Changes direction sometimes (based on random) .*/
    public void move(SimpleCanvas canvas, GameObject remy) {
        //set new speed
        int newX = getCenterX() + speedx;
        int newY = getCenterY() + speedy;

        // check for hitting walls
        if (getRightX() >= canvas.getWidth()) //hitting right wall, move left
            speedx = -(int) (Math.random() * 15) - 1;
        if (getLeftX() <= 0) //hitting left wall, move right
            speedx = (int) (Math.random() * 15) + 1;
        if (getBottomY() >= canvas.getHeight()) //hitting down wall, move up
            speedy = -(int) (Math.random() * 15) - 1;
        if (getTopY() <= 0) //hitting up wall, move down
            speedy = (int) (Math.random() * 15) + 1;

        //set the new location
        setLocationX(newX);
        setLocationY(newY);

        //call pickNewDirection() randomly (2 in 10 times)
        int random = (int) (Math.random() * 10 + 1); //create random (1-10)
        if (random <= 2) {
            pickNewDirection();
        }
    }

    /** Returns the size of the chef as drawn on the canvas. note: SIZE also equals width and height. */
    public int getSize(){ return SIZE; }

    /** When reached the ending, move to the center of the canvas. */
    public void ending(SimpleCanvas canvas){
        setLocationX(canvas.getWidth()/2);
        setLocationY(canvas.getHeight()/2);
    }

}

