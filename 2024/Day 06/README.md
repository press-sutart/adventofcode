# Day 6

Classic problem. I continued to get used to Java.

## Part 1

I simulated the movement of the guard until leaving the map area (the task wording implies that this always happens). To do so, I kept track of the guard's location and direction. I stored direction as an `int` which is equal to 0, 1, 2, 3 for up, right, down, left respectively, so a 90-degree-clockwise turn is equivalent to adding 1 to the direction modulo 4. I used a 2D Boolean array `isVisited` to keep track of which locations are visited by the guard. After simulating the guard's movement, the answer is the number of `true` values in the array.

Notice that a cycle exists if and only if the guard lands on some position in the same direction more than once. The task implies that the guard will never move in a cycle, so each position can be visited at most 4 times, once in each direction. Hence, the number of steps in the simulation will never exceed 4 times the size of the map.

The time and memory usage is roughly proportional to the size of the map.

## Part 2

The method described in Part 1 is insufficient to detect cycles, but as stated above, a cycle exists if and only if the guard lands on some position in the same direction more than once.

To check this, I made the `isVisited` array 3D, where the first two dimensions are the row and column of the position (same as Part 1) while the third dimension is the direction (0 to 3). In each step of the simulation, checking `isVisited` on the current location and direction allows to detect cycles. The simulation ends when the guard exits the map area or when a cycle is detected. A cycle can be detected the first time some location and direction is visited twice, so that the number of steps in the simulation is at most 4 times the size of the map, plus 1.

For each empty space `.` on the map, I tested adding an obstruction there and simulating the guard's movement to see if the guard ends up moving in a cycle. The number of candidate locations is clearly at most the size of the map, so the running time is, at worst, roughly proportional to the square of the size of the map. Using the full puzzle input, my program took about 12 seconds to run on my little laptop.