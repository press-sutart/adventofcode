# Day 1

Easy and classic problem.

## Part 1

Since we are working with a circular dial with 100 positions, we can treat rotations as modulo-100 arithmetic. Rightward and leftward rotations are addition and subtraction respectively.

Implementations across languages are likely to differ based on how they handle modular subtraction. C++ uses truncated division for its [modulo operator](https://en.wikipedia.org/wiki/Modulo), i.e., the dividend and the remainder have the same sign. I made sure that intermediate dial positions in my code are always non-negative using this rather clunky piece of code:
```
dial_position = ((dial_position - clicks) % DIAL_NUMBERS + DIAL_NUMBERS) % DIAL_NUMBERS;
```
However, it isn't strictly necessary for Part 1 because we are only interested in the position 0 which turns out to be a special case in modular arithmetic.

Time complexity: $`O(n)`$ where $`n`$ is the number of rotations.

## Part 2

For each rotation, given the dial position before the rotation which we already compute in Part 1, we can count the number of times the dial passes through or lands on 0. My implementation effectively considers four different cases, which I don't consider elegant at all, but that's what I have ¯\\\_(ツ)_/¯

- Rightward rotation
- Leftward rotation starting from 0
- Leftward rotation not starting from 0, with sufficient distance to reach 0 at least once
- Leftward rotation otherwise (implicitly handled by not doing anything)

Time complexity: $`O(n)`$ where $`n`$ is the number of rotations.