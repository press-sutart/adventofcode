# Day 13

Java una vez más.

## Part 1

I extracted the numbers from the raw input strings using regex. For each test case (i.e., one claw machine), let the input numbers be $`A_x, A_y, B_x, B_y, P_x, P_y`$. We want to solve the following system of linear equations:

$$
\begin{align*}
a A_x + b B_x &= P_x \\
a A_y + b B_y &= P_y
\end{align*}
$$

If there are multiple solutions, we should find the one that minimises $`3a+b`$. As hinted in the task statement, it is sufficient to consider $`0 \leq a,b \leq 100`$. Suppose $`a`$ is fixed, then rearranging the equations gives

$$
b = \frac{P_x - a A_x}{B_x} = \frac{P_y - a A_y}{B_y}
$$

I brute forced each integer $`a`$ in the range $`0 \leq a \leq 100`$. For each $`a`$, I checked that the two fractional terms above are integers and are equal to each other. If so, they provide the value of $`b`$ and I have a solution in the form $`(a,b)`$.

## Part 2

Now after receiving $`P_x, P_y`$ in the input, they have to be increased by $`10^{13}`$ each. Clearly it's no longer possible to brute force, so I had to solve the equations. Quickly checking the input, I noticed that all numbers in the input are strictly positive, so I proceeded to solve the equations like how I would do on paper. Rearranging the first equation gives

$$
a = \frac{P_x - b B_x}{A_x}
$$

Substituting into the second equation and rearranging gives

$$
b (A_x B_y - B_x A_y) = A_x P_y - P_x A_y
$$

Let $`u = A_x B_y - B_x A_y`$ and $`v = A_x P_y - P_x A_y`$ so that $`bu = v`$. Consider four cases:

* $`u \neq 0, v \neq 0`$. There is a unique solution where $`b = \frac{v}{u}`$.
* $`u \neq 0, v = 0`$. There is a unique solution where $`b = 0`$.
* $`u = 0, v \neq 0`$. There is no solution.
* $`u = 0, v = 0`$. There are infinite solutions.

Naturally, only integer solutions are accepted, so for the first two cases I checked that the values for $`a,b`$ I got were indeed integers. 

For the final case, I got lazy to code and assumed that it did not apply to any case in the input. Fortunately, I was right. Nonetheless, here's how I would solve it. Here, the two equations are constant multiples of each other, so let's focus on the one in $`x`$.

Let $`d = \gcd (A_x, B_x)`$. By Bézout's identity, there exist $`\alpha,\beta`$ such that $`\alpha A_x + \beta B_x = d`$. One pair can be found using the extended Euclidean algorithm.

If $`d`$ divides $`P_x`$, the entire equation can be scaled to get $`a A_x + b B_x = P_x`$ where $`(a,b)`$ is an integer solution but not necessarily positive. All integer solutions are of the form $`(a - k \frac{B_x}{d}, b + k \frac{A_x}{d})`$ where $k$ is an integer. For some (possibly empty) range of $`k`$, both values are positive. The value of $`k`$ that gives the minimum cost is either the smallest or largest value of $`k`$.