# Project 3 Prep

**For tessellating hexagons, one of the hardest parts is figuring out where to place each hexagon/how to easily place hexagons on screen in an algorithmic way.
After looking at your own implementation, consider the implementation provided near the end of the lab.
How did your implementation differ from the given one? What lessons can be learned from it?**

Answer:

I was trying to add everything in one class which made it hard to understand, whereas the TA's implementation involved
3 different classes, each with a specific task.

**Can you think of an analogy between the process of tessellating hexagons and randomly generating a world using rooms and hallways?
What is the hexagon and what is the tesselation on the Project 3 side?**

Answer:

instead of positioning hexagons I can randomly choose to place a room or hallway using the same method.
**If you were to start working on world generation, what kind of method would you think of writing first? 
Think back to the lab and the process used to eventually get to tessellating hexagons.**

Answer:

a position method, where I could get the x and y location
**What distinguishes a hallway from a room? How are they similar?**

Answer: Hallways are of width 1 with a random length, rooms have random width a length.
Both are connected, and the floors of the rooms have to be distinguishable from the empty spaces.
 
