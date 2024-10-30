package project6;
/* Ahyoung Hwang - I have neither given nor received unauthorized aid on this program. */

import java.util.ArrayList;

public class Recipe {
    /** The list of ingredients that must be collected before the chef appears.
     * Initialized in constructor. */
    protected ArrayList<String> ingredients;

    /** The number of ingredients needed. Its index corresponds to the index of ingredients.
     * Does not change after initialization in constructor. */
    protected ArrayList<Integer> needed;

    /** The number of ingredients collected. Index corresponds to index of ingredients.
     * Incremented (in handleCollected()) as Remy collects ingredients during the game.
     * Initialized as a blank arraylist, containing only 0s, in initialize(). */
    protected ArrayList<Integer> collected;

    /** Constructor. creates a new recipe, initializes it with pre-existing list of ingredients. */
    public Recipe(){
        ingredients = new ArrayList<>();
        needed = new ArrayList<>();

        initialize();
    }

    /** Draws the state of how many ingredients are collected / needed. */
    public void draw(SimpleCanvas canvas){
        int size = 30;
        //drawn on top right corner
        int leftX = canvas.getWidth() - size*ingredients.size();
        int topY = 0;

        //for each ingredient, display the status
        for(int i = 0; i < ingredients.size(); i++){
            String filename = "good_" + ingredients.get(i) + ".png";
            String numCollected = String.valueOf(collected.get(i));
            String numNeeded = String.valueOf(needed.get(i));

            //draw image
            canvas.drawImage(leftX, topY, filename, size, size);
            leftX += size;

            //draw text (shows how many are collected / needed)
            canvas.drawStringCentered(leftX-(size/2), topY+(size/5*3), numCollected + "/" + numNeeded, 10);
            canvas.update();
        }
    }

    /** When an ingredient is touched, increments number collected of that specific ingredient . */
    public void handleCollected(String touched){
        for(int i = 0; i < ingredients.size(); i++){ //check each ingredient
            if(touched.equals(ingredients.get(i))){ //if they match,
                int curr = collected.get(i);
                collected.remove(i);
                collected.add(i, curr+1);
            }
        }
    }

    /** Checks to see if all the ingredients have been collected (where num collected = num needed). */
    public boolean ingredientsCollected(){
        for(int i = 0; i < needed.size(); i++){
            if(collected.get(i) < needed.get(i)){ //have not reached the goals yet
                return false;
            }
        }
        return true; //will break if false
    }

    /** Adds ingredients to the list of ingredients. */
    public void initialize(){
        addIngredient("tomato", 20);
        addIngredient("onion", 20);

        //initialize collected (blank for now)
        collected = new ArrayList<>(ingredients.size());
        for(String i : ingredients){
            collected.add(0);
        }
    }

    /** Shorthand to add ingredients to the list. */
    public void addIngredient(String name, int numNeeded){
        ingredients.add(name);
        needed.add(numNeeded);
    }
}
