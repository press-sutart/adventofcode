# Day 1

Very easy problem, since it's the first day.

To allow myself to warm up to Advent of Code and to set up the repository, I used C++ as I'm most familiar with it in the context of solving small programming puzzles.

## Part 1

*Abridged Statement: Given two lists of numbers of equal length, find the absolute difference between their smallest elements, second-smallest elements and so on, then output the sum of these differences.*

Read the input into two `std::vector <int>`s. Let's call them `v[0]` and `v[1]`.

Sorting the vectors can be done using `std::sort` in $`O(n \log n)`$ time on average. 

After doing so, the $`i`$-th smallest elements of each vector can be accessed easily via `v[0][i]` and `v[1][i]`, so we just need to sum up the absolute differences of `v[0][i]` and `v[1][i]` over all $`i`$.

Time complexity: $`O(n \log n)`$; Space complexity: $`O(n)`$ where $`n`$ is the size of a list

## Part 2

*Abridged Statement: Given two lists of numbers of equal length, multiply each number in the first list by the number of occurrences of it in the second list and add up the products.*

Finding the number of occurrences of any number in the second list can be trivially done in $`O(n)`$ time without preprocessing, which is slow.

On the other hand, maintaining a frequency table (array) allows one to find the number of occurrences in $`O(1)`$ time at the cost of $`O(n)`$ preprocessing time and $`O(\max(v))`$ space where $`\max(v)`$ is the largest number in the input. This approach is expensive in terms of space usage.

While both of these solutions are sufficient due to the small input size, below we discuss a solution that is somewhat overkill and offers a better space-time tradeoff with $`O(n \log n)`$ preprocessing time, $`O(\log n)`$ query time and $`O(n)`$ space usage.

Let `freq` be a `std::map <int, int>` such that if `freq[x]` exists, then `x` appears `freq[x]` times in `v[1]`, whereas if `freq[x]` does not exist, then `x` does not appear in `v[1]`. This description already tells us how to query the number of occurrences of any `x` in `v[1]`. In the preprocessing stage, we construct `freq` like so. For each `x` in `v[1]`, if `freq[x]` does not exist yet, initialize `freq[x]` to $`1`$, otherwise increment it by $`1`$. The above-mentioned time complexities are due to the $`O(\log n)`$ access time of `std::map`.

It remains to iterate over all elements of `v[0]` to calculate the required products and sum them up. This process includes checking `freq` for each of $`n`$ elements of `v[0]`, which takes $`O(n \log n)`$ time.

Time complexity: $`O(n \log n)`$; Space complexity: $`O(n)`$ where $`n`$ is the size of a list
