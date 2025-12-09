# Day 5

Classic problem.

Let $N$ and $M$ be the number of ranges and the number of ingredients respectively. The asymptotically faster solution to Part 1 relies on Part 2, so I discuss Part 2 first.

## Part 2

Working with overlapping ranges is difficult. We explore how to merge them into disjoint ranges.

Imagine processing IDs starting from 0 and counting up. Suppose we maintain the number of ranges that contain our ID. When we pass the start of a range, the number of ranges containing the ID increases by 1. Similarly, when we pass the end of a range, the number of ranges decreases by 1. By recording the ID boundaries where the number of containing ranges changes from 0 to 1 or from 1 to 0, we find disjoint ranges of IDs with at least one containing range.

There are too many possible IDs to use this approach directly. Observe that the number of containing ranges only changes at input-range endpoints, so it suffices to consider input-range endpoints to find disjoint-range endpoints. We sort the input-range endpoints and process them in increasing order.

Now that we have disjoint ranges, it is trivial to sum up their lengths.

Time complexity: $O(N \log N)$ where $N$ is the number of ranges, due to sorting.

## Part 1

The procedure described above produces disjoint ranges $`[s_j, e_j]`$ which are sorted in increasing order, so that $`s_1 \leq e_1 < s_2 \leq e_2 < \dots`$. Given an ID $`i`$, we use binary search on start IDs to find the largest $`s_k`$ with $`s_k \leq i`$. We see that if $`j \neq k`$, then the range $`[s_j, e_j]`$ cannot contain $`i`$.

- If $`j < k`$, then $`e_j < s_k \leq i`$ by ordering of disjoint ranges.
- If $`j > k`$, then $`i < s_k`$ as a result of binary search.

Hence, it suffices to check if $`i`$ is in the range $`[s_k, e_k]`$.

Time complexity: $`O(N \log N + M \log N)`$ where $`M`$ is the number of ingredients.