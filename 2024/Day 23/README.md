# Day 23

Non-grid graph theory. Fortunately, I have some experience with it.

Computers and connections are vertices and edges respectively. The label of a vertex is the name of its corresponding computer with data type `String`.

From the input, I constructed the adjacency list of the graph. It maps each vertex to the set of its neighbouring vertices, so I let its type be `HashMap<String, HashSet<String>>`. Edges are undirected, so the two endpoints of an edge are each other's neighbours. Let $u,v$ be the endpoints, then $u$ is added to the set belonging to $v$, whereas $v$ is added to the set belonging to $u$.

The key set of the map is the set of vertices in the graph. Call it $V$.

## Part 1

Let a *triangle* be a set of three vertices such that a pair exists between each pair of them. Each triangle can be stored as a `HashSet<String>` of size $3$. Java considers two `HashSet<String>`s as equal (using the `equals()` method) if and only if they contain the same elements, ignoring order. Hence, a set of distinct triangles can be conveniently stored as a `HashSet<HashSet<String>>`.

One way to find all triangles including some vertex $s$ is given as follows.

1. Iterate over all neighbours $u$ of $s$.
2. Iterate over all neighbours $v$ of $u$.
3. Iterate over all neighbours $w$ of $v$.
4. If $s=w$, then $\\{s,u,v\\}$ is a triangle.

I implemented this using recursion. I called the function once for each vertex with a label that starts with `t`. The answer is the size of the union of all `HashSet<HashSet<String>>` return values.

## Part 2

A subset of vertices that are all connected to each other is known as a *clique*. A triangle is a clique of size $3$. Similarly, a clique can be stored as a `HashSet<String>`. The task is to find the clique of maximum size.

I wrote a recursive function to find cliques. For any set of vertices $S$, $getCliques(S)$ returns the set of cliques that are supersets of $S$. I attempted to add vertices $u$ to $S$. This addition is possible if and only if $u$ is a mutual neighbour of all vertices in $S$ and is not already in $S$. Let $U$ be the set of all such $u$, then

$$
getCliques(S) = \\{ S \\} \cup \bigcup_{u \in U} getCliques(S \cup \\{u\\})
$$

Instead of searching the entire vertex set for $u$, it is sufficient to pick an arbitrary vertex $`v \in S`$ and iterate over the set of neighbours of $v$.

The set of all cliques is

$$
\bigcup_{v \in V} getCliques(\\{ v \\})
$$

However, this is extremely slow. Observe that a clique of size $n$ is found $n!$ times, corresponding to the $n!$ different orders that the $n$ vertices get added. A major optimisation I used is to ensure that each clique is only found once. Instead of arbitrarily choosing $v$, I set it to the lexicographically largest element of $S$ and iterated over lexicographically larger neighbours of $v$. This ensures that vertices in a clique are added in lexicographical order. To avoid having to find the lexicographically largest element of $S$, I let it be a parameter of the function. Below, I call it $c$.

In summary, using the optimisation, $getCliques$ is computed as

$$
getCliques(c, S) = \\{ S \\} \cup \bigcup_{u \in U} getCliques(u, S \cup \\{ u \\})
$$

where

$$
U = \\{ u \in neighbours(c) : (c < u) \wedge (u \not\in S) \wedge \forall s \in S \\, (s \in neighbours(u)) \\}
$$

and the set of all cliques is

$$
\bigcup_{v \in V} getCliques(v, \\{ v \\})
$$
