package project6;
/* Ahyoung Hwang - I have neither given nor received unauthorized aid on this program. */

/** A Location represents an (x, y) location. */
public class Location{
    private int x, y;

    public Location(int x, int y){
        this.x = x;
        this.y = y;
    }

    public int getX(){
        return x;
    }

    public void setX(int x){
        this.x = x;
    }

    public int getY(){
        return y;
    }

    public void setY(int y){
        this.y = y;
    }
}