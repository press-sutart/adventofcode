# Day 14

ABSOLUTE CINEMA

I continue to abuse records in Java.

## Part 1

Let a robot's starting position and velocity be $`(p_x, p_y)`$ and $`(v_x, v_y)`$ respectively. After $`100`$ seconds, its position is

$$
((p_x + 100 v_x) \bmod 101, (p_y + 100 v_y) \bmod 103)
$$

The quadrant of a position can be determined by comparing the coordinates against the mid-width and mid-height ($50$ and $51$ respectively).

## Part 2

At first, I wrote a program that repeatedly prints the grid to standard output and waits for user input to continue, but I gave up after checking the first $`200`$ grids.

I switched to file output so that I could quickly scroll to look for patterns. VSCode also has a small file preview thing on the right which shows what the file would look like if it was zoomed out a lot. Scrolling through the first $`1000`$ grids, I found that at fixed time intervals, the robots seemed to arrange themselves in a range of columns (see the bottom-right of the picture).

![output file picture](https://github.com/press-sutart/AOC2024/blob/main/Day%2014/output.png)

Now checking the times at these intervals, I scrolled until I found the first Christmas tree.
