# Day 25

Quick and easy problem to round off AOC 2024.

The input can be split into schematic grids of size $`7 \times 5`$ describing locks and keys. If the top row of a grid contains `#`, it is a lock; otherwise, it is a key. The pin heights of a lock are the largest row indices containing `#` for each column. To find the pin heights of a key, I inverted the grid vertically and used the same code for finding the pin heights of a lock.

I stored the pin heights of each lock and key as an `ArrayList<Integer>` of size $5$. I used an `ArrayList<ArrayList<Integer>>` called `locks` and another called `keys` to collect the pin heights of all locks and all keys respectively.

I iterated over all pairs of locks and keys. The pair fits without overlapping if and only if, for all indices $0$ to $4$, the sum of the lock pin height and the key pin height at the index is at most $5$.