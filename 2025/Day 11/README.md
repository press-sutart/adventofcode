# Day 11

Classic DP-on-DAG problem (that's "dynamic programming on directed acyclic graph"), especially Part 1.

## Part 1

The problem statement notes that data only flows in one way through each connection. The input gives us the output connections for each device. We can reverse them to determine the input connections for each device instead, which are more useful as we will see below.

Let $P(d)$ be the number of paths from $\verb|"you"|$ to some device $d$. We want to find $P(\verb|"out"|)$. From the start, we only know $P(\verb|"you"|) = 1$. For any other device $d$, let its input connections be $a_i$ for $i = 1, 2, \dots, k$. There are $P(a_i)$ paths from $\verb|"you"|$ to $d$ passing through $a_i$. Thus, we sum up all $P(a_i)$ to get $P(d)$.

By memoizing results, we avoid calculating any $P(d)$ more than once. The time complexity is $O(V+E)$ where $V$ and $E$ are the numbers of devices and connections respectively.

## Part 2

Using the solution to Part 1, we can find the number of paths $P(s,d)$ from any "source" device $s$ to any "destination" device $d$ in $O(V+E)$ time. (We implicitly set $s = \verb|"you"|$ in the previous section.)

Further abusing notation, let $P(s, c_1, c_2, \dots, c_m, d)$ be the number of paths from $s$ to $d$ passing through $c_1, c_2, \dots, c_m$ in that specific order. Convince yourself that it's equal to

$$P(s, c_1) \times P(c_1, c_2) \times \dots \times P(c_{m-1}, c_m) \times P(c_m, d).$$

The value that we want to calculate is

$$P(\verb|"svr"|, \verb|"dac"|, \verb|"fft"|, \verb|"out"|) + P(\verb|"svr"|, \verb|"fft"|, \verb|"dac"|, \verb|"out"|)$$

which we could do using the product method. We can optimize this by observing that either $P(\verb|"dac"|, \verb|"fft"|)$ or $P(\verb|"fft"|, \verb|"dac"|)$ is equal to $0$ due to the absence of cycles, therefore one of $P(\verb|"svr"|, \verb|"dac"|, \verb|"fft"|, \verb|"out"|)$ and $P(\verb|"svr"|, \verb|"fft"|, \verb|"dac"|, \verb|"out"|)$ is $0$ and can be ignored.

The time complexity is still $O(V+E)$.

