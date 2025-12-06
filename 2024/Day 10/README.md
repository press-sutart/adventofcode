# Day 10

Back to Java.

Movement in the grid is one-way, in the sense that if we are on a position with height $`i`$, we can only move to neighbouring positions with height $`i+1`$. This allows a dynamic programming approach for both parts, where the state parameters are positions on the grid and transitions are movements. The base states are the positions with height $`9`$. Since the states and transitions of both parts are almost identical, my codes for both parts are similar as well.

I did dynamic programming by defining a class. A method of the class describes the DP recursion, while a `HashMap` memoises previously computed results. 

## Part 1

The question asks how many $9$'s are reachable from each $0$. A variant of the task that is solvable using DP is as follows: what is the set of $9$'s that are reachable from each position? The base case is easy: for any $9$, the only reachable $9$ is itself. As for the transition, suppose the current position has height $i < 9$. Then the set of $9$'s reachable from the current position is the union of sets of $9$'s reachable from the neighbouring positions we can move to of height $i+1$.

To solve the original problem, I called the DP function on each location of a $0$ and summed up the sizes of the sets.

Let the size of the grid be $`n \times n`$ (my input has $n = 55$) and the maximum height be $h$ (here $h = 9$). Since the graph is grid-shaped, the maximum number of maximum height positions reachable from any position is proportional to $h$. There are $`O(n^2)`$ states, a transition needs $`O(h)`$ time and memoising a single result needs $`O(h)`$ space. Hence, the solution takes $`O(n^2 h)`$ time and space.

## Part 2

The question asks how many paths to $9$'s there are from each $0$. A variant of the task that is solvable using DP is as follows: how many paths to $9$'s are there from each position? Any $9$ has only one path to a $9$ (not moving at all), while the number of paths to $9$'s from a position with height $i < 9$ is the sum of numbers of paths to $9$'s from its neighbouring positions of height $i+1$.

I called the DP function on each location of a $0$ and summed up the results.

There are $`O(n^2)`$ states, a transition needs constant time and memoising a single result needs constant space. Hence, the solution takes $`O(n^2)`$ time and space.
