# Day 2

Still very easy. I chose to use Python this time, mainly for its superior string handling (compared to C++), as each line in the input can have varying numbers of integers. This also gave me the opportunity to experiment with higher-order functions in Python using the `functools` module.

## Part 1

*Abridged Statement: A list of integers is **safe** if it satisfies both of the following conditions: (1) it is strictly increasing or strictly decreasing; and (2) any two adjacent integers in the list differ by at most 3. Given many lists of integers, count the number of safe lists.*

Not much to add as we just check the conditions provided. But since all the conditions involve checking all pairs of adjacent elements in the list, it was a good chance to use higher-order functions in order to avoid code repetition. Checking a list with $n$ elements takes $`O(n)`$ time and space.

## Part 2

*Abridged Statement: A previously unsafe list is now considered safe if deleting exactly one element from it makes it safe. Count the number of safe lists.*

Deleting exactly one element from a list can be done easily with Python list slicing. I used the naive method of trying to delete each element. Checking a list with $n$ elements takes $`O(n^2)`$ time and $`O(n)`$ space.