# Day 12

I used Java. I think my Java AOC solutions are more or less solidifying into the current structure, where some methods handle the input and a nested class does the heavy algorithmic lifting.

I defined a `Coords` record that stores two `int`s representing a position. In Part 2, I additionally defined a `Fence` record that holds a `Coords` position and an `int` direction (between 0 and 3 inclusive, describing four cardinal directions).

## Part 1

The task statement asks to find the product of the area and perimeter of all regions in the grid, where each region is a contiguous area with the same character. Given a "source" location, the entire region containing it can be found using flood fill. I used a queue to perform flood fill similar to breadth-first search. Using a 2D Boolean array `isVisited` to check visited positions, I avoided processing locations more than once within a region and processing a region more than once. The area and perimeter of a region can be calculated based on the definitions given in the task.

Let $`n`$ be the size of the grid. My solution uses $`O(n)`$ time and space.

## Part 2

During flood fill, I found and stored all fences in a `HashSet<Fence>`. To find all sides, I repeated the following steps while the `HashSet` is nonempty: take any fence and remove all fences from the same side as that fence. The fences that form one side all face the same direction and are adjacent to each other in the directions perpendicular to the direction they face. Hence, if I initially pick a fence facing upwards, I search coordinates to the left and right for fences that face upwards. Clearly the number of sides is the number of iterations of the loop.

Since a `HashSet` supports insertion and deletion in constant time, my solution uses $`O(n)`$ time and space.