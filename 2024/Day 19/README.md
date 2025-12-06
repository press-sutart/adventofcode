# Day 19

Decently simple problem. I used Java.

## Part 1

For each design, we separately solve the problem: can the design string be formed by concatenating towel strings together?

Let $D$ be the design string and let $n$ be its length. Let $T$ be the set of towel strings. Finally let $`isMatchable(i)`$ be a Boolean value describing whether it is possible to form the substring $`D[i,n)`$ for $`0 \le i \le n`$. $`isMatchable`$ can be defined recursively as follows:

$$
isMatchable(i) =
\begin{cases}
    true, & \text{if $i = n$} \\
    \bigvee_{j = i+1}^n (D[i,j) \in T \wedge isMatchable(j)), & \text{otherwise}
\end{cases}
$$

Implemented naively, it would take very long to calculate the required answer $`isMatchable(0)`$ as the total number of recursive calls grows exponentially with respect to $n$. Two quick and easy optimisations are: 

1. Return $`true`$ as soon as $`D[i,j) \in T \wedge isMatchable(j)`$ is true for one $j$, instead of evaluating it for all $j$
2. If $`D[i,j) \in T`$ is false, there is no need to evaluate $`isMatchable(j)`$

Using both optimisations, the program runs very quickly.

I used a `HashSet<String>` to store $T$. Its `contains()` method finds expected $O(1)$ strings with the same hash as $`D[i,j)`$ and needs $O(j-i) = O(n)$ time to check equality of strings.

## Part 2

Let $`countMatches(i)`$ be the number of ways to form the substring $`D[i,n)`$ by concatenating strings in $T$, for $`0 \le i \le n`$. The definitions of $`countMatches`$ and $`isMatchable`$ are similar:

$$
countMatches(i) =
\begin{cases}
    1, & \text{if $i = n$} \\
    \sum_{j | i+1 \leq j \leq n, D[i,j) \in T} countMatches(j), & \text{otherwise}
\end{cases}
$$

Optimisation 1 cannot be used here. Instead, using an array, I memoised the results of $`countMatches`$ that get computed. Now the heavy computations for each $`countMatches(i)`$ are only performed once. The time complexity of the solution is $O(n^3)$.
