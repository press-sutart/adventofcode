# Day 8

I found the problem classic, though it really depends if you recognize that the Elves are executing [Kruskal's algorithm](https://en.wikipedia.org/wiki/Kruskal%27s_algorithm) for minimum spanning trees. 

We can evaluate the straight-line distance for all $O(N^2)$ pairs of $N$ junction boxes and sort them in increasing order to obtain the order in which the Elves attempt to connect the circuit. This takes $O(N^2 \log N)$ time. Since we only need to compare distances, it suffices to compute the squared distances (i.e., no need for square root) and compare those instead.

We use a [disjoint-set data structure](https://en.wikipedia.org/wiki/Disjoint-set_data_structure) to store the disjoint subgraphs at each step. Since Part 1 requires us to know the sizes of each circuit, we can store them in the data structure and use union by size, where smaller subgraphs are always merged into larger subgraphs during a merge operation. Combined with the path compression technique, connecting all edges (until the graph becomes connected) requires $O(N^2 \alpha(N))$ time where $\alpha$ denotes the extremely slow-growing [inverse Ackermann function](https://en.wikipedia.org/wiki/Ackermann_function#Inverse).

There are asymptotically faster ways to compute the minimum spanning tree in Euclidean space, but they might not be usable for this problem as the Elves are already using Kruskal's algorithm.