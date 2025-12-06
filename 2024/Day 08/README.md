# Day 8

I was horrified to discover Java doesn't come with a `Pair` or `Point` class. Fortunately, it was convenient to write a budget class using `record` to represent 2D coordinates. Records implicitly come with their own `equals` and hashing methods and work with Java's `HashMap` and `HashSet` out of the box. However, to complete the emotional rollercoaster, it was appalling to discover that Java doesn't support operator overloading. I had to make do with inelegant code like `p1.add(p2)`.

## Part 1

Every ordered pair of distinct antennae of the same type at the coordinates $`ant_1 = (x_1, y_1)`$ and $`ant_2 = (x_2, y_2)`$ produces one antinode at the coordinates $`2ant_1 - ant_2`$. Of course, antinodes that fall outside the map area should be ignored and duplicate antinodes produced by several different pairs should be counted only once.

I defined a record `Coords` that describes 2D coordinates. I used a `HashMap<Character, ArrayList<Coords>>` to store the locations of the antennae, split by their frequencies. The `Character` keys are input characters describing frequencies of antennae. For each frequency in the input, I iterated over all pairs of distinct antennae of that frequency and calculated the locations of the two antinodes produced by that pair. If an antinode location is within the map area, I added it to a `HashSet<Coords>` which handles duplicates by design.

Let the grid dimensions be $`n \times n`$ (my input has $`n = 50`$) and let the number of antennae with frequency $`i`$ be $`a_i`$. My solution uses $`O(n^2 + \sum a_i^2)`$ time and space.

## Part 2

Every ordered pair of distinct antennae of the same type $`ant_1`$ and $`ant_2`$ produces antinodes at $`ant_1 + k(ant_1 - ant_2)`$ where $`k = 0, 1, 2, \dots`$. The antinodes lie on a straight line such that for sufficiently large $`k`$, the antinode falls outside the map area, while all other antinodes fall within the map area. I found all antinodes by checking all $`k`$ starting from $`0`$ until the location is out of bounds. Similar to Part 1, all antinodes can be added to the `HashSet`.

It is easy to show that a pair of antennae produces at most $`n`$ antinodes. Hence, my solution uses $`O(n^2 + n \sum a_i^2)`$ time and $`O(n^2 + \sum a_i^2)`$ space.