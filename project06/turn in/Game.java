package project6;
/* Ahyoung Hwang - I have neither given nor received unauthorized aid on this program. */

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

/** This class represents a generic game on a canvas. */
public class Game{
    /** The canvas that the game is drawn on. The canvas to my palette, I should say. */
    protected SimpleCanvas canvas;

    /** The rat himself. Controlled by the player, of course. */
    protected GameObject remy;

    /** Please hold all the objects that will scroll scross sche screen.*/
    protected ArrayList<GameObject> items;

    /** The number of items scrolling across the screen at a given time.
     * Declared FINAL (that's why it's in all caps!) for easy tinkering.
     * 10 is too little, 30 is too much (can't move around without getting
     * stabbed ten thousand times)——20 is perfect. */
    protected final int NUMITEMS = 20;

    /** Hold the recipe! */
    protected Recipe recipe;

    /** Hold my life represented in 5 little hearts! (actually it's the player's life)*/
    protected Hearts hearts;

    /** Oui this is a private chef. Or at least holds the private chef. */
    protected Chef chef;

    /** Should the chef be on screen? Oui or Noui? */
    private boolean yesChef = false;

    /** Is Remy on the chef? */
    private boolean remyOnChef = false; //not at the start, because that is how you win the game!

    /** Construct a new instance of the game with a given width and height. */
    public Game(int width, int height){
        canvas = new SimpleCanvas(width, height, "My Game"); //create a new canvas

        //objects that should appear at the start of the game:
        //icons in the corners
        hearts = new Hearts();
        recipe = new Recipe();

        //remy <3
        remy = new GameObject();
        remy.setLocation(new Location(0, height/2)); //Remy
        remy.setImageFilename("player_remy.png");

        //chef
        chef = new Chef(canvas, remy); //moves in relation to remy

        //all the scrolling objects!
        items = new ArrayList<>();
        for(int i = 0; i < NUMITEMS; i++){
            createRandomScrollingObject();
        }
    }

    /** Draw the state of the game on the canvas. Canvas is shown in instructions(). */
    public void draw(){
        canvas.clear(); // always clear first.

        if(remy.getFrozen()){ remy.drawFrozenStatus(canvas); } //frozen status

        //draw scrolling objects and hearts and the ingredients.
        if(!remyOnChef){
            drawScrollingObjects();
            hearts.draw(canvas); //the hearts left
            recipe.draw(canvas);
        }

        //draw the chef, if he should be drawn
        if(yesChef){
            chef.draw(canvas);
            chef.move(canvas, remy);
        }

        //draw the player
        remy.draw(canvas);

        canvas.update();
    }

    /** Draws the items that need to scroll over the canvas.
     * Called in draw(). */
    private void drawScrollingObjects(){
        for(int i = 0; i < items.size(); i++){
            int newX = items.get(i).getLocation().getX() - 4; //new x value

            //check if on or off screen
            if(newX <= -(items.get(i).getWidth())){  //if off screen,
                items.remove(i); //delete
                createRandomScrollingObject(); //replace with new random object
            } else { //still on the canvas
                items.get(i).setLocationX(newX); //set newX as its x-value to "move" it
            }

            //draw the item
            items.get(i).draw(canvas);
        }
    }

    /** Create a random scrolling object, adds it to the rightmost end of the canvas.*/
    private void createRandomScrollingObject(){
        GameObject ob = new ScrollingObject();

        int size = 40; //random size 20-40
        //square
        ob.setHeight(size);
        ob.setWidth(size);
        setRandomLocation(ob); //gives random location
        ob.setImageFilename("random"); //sets random filename

        items.add(ob);

        if(ob.getImageFilename().equals("bad_mousetrap.png")){
            ob.setImageFilename("good_cheese.png");
            int cheeseX = ob.getLeftX();
            int cheeseY = ob.getTopY();
            Location followingLocation = new Location(cheeseX + size, cheeseY);

            ScrollingObject trap = new ScrollingObject();
            trap.setImageFilename("bad_mousetrap.png");
            trap.setLocation(followingLocation);
            items.add(trap);
        }
    }

    /** Gives the given object a new location that does not overlap with existing
     * locations in the item list.
     * Called in createRandomScrollingObject(). */
    private void setRandomLocation(GameObject ob){
        int x = canvas.getWidth() + ((int) (Math.random() * canvas.getWidth()));
        int y = (int) (Math.random() * canvas.getHeight());

        ob.setLocation(new Location(x,y)); //set new location

        //check for overlaps between objects
        for(GameObject item : items){
            if(ob.overlaps(item)){
                setRandomLocation(ob); //do it again! to make sure none of them overlap.
            }
        }
    }

    /** Start the game running. */
    public void runGame(){
        //prompt user with instructions
        instructions();
        canvas.waitForClick();
        canvas.clear();

        //Game starts now
        draw();
        canvas.setPenColor(Color.BLACK);
        canvas.drawStringCentered(canvas.getWidth()/2 , canvas.getHeight()/2, "Click to start!", 30);
        canvas.update();
        canvas.waitForClick();

        //while the game is not over:
        while (!isGameOver()) {
            //update the state of the game
            updateScrollingObjects();
            //draw the state of the game.
            draw();

            //handle keyboard, but only if not frozen
            if(!remy.getFrozen()){
                remy.setImageFilename("player_remy.png"); //remy should not be stuck in a cage
                handleKeyboard();
            } else { //frozen!
                remy.unfreezing(); //work towards unfreezing
            }

            //check recipe status. if complete, show the chef.
            if(recipe.ingredientsCollected()){
                yesChef = true; //show the chef!
                if(remy.overlaps(chef) && !remy.getFrozen()){ //touching chef while frozen doesn't count
                    handleObjectTouch(chef);
                }
            }

            canvas.pause(50); // can adjust this higher or lower as needed
        } //game is now over.

        draw(); //any last minute updates?


        if(isGameOver() && remyOnChef){ //celebrate! you won!
            celebration();
            draw();
            canvas.setPenColor(Color.BLACK);
            canvas.drawStringCentered(canvas.getWidth()/2, (canvas.getHeight()/2)+(chef.getHeight()*3/4), "YOU WIN!", 30);
            canvas.update();
        } else { //oops sorry you lost
            System.out.println("loser");
            canvas.clear();
            remy.ending(canvas);
            remy.draw(canvas);
            canvas.drawStringCentered(canvas.getWidth()/2, (canvas.getHeight()/2)+(remy.getHeight()*3/4), "YOU LOST :(", 30);
            canvas.update();
        }
    }

    /** Is the game over? Are there hearts left or not? Very important because
     * it keeps the while loop running.
     * Called in runGame(). */
    private boolean isGameOver(){
        return remyOnChef || (hearts.getHearts() ==0);
    }

    /** Move the player based on which arrow key is pressed.
     * Called in runGame(). */
    private void handleKeyboard() {
        Location myLocation = remy.getLocation();
        if (canvas.isKeyPressed(KeyEvent.VK_UP)) {
            myLocation.setY(myLocation.getY() - 10);
        }
        if (canvas.isKeyPressed(KeyEvent.VK_DOWN)) {
            myLocation.setY(myLocation.getY() + 10);
        }
        if (canvas.isKeyPressed(KeyEvent.VK_LEFT)) {
            myLocation.setX(myLocation.getX() - 10);
        }
        if (canvas.isKeyPressed(KeyEvent.VK_RIGHT)) {
            myLocation.setX(myLocation.getX() + 10);
        }
    }

    /** Give the user instructions before they begin the game. First time showing
     * the canvas. canvas.show() is SO important. without it, you can't see anything
     * "play"ing out.
     * Called in runGame(). */
    private void instructions(){
        canvas.drawImage(0,0,"instructions.png", canvas.getWidth(), canvas.getHeight());
        canvas.update();
        canvas.show(); //VERY IMPORTANT!!
    }

    /** Once an object is touched in runGame, come here to see which one it
     * was, and perform appropriate functions based on the object.
     * Called in runGame(). */
    private void handleObjectTouch(GameObject object){
        //GOOD OR BAD?
        String filename = object.getImageFilename();
        String name = object.onlyName();

        //check first letter
        if(filename.charAt(0) == 'g'){ //GOOD
            if(name.equals("cheese")){ //if name is cheese
                if(!remy.getFrozen()) //if not frozen
                    hearts.increaseHearts();
            } else
                recipe.handleCollected(name);
        } else if (filename.charAt(0) == 'b'){ //BAD, knife or mousetrap
            hearts.decreaseHearts(); //life decreases
            if (name.equals("mousetrap"))
                remy.setFrozen(true); //FREEZE!!
        }

        if(name.equals("chef")) //chef, he is now on the chef!
            remyOnChef = true;
    }

    /** Moves the player and the chef to the ending position.
     * Called in runGame() after the game concludes. */
    private void celebration(){
        chef.ending(canvas);
        remy.ending(canvas, chef);
    }

    /** Updates the items that need to scroll over the canvas. Phew, scrolling is a lot of work.
     * Called in runGame(). */
    private void updateScrollingObjects(){
        for(int i = 0; i < items.size(); i++){
            GameObject object = items.get(i);
            if(remy.overlaps(object) && !remy.getFrozen()){ //if overlapping, remove object and implement any changes
                handleObjectTouch(object); //run appropriate operation
                items.remove(i); //remove
                createRandomScrollingObject(); //add new random object
            }
        }
    }

}
