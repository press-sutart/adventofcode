# Day 16

今天也是Java

## Part 1

This problem is a single-source shortest-path problem. The vertices are combinations of locations on the grid and direction. The edges are the possible actions of a reindeer: move forward and rotate. The edge weights are the costs of these actions. I solved it using Dijkstra's algorithm.

As usual, I used my `Coords` record for 2D grids. I added rotate methods. Like yesterday, I stored the grid as a `HashMap<Coords, Character>`.

I represented a reindeer's state in the grid using a record `Position` that contains a `Coords location` and a `Coords direction`. `Position` has methods for moving forward and rotating clockwise and counterclockwise. The minimum cost from the start position (starting coordinates and facing East) is stored in a `HashMap<Position, Integer>`.

Finally, I have another record `DijkstraState` containing an `int cost` and a `Position`, as well as a method to compare two such records by cost. This was needed to create a `PriorityQueue<DijkstraState>` for use in Dijkstra's algorithm.

Consider the minimum costs to reach any end position (i.e., end coordinates and facing one of four cardinal directions). The answer is the minimum among them.

## Part 2

For a middle position $M$ containing location and direction, we are interested in

1. The minimum cost from the start position to $M$
2. The minimum cost from $M$ to any end position

Let `minCost` be the answer to Part 1. $M$ lies on a best path if and only if the two costs above add up to `minCost`.

Suppose a path producing the second cost is reversed. We obtain a path that starts at the end coordinates facing some direction and ends at the middle coordinates facing the opposite direction as $M$. Hence, the second cost is equal to the minimum cost of such a path.

I used Dijkstra's algorithm to create `HashMap<Position, Integer>`s of minimum costs from the start position and from all end positions. I iterated over all positions in the grid to check if they lie on a best path based on the conditions described above.