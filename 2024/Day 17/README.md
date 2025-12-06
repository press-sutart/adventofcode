# Day 17

I used Java in Part 1 and Python (featuring pen and paper) in Part 2.

Some functions can alternatively be expressed using bitwise operations. Dividing by $2^x$ is equivalent to a bitwise right shift by $x$ positions, while modulo $8$ is equivalent to taking bitwise AND with $7$ (i.e., smallest three bits). Part 1 is just doing the tedious work of writing an interpreter.

In Part 2, no clear way to solve the general case came to mind, so I scrutinised my input program. (**The observations here may be specific to my input!**)

* The instruction with opcode 3 is used only once at the end of the program, with operand 0. This sends the instruction pointer to the start if register A does not contain 0.
* The only instruction that modifies register A has opcode 0. This also only appears once, right before the instruction with opcode 3. Together, these two instructions resemble enclosing the rest of the program in a do-while loop:

```
do {
    // all other instructions
    A = A >> 3;
} while (A != 0);
```

* The output instruction (opcode 5) only considers the smallest three bits of register B. The only such instruction appears just before `A = A >> 3` in the above code.
* Register B is only used in bitwise XOR and AND operations, so only the smallest three bits of register B will affect operations. It is safe to ignore all other bits.
* Similarly, Register C is only used in a bitwise XOR with B, so only the smallest three bits of Register C matter as well.

Let $`a_i`$ represent the $i$-th smallest bit of register A. Let $`\tilde{b}`$ represent the result of flipping a bit $b$. I considered 8 different cases, corresponding to all of the different values the smallest three bits of register A could take at the beginning of the loop. The output value depends on the smallest ten bits of register A. For example,

* If $`a_2 a_1 a_0 = 000`$, the output value is $`a_4 a_3 1`$ in binary
* If $`a_2 a_1 a_0 = 001`$, the output value is $`a_5 a_4 a_3`$ in binary
* If $`a_2 a_1 a_0 = 101`$, the output value is $`\tilde{a}_9 a_8 a_7`$ in binary

The full table for my input is shown in the Python program. Consider the simplest case where the output has length $1$. Then $`a_3, a_4, \dots`$ must all be $0$, otherwise the loop runs more than once. If the required output value matches an output in the table, then the corresponding three smallest bits can be used. Note that more than one match is possible in this task. (And, clearly, there could be no match for a general output.)

For larger required outputs, note that numbers that appear later in the input are produced by larger bits. In particular, the last number is produced by the largest three bits, which can be found using the solution for an output of length 1. These three bits become $a_5, a_4, a_3$ when processing the next number in the output.
