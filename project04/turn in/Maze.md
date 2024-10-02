What bugs and conceptual difficulties did you encounter? How did you overcome them? What did you learn?
- This project was pretty straightforward to me. The several working parts in the project (eg. converting the text file, drawing the maze, etc) seemed complicated at first, but walking through them slowly helped. Referencing previous projects (like the gumdrop project) guided me through the drawing process as well. The bugs I encountered were mostly typos and/or small mistakes, rather than the concepts. 
Describe whatever help (if any) that you received. Don’t include readings, lectures, and exercises, but do include any help from other sources, such as websites or people (including classmates and friends) and attribute them by name.
- To create a new color based on RGB values: https://stackoverflow.com/questions/42855224/how-to-add-rgb-values-into-setcolor-in-java 
Describe any serious problems you encountered while writing the program.
- My canSolve method was not working properly because when I checked east, the conditional was not correct. Instead of (col<mazeMap[row].length), I checked (col<mazeMap.length), which caused an error when the width of the maze was less than the height. 
- I did not know where to define and increment the number of calls (counting the recursive functions). At first, I placed it inside each directional if statement (once within checking north, one within south, etc), but realized that it works best when I call it at the start of the canSolve()/directionalSolve() methods. That way, I can ensure that they are always counted.
- In general, familiarizing myself with where each method was and how each method related to the others helped me see the bigger picture of the project. Without it, I found myself getting lost in the code. Especially as it got longer, and the program was too long for a single screen to handle, I had to orient myself and make sure I knew where I was, where the other methods were, and how the variables (both local and instance variables) interacted with one another.
Did you do any of the challenges (see below)? If so, explain what you did.
- I did not complete any of the challenges. 
List any other feedback you have. Feel free to provide any feedback on how much you learned from doing the assignment, and whether you enjoyed doing it.
- I enjoyed this project, and I understood recursion better through working with it in my code. I also learned how to utilize instance variables across different classes. 

