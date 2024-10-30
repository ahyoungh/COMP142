package project6;
/* Ahyoung Hwang - I have neither given nor received unauthorized aid on this program. */

import java.util.ArrayList;

public class ScrollingObject extends GameObject{
    /** The ArrayList of strings that contains the file names of all objects.
     * Initialized and items added in initializeObjects(). */
    protected ArrayList<String> allObjects;

    /** Stores the index of the object currently being used or drawn. */
    protected int currRandomNum;

    /** Constructor. Uses Gameobject's constructor, then initializes the array of scrolling objects. */
    public ScrollingObject(){
        super();
        initializeObjects();
    }

    /** Get the x-coordinate of the left edge of this object.
     * Unlike normal GameObjects, can be drawn outside canvas bounds, because they need to
     * appear/disappear off canvas. */
    public int getLeftX(SimpleCanvas canvas) {
        return location.getX() - width/2;
    }

    /** Set this ScrollingObject's filename. */
    public void setImageFilename(String imageFilename) {
        if(imageFilename.equals("random")) {
            currRandomNum = (int) (Math.random() * allObjects.size()); //generate a random number
            this.imageFilename = allObjects.get(currRandomNum); //get the object at the random
        } else {
            this.imageFilename = imageFilename;
        }
    }

    /** Add the list of good items into the good array list. */
    public void initializeObjects(){
        allObjects = new ArrayList<>();

        //good objects
        allObjects.add("good_cheese.png");
        allObjects.add("good_onion.png");
        allObjects.add("good_tomato.png");

        //bad objects
        allObjects.add("bad_knife.png");
        allObjects.add("bad_mousetrap.png");
    }

    /** Returns the file name of the current object. */
    public String toString(){
        return allObjects.get(currRandomNum);
    }


}
