# Day 4

Easy problem. I used C++.

## Part 1

*Abridged Statement: Given a grid of letters, count the number of occurrences of the string `XMAS` in the grid. The letters of `XMAS` may be arranged horizontally, vertically or diagonally, including written backwards.*

I used brute force by checking each letter in the grid and assuming it to be the start, and checking each of 8 directions to see if the letters match `XMAS`. If they matched, I incremented a counter.

### Bonus

The trivial solution above runs in $`O(rcd|S|)`$ time where $`r \times c`$ is the size of the input, $`d`$ is the number of directions ($`8`$ here) and $`S`$ is the pattern string (`XMAS` here, so $`|S| = 4`$).

There is an asymptotically faster solution in $`O(rcd + |S|)`$ time, using string hashing with a polynomial rolling hash. First, calculate the hash of $`S`$ in $`O(|S|)`$ time. Now suppose we are finding all horizontal left-to-right occurrences of $`S`$. We consider each row separately. Each row has length $`c`$. We can find all occurrences of $`S`$ in each row in $`O(c)`$ time using Rabin-Karp algorithm, therefore taking $`O(rc)`$ time for one direction. This can be repeated in all other directions.

In the context of this problem, however, this solution is probably not faster than the trivial solution in practice. The trivial solution allows for pruning: as soon as we find one mismatch, we can deduce that two strings are not equal, without having to check all $`|S|`$ characters. The asymptotically faster solution might only shine when given large or nasty inputs. An example of a nasty input is where $`S`$ and the input grid of letters are comprised entirely of `A`s.

I did not implement this bonus solution.

## Part 2

*Abridged Statement: An **X-MAS** refers to two `MAS` arranged in the shape of an X. That is, the `MAS` are diagonal, can be written forwards or backwards, and their `A`s overlap. Count the number of X-MASes in the grid.*

Brute force again! Each letter in the grid is the centre of an X-MAS if and only if it satisfies all three conditions below:

1. The centre is an `A`;
2. Among the top-left and bottom-right letters, there is one `M` and one `S`;
3. Among the top-right and bottom-left letters, there is one `M` and one `S`.

The answer is the number of centres of X-MASes.