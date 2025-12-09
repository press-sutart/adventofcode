# Day 4

Classic problem.

## Part 1

Iterate over all positions on the grid. If a position contains a paper roll, iterate over all its neighbours and count paper rolls.

As an implementation note, to iterate over all neighbours $`(nr, nc)`$ of $`(r, c)`$, we can define arrays $`DR`$ and $`DC`$ of length $8$ such that taking one step to a neighbour is equivalent to moving by the vector $`(DR[d], DC[d])`$ where $`0 \leq d \leq 7`$.

```
const int DR[8] = {1, 1, 1, 0, -1, -1, -1, 0};
const int DC[8] = {1, 0, -1, -1, -1, 0, 1, 1};
for (int d = 0; d < 8; d++) {
    int nr = r + DR[d], nc = c + DC[d];
    // do something
}
```

Time complexity: $`O(RC)`$ where the grid's dimensions are $`R \times C`$.

## Part 2

A naive way to develop the solution from Part 1 is as follows.

1. Similar to Part 1, iterate over the entire grid to identify paper rolls that can be removed, and remove them.
2. If at least one paper roll is removed, repeat from Step 1.

This wastes time as we possibly iterate over huge sections of the grid that do not have any paper rolls that can be removed. A minor improvement is to store positions of paper rolls from one iteration of Step 1 and iterate over them in the next. Regardless, we run into the problem where many runs of Step 1 may be needed to remove all paper rolls. An adversarial test case can force $`O(RC)`$ repetitions. For example, consider the snaking pattern below.

```
@@@@@@@@@@@@@@@@@@.
@@@@@@@@@@@@@@@@@@@
.................@@
.@@@@@@@@@@@@@@@@@@
@@@@@@@@@@@@@@@@@@.
@@.................
@@@@@@@@@@@@@@@@@@@
.@@@@@@@@@@@@@@@@@@
```

The only paper rolls that can be removed are those at the ends of the snake. This continues to hold after every run of Step 1, so we chip away 2 units of length each time. We would require $`O(RC)`$ iterations of Step 1, giving an overall time complexity of $`O(R^2 C^2)`$ which is not ideal.

We return to the condition that determines whether a paper roll can be removed. To state the obvious, if a paper roll has four or more paper rolls adjacent to it, it becomes removable exactly after some adjacent paper rolls are removed. But this hints towards a more efficient way of checking whether a paper roll is removable. We only need to re-check a paper roll if one of its neighbours is removed. This can be handled using recursion. The pseudocode below also returns the number of removed paper rolls.

```
// Remove the paper roll at the given position and recurse to neighbours.
FUNCTION Remove(pos):
    cnt = 1 // count itself
    update pos in grid to an empty space
    for all neighbours npos of pos:
        if paper at npos and paper at <4 neighbours of npos:
            cnt = cnt + Remove(npos)
    return cnt
```

Clearly, `Remove` can only be called on each position once. Also, the paper rolls that are removed in the first cycle must still be determined by iterating over the entire grid once like in Part 1:

```
cnt_p2 = 0
for all pos:
    if paper at pos and paper at <4 neighbours of pos:
        cnt_p2 = cnt_p2 + Remove(pos)
```

Time complexity: $`O(RC)`$