1. What bugs and conceptual difficulties did you encounter? How did you overcome them? What did you learn?
- It was challenging to understand how the number of rows/cols translates into the width/height of the board. I was able to udnerstand by drawing out a diagram with specific numbers and calculating the real numbers before making it into an equation. It helped to visualize what I was working with, and working from concrete numbers to abstract equations.
- At first, I had trouble with drawing the board. When I was testing my draw function, I set all the nubers to 1 just to check the visuals. However, only one gumdrop would draw. I fixed this when I realized that I had "clear canvas" within the nested loop, which meant every other circle was being drawn but erased. I fixed this by bringing the clear canvas out of the loop and only at the beginning of the function.
- Similar to my first problem, I had trouble calculating which row/col was clicked based on the user's clicked x and y coordinates. Because I used an instance variable for the size of the circle rather than writing 40 out, it made it conceptually confusing to me. However, I also wrote this out and drew a diagram, which helped me understand how the numbers related to each other and how I should set up the equation.

2. Describe whatever help (if any) that you received. Don’t include readings, lectures, and exercises, but do include any help from other sources, such as websites or people (including classmates and friends) and attribute them by name.
- I used Google images to reference colors for my gumdrops. 

3. Describe any serious problems you encountered while writing the program.
- When I first wrote my hasMatchingNeighbor function, I overcomplicated things (see below). Instead of checking for validity (of row/col) and value (whether the two colors were equal) in the same if statement, I went through a whole process that just complicated my code. Also, the code did not work because my true/false assignment was off, and the function would end before the negative numbers to everywhere it could go.

4. Did you do any of the challenges (see below)? If so, explain what you did.
- I did not do any of the challanges.

5. List any other feedback you have. Feel free to provide any feedback on how much you learned from doing the assignment, and whether you enjoyed doing it.
- I learned how to create Java functions and use them to make my code more readable and efficient. It took me a while to get used to defining everything properly (eg. setting a variable to int or String within function parameters)


Q3: Redundant code that i changed but wanted to keep to see where I went wrong.
/*
//this was meant to check the neighbors. It worked, but I didn't have a "justSpread" or other boolean variable to keep track of whether it had spread or not.
if (row == 0) { // first row
    if (col == 0) { //left corner, no above, no left
        System.out.println("upper left corner");
        if (clickedColor == board[row][col + 1]) { return true; } //right
        else if (clickedColor == board[row + 1][col]) { return true; } //below
    } else if (col == (board.length - 1)) { //right corner, no above, no right
        System.out.println("upper right corner");
        if (clickedColor == board[row][col - 1]) { return true; } //left
        else if (clickedColor == board[row + 1][col]) { return true; } //below
    } else { //middle two; no above
        System.out.println("upper middle");
        if (clickedColor == board[row][col + 1]) { return true; } //right
        else if (clickedColor == board[row][col - 1]) { return true; } //left
        else if (clickedColor == board[row + 1][col]) { return true; } //below
    }
} else if (row != (board.length - 1)) { //not the last row
    if (col == 0) { //left; no left
        System.out.println("left");
        if (clickedColor == board[row - 1][col]) { return true; } //above
        else if (clickedColor == board[row][col + 1]) { return true; } //right
        else if (clickedColor == board[row + 1][col]) { return true; } //below
    } else if (col == (board.length - 1)) { //right; no right
        System.out.println("right");
        if (clickedColor == board[row - 1][col]) { return true; } //above
        else if (clickedColor == board[row][col - 1]) { return true; } //left
        else if (clickedColor == board[row + 1][col]) { return true; } //below
    } else { //just the middle.
        if (clickedColor == board[row - 1][col]) { return true; } //above
        else if (clickedColor == board[row][col + 1]) { return true; } //right
        else if (clickedColor == board[row][col - 1]) { return true; } //left
        else if (clickedColor == board[row + 1][col]) { return true; } //below
    }
} else if (row == (board.length - 1)) { //last row
    if (col == 0) { //left corner, no above, no left
        System.out.println("bottom left corner");
        if (clickedColor == board[row - 1][col]) { return true; } //above
        else if (clickedColor == board[row][col + 1]) { return true; } //right
    } else if (col == (board.length - 1)) { //right corner, no above, no right
        System.out.println("bottom right corner");
        if (clickedColor == board[row - 1][col]) { return true; } //above
        else if (clickedColor == board[row][col - 1]) { return true; } //left
    } else { //middle two; no above
        System.out.println("middle two corner");
        if (clickedColor == board[row - 1][col]) { return true; } //above
        else if (clickedColor == board[row][col + 1]) { return true; } //right
        else if (clickedColor == board[row][col - 1]) { return true; } //left
    }
}*/