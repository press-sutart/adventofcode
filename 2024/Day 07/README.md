# Day 7

Learning Java again. I experienced firsthand how Java doesn't do implicit typecasting, so passing `int`s to `Math.pow(double, double)` isn't allowed, for example. I suppose this encourages healthy programming practices of making sure your types always match, but this is even stricter than C++ which I'm used to...

## Part 1

Suppose there are $`n`$ numbers (not counting the test value that we aim to obtain), then there are $`n-1`$ operators. I brute-forced all $`2^{n-1}`$ possible combinations of `+` and `*` operators between the numbers. To iterate over the combinations, I used a bitmask, which is an `int` that ranges from $`0`$ to $`2^{n-1}-1`$. The $`n-1`$ bits of the bitmask describe the operators in order, where off and on bits represent `+` and `*` respectively. The $`i`$-th bit of the bitmask can be checked using `bitmask & (1 << i)`.

## Part 2

Now there are three operators: `+`, `*` and concatenation. A natural extension of Part 1 would be to encode a combination of operators in a *tritmask*: a series of *trits* that can take on the values 0, 1 and 2. While it is possible to represent a tritmask as an `int`, there isn't a way to conveniently retrieve the trit at some index like the bit manipulations in Part 1, so I decided to write my own tritmask class instead. The constructor takes in an `int` value and calculates the trits which are stored as an array of `int`s. The class has a method to get the trit at a given index, which simply reads from the array.