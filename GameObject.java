package project6;
/* Ahyoung Hwang - I have neither given nor received unauthorized aid on this program. */

import java.awt.*;

/** A GameObject represents a generic object on the game canvas.
 * Each object has an (x, y) location, an image filename used to display
 * the object on the canvas, and a width and height.
 */
public class GameObject {
    /** The location of the CENTER of the object (image). */
    protected Location location;

    /** The image filename to use for this object. */
    protected String imageFilename;

    /** The border color to draw on the image. */
    protected Color borderColor;

    /** The width and height of the image. */
    protected int height, width;

    /** Should the player be frozen or not? If yes, then the keyboard won't work, and lives won't increase. . */
    protected boolean frozen;

    /** Duration of freeze when trapped. Increase = trapped longer. */
    protected final int  FROZENTIME = 70;

    /** If Remy is currently frozen, will increase with every time the canvas is drawn. */
    protected int currFrozen = 0;

    /** Create a "default" GameObject.  Feel free to change these defaults,
     * or add other constructors to let you set them as arguments.
     */
    public GameObject(){
        location = new Location(0, 0);
        imageFilename = "questionmark.png";
        height = 30;
        width = 30;
        borderColor = null;
        frozen = false;
    }

    /** Draws the GameObject on the canvas. getLeftX and getTopY(canvas)
     * ensure that it is within the canvas. */
    public void draw(SimpleCanvas canvas){
        canvas.drawImage(getLeftX(canvas), getTopY(canvas), imageFilename, width, height);
    }

    /** When the game ends, the object goes to its finishing position.
     * HAPPY! CONGRATS! YOU WON! (that's why you need the second parameter.
     * that's the chef who Remy will be celebrating with.) */
    public void ending(SimpleCanvas canvas, GameObject object){
        int middle = canvas.getWidth()/2;
        int topOfObject = (canvas.getHeight()/2) - (object.getHeight()/2);

        //perched on top of the argument's object
        setLocationX(middle);
        setLocationY(topOfObject);
    }

    /** When the game ends, the object goes to its finishing position.
     * aw... you lost :( */
    public void ending(SimpleCanvas canvas){
        setLocationX(canvas.getWidth()/2);
        setLocationY(canvas.getHeight()/2);
    }

    /** If the player is frozen, then the frozen status (how long / how much left)
     * shown as a "progress bar" on the lower right hand side. */
    public void drawFrozenStatus(SimpleCanvas canvas){
        int height = canvas.getHeight()/12;
        int width = canvas.getWidth()/3;
        //location of box: lower right
        int leftX = canvas.getWidth() - width;
        int topY = canvas.getHeight() - height;

        //background/bottom color
        canvas.setPenColor(Color.LIGHT_GRAY);
        canvas.drawFilledRectangle(leftX, topY, width, height);

        //time elapsed: top color changes as the time passes by
        canvas.setPenColor(Color.WHITE);
        int frozenWidth = (int) ( ((double)currFrozen/FROZENTIME) *width); //ratio of frozen to non-frozen time
        canvas.drawFilledRectangle(leftX, topY, frozenWidth, height);

        canvas.setPenColor(Color.BLACK);
        canvas.drawString(canvas.getWidth()-60, canvas.getHeight(), "FREEZE!");
    }

    /** Return if the object is frozen or not.
     * true = frozen, false = not frozen. */
    public boolean getFrozen(){
        return frozen;
    }

    /** Sets frozen to argument passed through. */
    public void setFrozen(boolean frozenOrNot){
        setImageFilename("player_remyfrozen.png");
        frozen = frozenOrNot;
    }

    /** When Remy is frozen, increments his frozen count
     * so that he will eventually unfreeze. */
    public void unfreezing(){
        if(currFrozen <= FROZENTIME){ //still frozen
            currFrozen++;
        } else { //should not be frozen anymore
            setImageFilename("player_remy.png"); //normal pic
            currFrozen = 0; //double check - not frozen anymore
            setFrozen(false);
        }
    }

    /** Returns only the name of the file, without the "good/bad"
     * before it nor the ".png" at the end. */
    public String onlyName(){
        String filename = getImageFilename();
        char firstCh = filename.charAt(0);
        int underscoreAt = filename.indexOf('_'); //every scrolling object name has an underscore in it
        int periodAt = filename.indexOf('.');

        return filename.substring(underscoreAt + 1, periodAt); //object name after underscore (aka specific name)
    }

    /** Returns true if this GameObject overlaps the other GameObject. */
    public boolean overlaps(GameObject other){
        boolean leftOverRight = (getLeftX() <= other.getRightX());
        boolean otherLeftOverRight = (other.getLeftX() <= getRightX());

        boolean topOverBottom = (getTopY() <= other.getBottomY());
        boolean otherTopOverBottom = (other.getTopY() <= getBottomY());

        if(leftOverRight && otherTopOverBottom) {
            return otherLeftOverRight && topOverBottom; //will stop if this is true. in that case, all four cases are true.
        }
        return false;
    }

    /** Get the x-coordinate of the left edge of this object.
     * Parameter ensures it is not being drawn outside canvas bounds,
     * which is important because this is the function called when
     * drawing objects. */
    public int getLeftX(SimpleCanvas canvas) {
        if(getLeftX() < 0){ //crossing left border
            setLocationX(width/2); //set center so that edge does not cross over bound
            return 0; //leftX is 0
        } else if(getRightX() > canvas.getWidth()){
            setLocationX(canvas.getWidth() - (width/2));
            return canvas.getWidth() - width; //leftX cannot be greater than canvas width minus image width
        } else //no problemo
            return getLeftX();
    }

    /** Get the x-coordinate of the left edge of this object. */
    public int getLeftX() {
        return location.getX() - width/2;
    }

    /** Get the x-coordinate of the right edge of this object. */
    public int getRightX() {
        return location.getX() + width/2;
    }

    /** Get the y-coordinate of the top edge of this object.
     * Parameter ensures it is not being drawn outside canvas bounds,
     * which is important because this is the function called when
     * drawing objects.  */
    public int getTopY(SimpleCanvas canvas) {
        if(getTopY() < 0){ //too far up
            setLocationY(height/2); //sets center so that edge does not cross over bound
            return 0;
        } else if(getBottomY() > canvas.getHeight()){
            setLocationY(canvas.getHeight() - (height/2));
            return canvas.getHeight() - height;
        } else //no problemo
            return getTopY();
    }

    /** Get the y-coordinate of the top edge of this object. */
    public int getTopY() {
        return location.getY() - height/2;
    }

    /** Get the y-coordinate of the bottom edge of this object. */
    public int getBottomY() {
        return location.getY() + height/2;
    }

    /** Get the x-coordinate of the right edge of this object. */
    public int getCenterX() {
        return location.getX();
    }

    /** Get the y-coordinate of the top edge of this object. */
    public int getCenterY() {
        return location.getY();
    }

    /** Get this GameObject's location. */
    public Location getLocation() {
        return location;
    }

    /** Set this GameObject's location. */
    public void setLocation(Location location) {
        this.location = location;
    }

    /** Set this GameObject's location's x coordinate. */
    public void setLocationX(int x) {
        this.location.setX(x);
    }

    /** Set this GameObject's location's y coordinate. */
    public void setLocationY(int y) {
        this.location.setY(y);
    }

    /** Get this GameObject's image filename. */
    public String getImageFilename() {
        return imageFilename;
    }

    /** Set this GameObject's filename. */
    public void setImageFilename(String imageFilename) {
        this.imageFilename = imageFilename;
    }

    /** Set this GameObject's border color.  null indicates no border. */
    public void setBorderColor(Color borderColor) {
        this.borderColor = borderColor;
    }

    /** Get this GameObject's height. */
    public int getHeight() {
        return height;
    }

    /** Set this GameObject's height. */
    public void setHeight(int height) {
        this.height = height;
    }

    /** Get this GameObject's width. */
    public int getWidth() {
        return width;
    }

    /** Set this GameObject's width. */
    public void setWidth(int width) {
        this.width = width;
    }
}
