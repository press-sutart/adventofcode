# Day 6

A refreshing change from the previous algorithmic tasks, though this means I don't have much to write about.

For Part 1, I used `istringstream` to extract numbers and operators from rows.

For Part 2, I worked directly on the grid of characters in the input. Some key assumptions I used about the input:

- Operators are aligned to the bottom-left of one problem, so they indicate which column a problem ends (if processing from right to left).
- The rightmost column is not empty.