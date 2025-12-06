# Day 18

An easy puzzle? On Day 18??

In Part 1, I ran breadth-first search (BFS) on the grid containing the first 1024 obstacles to find the minimum distance from the start to the end. In Part 2, I used binary search to find the minimum number of obstacles needed until no path exists. If that minimum number is $x$, the answer is the $x$-th obstacle (or $x-1$, counting from $0$).

Like recent grid puzzles, instead of using 2D arrays, I used `HashMap`s from `Coords` to the required data type. In particular, I used a `HashMap<Coords, Integer>` to store the time that a location becomes blocked.