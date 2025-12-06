# Day 20

The 10th grid problem of the 10th rendition of Advent of Code. What a milestone!

I used breadth-first search (BFS) to find the shortest distance from the start $S$ to each track location, stored as a `HashMap<Coords, Integer>`. The size of this `HashMap` is the number of tracks along the path, so the distance without shortcuts is one less than it. (Alternatively, the distance without shortcuts can be retrieved from the `HashMap` using the coordinates of the end $E$.)

Suppose a racer uses a cheat to move from location $P$ to location $Q$. The distance traversed is the sum of the following three terms:

* The distance from $S$ to $P$, which can be retrieved from the `HashMap`;
* The distance from $P$ to $Q$, which is their Manhattan distance; and
* The distance from $Q$ to $E$, which is the distance without shortcuts minus the distance from $S$ to $Q$.

I precomputed all possible "offsets" $Q-P$ with length not more than 2 in Part 1 or 20 in Part 2. Since the `keySet` of the `HashMap` is the set of all track locations, I let each element of the set be $P$ and tried all offsets $Q-P$. I calculated the location of $Q$ and checked if it was in the `keySet`, which would mean it was a track. If so, I calculated the cheated distance using the method above and checked if it saved enough distance.

Let $n$ and $k$ be the size of the grid and the maximum cheat length respectively. My solution uses $O(nk^2)$ time. An alternative solution of iterating both $P$ and $Q$ over the `keySet` would use $O(n^2)$ time.