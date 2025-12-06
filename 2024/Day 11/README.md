# Day 11

I used a dynamic programming approach. The implementation details are largely similar to Day 10: I wrapped in a class the DP recursion and a `HashMap` that memoises results.

To start, notice that blinking transforms each stone individually. We don't have to simulate the transformation of all stones together after each blink. Rather, we just need to process each stone individually to find how many stones there are in the end starting from each single stone, then sum the results up.

A stone can be described by two parameters $n, s$, which are respectively the number engraved on the stone and the number of steps remaining. Let $`DP(n, s)`$ be the number of stones in the end, where $n, s$ describe a starting stone. We have

$$
DP(n, s) =
\begin{cases}
    1, & \text{if $s = 0$} \\
    DP(1, s - 1), & \text{if $n = 0$} \\
    DP(n_1, s - 1) + DP(n_2, s - 1), & \text{if even number of digits in $n$} \\
    DP(2024n, s - 1), & \text{otherwise}
\end{cases}
$$

Memoisation is very powerful in Part 2. Even though the answer is more than $`10^{14}`$, my program ran in under a second. My `HashMap` stored results for just above $130,000$ states. These suggest that naive recursion would visit a significant number of duplicate states, which isn't too surpising as I expect stones with certain numbers, especially small numbers, to appear many times in a somewhat cyclic manner.

On the other hand, I made an assumption that stone numbers will not explode into infinity. The basis for this assumption is meta: I thought the task creator is obligated to keep things fair for all participants regardless of the programming language they use, and that includes whether they support big integer types. It would force him to put only well-behaved numbers in the input, which would never exceed 64-bit integer limits. Of course, it's also possible that the entire recurrence relation itself is well-behaved and never creates numbers that grow too large, but we've gone a little too close to Collatz conjecture territory at this point.