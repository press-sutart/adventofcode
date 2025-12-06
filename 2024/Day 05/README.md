# Day 5

Baby's second Java program (after Hello World). Besides my struggles with Java and VSCode, maybe this problem is moderately difficult?

## Part 1

I observed that page $`x`$ cannot be before $`y`$ if the rule $`y|x`$ exists. Hence, I let $`orderingIsAllowed`$ be a 2D Boolean array where $`orderingIsAllowed[x][y]`$ determines if page $`x`$ can be placed before page $`y`$. All its values are initially `true` and for each rule $`y|x`$ I changed $`orderingIsAllowed[x][y]`$ to `false`. Let $`n,p`$ be the number of rules and the largest page number respectively, then the array has dimensions $`p \times p`$ and takes $`O(n + p^2)`$ time to create.

To check if a sequence of pages is valid, for each pair of pages $`x,y`$ such that $`x`$ is before $`y`$ in the sequence, $`orderingIsAllowed[x][y]`$ must be `true`. With the assumption that every sequence has odd length, the middle page is retrieved from the index $`\frac{l-1}{2}`$ where $`l`$ is the length of the sequence.

 My solution uses $`O(n + p^2 + \sum l^2)`$ time and $`O(p^2 + \max l)`$ space.

## Part 2

I first checked if a sequence is valid as above. If not, I assumed from the context of the task that there exists a reordering of the sequence that is valid (i.e., no contradictions in the rules) and proceeded to construct the valid sequence.

I used a Boolean array of length $`l`$ to check if a page number from the original sequence has been used in constructing the valid sequence. I wrote an algorithm with $`l`$ steps, where step $`i`$ finds a page that can be placed in the $`i`$-th position of the valid sequence. A page $`x`$ can be chosen if, for all other currently unused pages $`y`$, $`orderingIsAllowed[x][y]`$ is `true`. Checking if a page can be chosen takes $`O(l)`$ time, so a step takes $`O(l^2)`$ time, so the algorithm takes $`O(l^3)`$ time. The middle page number can be extracted in the same way as Part 1.

My solution uses $`O(n + p^2 + \sum l^3)`$ time and $O(p^2 + \max l)$ space.