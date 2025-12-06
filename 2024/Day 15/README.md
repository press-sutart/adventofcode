# Day 15

Java!!!!!!!!

The `Coords` record makes a reappearance with the usual `Coords add(Coords rhs)` method. For this task I wrote an additional method `long value()` which just returns `100 * r + c`.

I mostly simulated the movement of objects in the grid, with a very minor optimisation in Part 1. I initialised a `Solver` class with the grid and instructions from the input. Instead of storing the grid in a 2D `char` array, I used a `HashMap<Coords, Character>`.

## Part 1

The `Solver` class has a `void solve()` method that finds the robot's location in the grid and stores it in `robotCoords`, processes each instruction in order using a separate `void move(char inst)` method and calculates the value of all box coordinates.

The `void move(char inst)` method implemented by finding the first non-box location in the direction of the robot's movement. If the item is an empty space, movement is allowed and I consider two cases. If there are no boxes to push, two locations of the grid are updated: the robot's location becomes empty and the non-box location contains the robot. If there are boxes to push, then the robot's location becomes empty, the location one step in the movement direction contains the robot and the non-box location contains a box.

## Part 2

When receiving the grid as input, I immediately transform it into the widened version as shown in the task. My solver works on this widened grid.

The `void solve()` method is almost identical. I changed the `move()` method to accept a `Coords direction` instead of a character, but this is merely moving the translation of instruction character to a direction out of `move()` into `solve()`. I also look for the box left character `[` instead to sum up values.

The `void move(Coords direction)` checks if moving is possible using another method `boolean isMovable(Coords position, Coords direction)`. If possible, I move the robot and any boxes using `void moveChars(Coords position, Coords direction)` and update `robotCoords`. Both `isMovable` and `moveChars` are called with `position` as `robotCoords` and `direction` based on the instructed direction.

`isMovable` is defined recursively as follows, where the first applicable branch is chosen:

* If `position` is an empty space, then `isMovable` returns `true`
* If `position` contains a wall, then `isMovable` returns `false`
* If `position` contains the robot or `direction` is left or right, then check `isMovable` towards `direction` (this is similar to how `isMovable` would be implemented if it were used in Part 1)
* Otherwise, `position` contains half of a box and `direction` is up or down. Check `isMovable` from both box halves towards `direction` and take conditional `AND`

`moveChars` follows the same recursive structure. An object is only moved after its recursive call(s) are finished, so that an object moves one step in the given direction into an empty space.