package project6;
/* Ahyoung Hwang - I have neither given nor received unauthorized aid on this program. */

public class Hearts {
    /** The current number of hearts. */
    private int hearts;

    /** Maximum hearts is 5. */
    private final int MAX = 5;

    /** Constructor. The initial number of hearts is the max. */
    public Hearts(){
        hearts = MAX;
    }

    /** Returns the current number of hearts. */
    public int getHearts(){
        return hearts;
    }

    /** Increases number of hearts by one, but only to the maximum number. */
    public void increaseHearts(){
        if(hearts < MAX){
            hearts++;
        }
    }

    /** Decreases number of hearts by one. */
    public void decreaseHearts(){
        hearts--;
    }

    /** Changes number of hearts to a String. */
    public String toString(){
        return String.valueOf(hearts);
    }

    /** Draws the current number of hearts in the lower left corner of the screen. */
    public void draw(SimpleCanvas canvas){
        int size = 30;

        //location: bottom left corner
        int leftX = 0;
        int topY = canvas.getHeight() - size;

        //draw each one
        for(int i = 0; i < hearts; i++){
            canvas.drawImage(leftX, topY, "icon_heart.png", size, size);
            leftX += size;
        }
    }
}
