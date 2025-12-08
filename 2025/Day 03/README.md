# Day 3

I have solved a similar problem before.

## Solution

Let $N$ and $M$ be the number of banks and the number of batteries per bank respectively. Let $D$ be the number of batteries to choose ($2$ and $12$ in both parts respectively). It suffices to maximize the result for each bank independently and sum them up.

Since the result is a concatenation of the chosen digits, it is always optimal to greedily maximize digits starting from the left as they are more significant. As such, we pick digits from left to right. If the maximum digit appears multiple times, we should choose the leftmost index so that we have more choices later. Naturally, the first (leftmost) digit cannot be too far to the right, so that there are enough digits to choose from later. Hence, our procedure is as follows.

- For the first digit, choose the largest digit from the indices $[0, M-D]$, breaking ties by preferring smaller indices. We pick index $i_1$.
- For the second digit, choose the largest digit from the indices $[i_1+1, M-D+1]$, breaking ties by preferring smaller indices. We pick index $i_2$.
- ...
- For the $D$-th digit, choose the largest digit from the indices $[i_{D-1}+1, M-1]$, breaking ties by preferring smaller indices. We pick index $i_D$.

We maintain two pointers representing the ends of the range of indices we are considering, as well as a frequency table of each digit in that range of indices. Whenever the left or right end of the range moves a distance of $1$, the frequency table can be updated in constant time. We can find the maximum digit by iterating over the frequency table. To find the first occurrence of the maximum digit, knowing the value of the maximum digit, it suffices to advance the left pointer rightwards until it is found.

Since each pointer starts on the left and only needs to advance rightwards, there are $O(M)$ updates to the pointers and the frequency table. This does not change with $D$. The time complexity to solve the problem for each bank is $O(M)$, giving an asymptotically optimal solution of $O(NM)$ for all banks.