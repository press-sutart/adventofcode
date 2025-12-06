# Day 3

Annoyingly, string matching is involved this time. I used regex in C++.

## Part 1

*Abridged Statement: A string with `mul` instructions and gibberish text is given. A `mul` instruction is of the form `mul(X,Y)` where `X` and `Y` are 1-3 digit numbers and returns `X` multiplied by `Y`. Find all `mul` instructions in the string and sum up their return values.*

It appears that all `mul` instructions in the input have nonnegative parameters.

I found the `mul` instructions with the regex `mul\\([0-9]{1,3},[0-9]{1,3}\\)`. Within each `mul` instruction, I used the regex `[0-9]{1,3}` to extract the parameters. The `regex_iterator` provided in C++'s `<regex>` library was very helpful for iterating over all regex matches.

## Part 2

*Abridged Statement: The input string also contains `do()` and `don't()` instructions which enable and disable following `mul` instructions respectively. Only the most recent `do()` or `don't()` instruction applies. `mul` instructions are initially enabled. Sum up the return values of all enabled `mul` instructions.*

Define the following regex patterns:

```
muls_pattern = "mul\\([0-9]{1,3},[0-9]{1,3}\\)";
dos_pattern = "do\\(\\)";
donts_pattern = "don't\\(\\)";
ops_pattern = muls_pattern + "|" + dos_pattern + "|" + donts_pattern;
```

Using `regex_iterator` with the regex `ops_pattern`, all `mul`, `do` and `don't` instructions can be processed in order, but the type of each instruction is not known yet. I find the type of an instruction by checking the string against `muls_pattern`, `dos_pattern` and `donts_pattern` -- clearly exactly one of the patterns will match.

I maintain a Boolean flag to check if `mul` instructions are currently enabled or disabled and update it when `do` and `don't` instructions are found.