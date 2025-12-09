# Day 7

Simple dynamic programming-ish problem.

## Part 1

This is a simulation task. Knowing which columns of row $r$ have tachyon beams, we use the rules stated in the problem to determine which columns of row $r+1$ have tachyon beams. At the same time, we can count the number of times the beam is split.

## Part 2

We only need to make slight modifications to Part 1. In Part 1, we only needed to know if each cell of the grid had a tachyon beam. In Part 2, we count the number of timelines where each cell has a tachyon beam instead. The number of timelines is additive: if row $r$ has three ways to produce a tachyon beam in one specific cell in row $r+1$, with $x$, $y$, $z$ timelines respectively, then that cell in row $r+1$ has a beam in $x+y+z$ timelines.

In both parts, the time complexity is $O(RC)$ where the dimensions of the grid are $R \times C$.