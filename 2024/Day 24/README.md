# Day 24

Part 2 was decently tough.

## Part 1

I wrote a `D24Gate` record that comprises of three strings. These are the logic gate type and the two input wires that form a logic gate.

The `D24Wires` class holds the entire system of wires and gates. Its constructor takes in the input text as its argument and initialises two data structures. The first is `HashMap<String, Integer> knownWires` which map wire names to the value carried in the wire if it is known. Initially it contains only the given values in the first part of the input. The second data structure is `HashMap<String, D24Gate> gateWires` which map wire names to the gate that the wire gets its value from.

The value of a wire is retrieved by passing the wire name to the `getValue` method of the `D24Wires` class. If the key set of `knownWires` contains the wire name, then the value stored in `knownWires` is returned. Otherwise, I look at the gate pointing into the wire, which is retrieved from `gateWires`. The values of the gate's input wires are obtained with recursive calls to `getValue`. The computed output is memoised in `knownWires` before it is returned.

A wire with some name exists if and only if the name is in the key set of `knownWires` or `gateWires` or both. This is checked in the `exists` method of `D24Wires`.

To get the bits forming the answer, I queried the values of wires `z00`, `z01`, `z02` and so on until finding a wire that does not exist.

## Part 2

### Adders

Given that the system is supposed to add two numbers, I read up on [adders](https://en.wikipedia.org/wiki/Adder_(electronics)). A simple way to add up two $n$-bit terms using `AND`, `OR` and `XOR` logic gates is described below. The $0$-th (least significant) bits `x[0]` and `y[0]` are passed as inputs into a *half adder*, which outputs the $0$-th bit of the answer `z[0]` and a carry bit `c[1]`. The half adder is commonly implemented as follows:

```
x[0] XOR y[0] -> z[0]
x[0] AND y[0] -> c[1]
```

For each remaining bit position $i$ in the range $`1 \leq i < n`$, the $i$-th bits of the terms `x[i]`, `y[i]` and the carry bit `c[i]` are passed into a *full adder*, which outputs the $i$-th bit of the answer `z[i]` and a carry bit `c[i+1]`. Let `t[i,0]`, `t[i,1]` and `t[i,2]` denote intermediate results. A common way to implement the full adder is as follows (note that two pairs of inputs are the same):

```
x[i]   XOR y[i]   -> t[i,0]
x[i]   AND y[i]   -> t[i,1]
c[i]   XOR t[i,0] -> z[i]
c[i]   AND t[i,0] -> t[i,2]
t[i,1] OR  t[i,2] -> c[i+1]
```

All `x[i]`, `y[i]` and `z[i]` bits follow the wire naming system described in the task. The final carry bit `c[n]` is not added to any other bit and therefore provides the value of `z[n]`. All `t[i,j]` bits and all `c[i]` bits except `c[n]` are random three-character strings.

In my input, the system attempts to add two $45$-bit numbers using $222$ logic gates. Besides some swapped output wires, the system is actually implementing the above method, as suggested by the matching number of logic gates used.

### Approach

There are too many logic gates to check by hand. My approach was to find the smallest bit that was computed wrongly and take a closer look at the computation leading up to its result. I figured out which outputs were swapped, fixed them and repeated the process.

I wrote a solver method `void assistP2(int bits)` that helps to check if the bits in positions $0$ to $bits - 1$ are computed correctly. The method has a variable integer inside called $aimSum$, which I usually initialise to $`2^{bits} - 1`$ but sometimes manually change. I randomly generated an integer $r$ which is strictly less than $aimSum$ (in hindsight, randomly generating both $aimSum$ and $r$ below $`2^{bits}`$ would probably be better), then checked if passing $r$ and $aimSum-r$ to the system produced $aimSum$. The choice of limiting the sum to $`2^{bits} - 1`$ is to ignore the effects of larger bits. Even if outputs are swapped, the larger bits (at positions $bits$ and above) are guaranteed to be $0$ as taking bitwise `AND`, `OR` or `XOR` on two zeroes always produces a zero.

Additionally, in the `getValue` method of `D24Wires`, I output the details of each logic gate after it gets evaluated. This shows the order in which logic gates get evaluated.

Starting from $0$, I increase $bits$ until I find an error. 

### Example

In my input, the first error occurs when $bits = 8$, suggesting that `z[7]` is computed incorrectly. Here is the order of evaluation around indices $6$ and $7$.

```
...
evaluated x06 XOR y06 -> btp
evaluated wmv AND whq -> kjf
evaluated x05 AND y05 -> wtq
evaluated kjf OR wtq -> hsm
evaluated btp XOR hsm -> z06
evaluated y07 AND x07 -> z07
evaluated x07 XOR y07 -> mvw
evaluated btp AND hsm -> jtg
evaluated x06 AND y06 -> hgj
evaluated jtg OR hgj -> pmc
evaluated mvw AND pmc -> wnn
evaluated pmc XOR mvw -> gmt
evaluated wnn OR gmt -> rkw
evaluated y08 XOR x08 -> whp
...
```

It is known that the computations for index $6$ are correct. I verified that they match the full adder format. According to the format, the carry bit `c[7]` is `pmc`, though at this stage it is not guaranteed to be correct.

```
evaluated x06 XOR y06 -> btp
evaluated x06 AND y06 -> hgj
evaluated hsm XOR btp -> z06 (inputs swapped)
evaluated hsm AND btp -> jtg (inputs swapped)
evaluated hgj OR jtg -> pmc  (inputs swapped)
```

The evaluated gates for $i = 7$ are as follows. The third and fourth gate were determined as they contain `pmc` as an input.

```
evaluated x07 XOR y07 -> mvw
evaluated x07 AND y07 -> z07 (inputs swapped)
evaluated pmc XOR mvw -> gmt
evaluated pmc AND mvw -> wnn (inputs swapped)
evaluated gmt OR wnn -> rkw  (inputs swapped)
```

Referencing the full adder format, `z07` and `gmt` are swapped. 

For my input, the swapped outputs were quite close to each other in the order of evaluation.