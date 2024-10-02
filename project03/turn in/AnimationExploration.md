What bugs and conceptual difficulties did you encounter? How did you overcome them? What did you learn?
- Rotate class:
  - When I was first testing the code, the rotations did not seem to be working. The code would be there, and the canvas would draw, but there was no rotation. Turns out, I forgot to add a line to show the canvas! Once I added canvas.show(), the code worked and displayed itself correctly.
- TranslationAnimation class:
  - Initially, I did not make frameX or frameY into doubles. This caused errors in the code, because frameX/Y require precise decimal evaluation. I solved the problem when I cast the quotient into a double.
- AnimationSequence:
  - At first, I called the playOn sequence on the ComboPolygon everytime I needed to draw a single frame. This caused problems and only the triangles (rotations) or the squares (translation) showed up, rather than everything showing up together. I fixed this by calling changing it from `rotations[curr].drawOn(canvas);` to `rotations[curr].getPolygon().drawOn(canvas);` (line 85). This ensured that every polygon was being drawn through each frame of the animation.
  - Once again, I used print statements within my functions to gauge my progress. It allowed me to test if and how the code was working. 

Describe whatever help (if any) that you received. Don’t include readings, lectures, and exercises, but do include any help from other sources, such as websites or people (including classmates and friends) and attribute them by name.
- Polygon class:
  - Referenced for string concatenation (while testing): https://www.baeldung.com/java-string-concatenation

Describe any serious problems you encountered while writing the program.
- The biggest problem was keeping track of all the animations, and making sure that each frame was being drawn at the right times. If not, the canvas would only display a single polygon, only the rotation or translation, or nothing would show up at all. 

Did you do any of the challenges (see below)? If so, explain what you did.
- I did not complete any of the challenges.

List any other feedback you have. Feel free to provide any feedback on how much you learned from doing the assignment, and whether you enjoyed doing it.
- This project helped me take my time in writing my code, so that at each step, I would visualize the steps taking place. I now have a better understanding of how classes work, and how they interact with one another (eg. creating a class specifically for groups of polygons, separate classes for each type of animation, etc). 