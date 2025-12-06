# Day 9

Taking a break from learning Java and falling back onto C++. Medium difficulty problem(?) though I probably made it harder for myself in Part 2 by coming up with a different solution than brute force.

Let $`n`$ and $`s`$ be the length of the input and the maximum size of files or free spaces. Under the constraints of the task, $`s = 9`$.

## Part 1

After reading the input, I made a representation of the disk blocks using a `std::vector<long long>` called `disk_files`, where the $`i`$-th element is the index of the file occupying the $`i`$-th block, or $`-1`$ if the $`i`$-th block is empty. This is similar to the example in the task statement.

To simulate the movement of the files, I used a "two pointers" approach. The "pointers" (actually `int`s storing indices of `disk_files`) `left_i` and `right_i` initially refer to the leftmost and rightmost block in the disk respectively and will move towards each other.

If the block at `left_i` is empty, I decrement `right_i` until I discover a nonempty block. Then I move the file block at `right_i` into the empty block at `left_i` (I don't *explicitly* do this but it's clearer to explain like so). Otherwise, the block at `left_i` is nonempty and I don't modify the blocks. In both cases I increment `left_i`. This process repeats until the pointers move past each other. Finally, I calculate the checksum in the same way as described in the task.

My solution uses $`O(ns)`$ time and space.

A solution using $`O(n)`$ time and space is discussed in the Bonus section after Part 2.

## Part 2

Let `file_size` and `file_location` be `std::vector<long long>`s that take file IDs as indices. Let `free_locations` be an array, so that `free_locations[i]` is a minimum priority queue of `long long`s storing the left ends of maximal free spaces with a width of $`i`$ blocks. The three data structures can be filled after reading the input. Priority queues support insertion in $`O(\log n)`$ time, so this process takes $`O(n \log n)`$ time.

By definition, `free_locations[i].top()` is the location of the **leftmost** free space with a width of $`i`$ blocks. Hence, the final location of some given file is the smallest value among its original location and `free_locations[i].top()` for all $`i`$ greater than or equal to the file's size. I used a `for` loop to check each $`i`$ and find the final location. If the final location is different from the original location, the file is moved and I update `file_location` and `free_locations`. The `top` member function of a priority queue runs in constant time, but edits run in $`O(\log n)`$ time, so processing one file takes $`O(s + \log n)`$ time.

Let $`i,s,l`$ be a file's ID, size and location respectively. Its contribution to the checksum is

$$i \times (l + (l+1) + \dots + (l+s-1)) = i \times (tri(l+s-1) - tri(l-1)),$$

where

$$tri(k) = 1 + 2 + \dots + k = \frac{k(k+1)}{2}$$

can be computed in constant time.

My solution uses $`O(ns + n \log n)`$ time and $`O(n)`$ space.

## Bonus

My solution to Part 1 is inefficient as it moves individual pieces of a file separately, even if they end up next to each other. By storing both size and location of files and empty spaces in `std::vector`s similar to Part 2, contiguous file segments can be moved to contiguous empty spaces. If the faster checksum calculation method in Part 2 is also used, the solution would only use $`O(n)`$ time and space.

The time complexity of finding the minimum of `free_locations[i].top()` for some range of $`i`$ in Part 2 could be improved from $`O(s)`$ to $`O(\log s)`$ using a segment tree. Such a solution would use $`O(n \log s + n \log n)`$ time and $`O(n)`$ space.