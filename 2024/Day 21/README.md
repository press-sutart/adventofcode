# Day 21

Massive difficulty spike for me. I referred to the AOC subreddit for hints about the order of pressing buttons when several choices are available.

I initially solved Part 1 using brute force. This was based on breadth-first search, so that all possible moves of small lengths are tested before moving on to test larger lengths. (This code is not shown here.) Clearly, this approach would not work for Part 2 as the search space is too large.

## Transitions

Let a *transition* be a process of moving from a button `s` to another not necessarily distinct button `e` and pressing button `e`, denoted as `s-e`. This is represented in my program as a record containing two characters. If a human were to enter the code `029A` on the numeric keypad, there would be four transitions, namely `A-0`, `0-2`, `2-9` and `9-A`.

## Layers

Suppose one robotic layer is placed between the human and the numeric keypad, so that a human uses a directional keypad to control a robot who interacts with the numeric keypad. I attempted to map each robot transition on the numeric keypad to human transitions on the directional keypad. For any robot transition `s-e` except the first, the human had to make the robot press `s` in a previous transition and therefore starts on `A`. For the first transition, assume the human starts on `A` anyway to allow for further addition of robot layers, since robots always start on `A`. 

It is somewhat intuitive that one should pick a shortest way to move the robotic arm. As a simple example, there is only one shortest way to move the robotic arm from `9` to `A`: press the buttons `vvvA`. Hence, the robot transition `9-A` maps to four human transitions, namely `A-v`, `v-v`, `v-v` and `v-A`. Notice that the same transition can appear more than once, as `v-v` does here, so the result can be stored as a map from transitions to integers like

```
{
    A-v: 1,
    v-v: 2,
    v-A: 1
}
```

However, there can be several shortest ways to move a robotic arm, such as the transition `2-9` in the task statement. While the zigzag pattern `^>^` is intuitively worst, it wasn't clear to me whether `^^>` is better than `>^^`. At this stage, I *(incorrectly)* assumed they were the same and arbitrarily picked one.

Every possible robot transition on the numeric keypad has its own such mapping, so I collected all of them into a map of robot transitions to maps of human transitions to integers. Its data type is

```
HashMap<Transition, HashMap<Transition, Long>>
```

Similarly, I constructed a layer map from robot directional transitions to maps of human directional transitions to integers. Here, I also incorrectly assumed that non-zigzag patterns were the same. I made an additional assumption that choosing the same pattern in every directional layer is optimal, which fortunately turns out to be accurate. (For example, given that using `<v` to move a robotic arm from `A` to `v` is best in one layer, we should use `<v` in every layer instead of alternating `<v` and `v<`.) Both layer maps are built during runtime.

## Counting Moves

It remains to pass all transitions on the numeric keypad through the numeric and directional layers. I keep track of a `HashMap<Transition, Long>` denoting how many times each transition must be done. From the input, I know all distinct transitions on the numeric keypad and how many times each numeric transition is done.

Using the numeric layer map, each numeric transition produces some directional transitions. The frequencies of identical directional transitions are summed up so that I have another `HashMap<Transition, Long>` denoting how many times each directional transition is done. Using the directional layer map, each directional transition produces some directional transitions which can be summed up similarly. The process of passing transitions through the directional layer is performed 25 times. The answer to Part 2 is the sum of the numbers of times each transition is done.

## Hint from Reddit

Naturally, my answer was too high as I did not choose optimal mappings in my transition layers. As mentioned above, I hypothesised that non-zigzag shortest patterns are best. However, up to two such patterns could exist for any start and end position. To move from `2` to `9`, these are `^^>` and `>^^`. They may use the same number of moves in one layer, but after several layers, `^^>` would use fewer moves than `>^^`.

Unable to find a rule and unsure if there even was one, I turned to Reddit. It turns out that a rule indeed exists: if only one non-zigzag shortest pattern is available, use it; otherwise, it is optimal to make all necessary `<` moves, then all `^` or `v` moves, and finally `>` moves. I don't know *why* this works, but thanks to it I solved the puzzle. In hindsight, I probably could have tested more ways to order moves to observe this fact on my own...

## Going Further

It turns out that the act of passing transitions through layers to get more transitions can be expressed in terms of matrix multiplication. First note that there are $`11 \times 11 = 121`$ unique numeric transitions and $`5 \times 5 = 25`$ unique directional transitions. Define the following three matrices: 

* Let $T$ be a $`121 \times 1`$ column matrix where each different entry represents the number of times each numeric transition must be done;
* Let $N$ be a $`25 \times 121`$ matrix describing the numeric layer map, so that each row describes the number of times a specific type of directional transition is created from each numeric transition; and
* Let $D$ be a $`25 \times 25`$ matrix similarly describing the directional layer map.

If there are $k$ robots controlling directional keypads, the transitions performed by the human at the end is given by the $`25 \times 1`$ column matrix $`D^{k}NT`$. This can be evaluated quickly even for large $k$, using matrix exponentiation with squaring.
