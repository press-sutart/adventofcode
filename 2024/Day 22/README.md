# Day 22

Luckily a much easier problem than Day 21.

## Part 1

The evolution of a secret number into the next secret number can be equivalently written in terms of bitwise operations:

```
public long getNextSecret(long secret) {
    secret = ((secret << 6) ^ secret) & 16777215L;
    secret = ((secret >> 5) ^ secret) & 16777215L;
    secret = ((secret << 11) ^ secret) & 16777215L;
    return secret;
}
```

I simulated generating 2,000 secret numbers from each secret number in the input using the above function and added up the last secret numbers.

## Part 2

Firstly, for each secret number, I generated 2,000 secret numbers as above, then converted all 2,001 numbers to their ones digits. Consider the list of 2,001 ones digits.

Before finding the sum, I wanted to find the number of bananas that would be earned from one purchase by using all possible 4-tuples of consecutive differences. Clearly it would be inefficient to try every 4-tuple and simulate the result, so I found a faster way. For every 5 consecutive numbers in the list, calculate their 4-tuple of consecutive differences. If this 4-tuple was not seen previously, I inserted the 4-tuple and the sale value as a key-value pair into a `HashMap` of 4-tuples to numbers. (Otherwise, if the 4-tuple was seen previously, the negotiator monkey would have stopped earlier, so the sale value here cannot be used.) A 4-tuple that is not in the `HashMap` produces no earnings, as the four consecutive differences do not appear.

I used another `HashMap` of 4-tuples to numbers to store the sum of earnings due to using each 4-tuple for all secrets. The answer is the maximum number value in this `HashMap`.