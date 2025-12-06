import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class D15P2 {
    public record Coords(long r, long c) {
        public Coords add(Coords rhs) {
            return new Coords(r + rhs.r, c + rhs.c);
        }
        public long value() {
            return 100L * r + c;
        }
    }

    public static class Solver {
        private final char ROBOT_CHAR = '@';
        private final char BOX_LEFT_CHAR = '[';
        private final char BOX_RIGHT_CHAR = ']';
        private final char WALL_CHAR = '#';
        private final char EMPTY_CHAR = '.';

        private final Coords UP_DIRECTION = new Coords(-1, 0);
        private final Coords LEFT_DIRECTION = new Coords(0, -1);
        private final Coords DOWN_DIRECTION = new Coords(1, 0);
        private final Coords RIGHT_DIRECTION = new Coords(0, 1);

        private final HashMap<Character, Coords> DC = new HashMap<>(Map.of(
            '^', UP_DIRECTION,
            '<', LEFT_DIRECTION,
            'v', DOWN_DIRECTION,
            '>', RIGHT_DIRECTION
        ));
        
        private final HashMap<Coords, Character> grid;
        private final String instructions;
        private Coords robotCoords;

        public boolean isMovable(Coords position, Coords direction) {
            char hereChar = grid.get(position);

            if (hereChar == EMPTY_CHAR) return true;
            if (hereChar == WALL_CHAR) return false;

            if (
                direction.equals(LEFT_DIRECTION) ||
                direction.equals(RIGHT_DIRECTION) ||
                hereChar == ROBOT_CHAR
            ) {
                return isMovable(position.add(direction), direction);
            } else { // hereChar == BOX_LEFT_CHAR || hereChar == BOX_RIGHT_CHAR
                Coords leftPosition = (hereChar == BOX_LEFT_CHAR)
                                      ? position
                                      : position.add(LEFT_DIRECTION);
                Coords rightPosition = (hereChar == BOX_RIGHT_CHAR)
                                       ? position
                                       : position.add(RIGHT_DIRECTION);
                return isMovable(leftPosition.add(direction), direction) &&
                       isMovable(rightPosition.add(direction), direction);
            }
        }

        public void moveChars(Coords position, Coords direction) {
            char hereChar = grid.get(position);

            if (hereChar == EMPTY_CHAR || hereChar == WALL_CHAR) {
                return;
            } else if (
                direction.equals(LEFT_DIRECTION) ||
                direction.equals(RIGHT_DIRECTION) ||
                hereChar == ROBOT_CHAR
            ) {
                moveChars(position.add(direction), direction);
                grid.put(position.add(direction), hereChar);
                grid.put(position, EMPTY_CHAR);
            } else { // hereChar == BOX_LEFT_CHAR || hereChar == BOX_RIGHT_CHAR
                Coords leftPosition = (hereChar == BOX_LEFT_CHAR)
                                      ? position
                                      : position.add(LEFT_DIRECTION);
                Coords rightPosition = (hereChar == BOX_RIGHT_CHAR)
                                       ? position
                                       : position.add(RIGHT_DIRECTION);
                moveChars(leftPosition.add(direction), direction);
                moveChars(rightPosition.add(direction), direction);
                grid.put(leftPosition.add(direction), BOX_LEFT_CHAR);
                grid.put(rightPosition.add(direction), BOX_RIGHT_CHAR);
                grid.put(leftPosition, EMPTY_CHAR);
                grid.put(rightPosition, EMPTY_CHAR);
            }
        }

        public void move(Coords direction) {
            if (isMovable(robotCoords, direction)) {
                moveChars(robotCoords, direction);
                robotCoords = robotCoords.add(direction);
            }
        }

        public long solve() {
            boolean foundRobot = false;
            for (Coords key : grid.keySet()) {
                if (grid.get(key) == ROBOT_CHAR) {
                    foundRobot = true;
                    robotCoords = key;
                }
            }
            if (!foundRobot) return 0L;

            for (char inst : instructions.toCharArray()) {
                move(DC.get(inst));
            }

            long ans = 0L;
            for (Coords key : grid.keySet()) {
                if (grid.get(key) == BOX_LEFT_CHAR) {
                    ans += key.value();
                }
            }

            return ans;
        }

        public Solver(HashMap<Coords, Character> grid, String instructions) {
            this.grid = grid;
            this.instructions = instructions;
        }
    }

    public static Solver input(Scanner scanner) {
        boolean foundEmptyLine = false;
        HashMap<Coords, Character> grid = new HashMap<>();
        int r = 0;
        String instructions = "";

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            if (line == "") {
                foundEmptyLine = true;
            } else if (foundEmptyLine) {
                instructions = instructions + line;
            } else {
                for (int c = 0; c < line.length(); c++) {
                    if (line.charAt(c) == '#') {
                        grid.put(new Coords(r, 2 * c), '#');
                        grid.put(new Coords(r, 2 * c + 1), '#');
                    } else if (line.charAt(c) == 'O') {
                        grid.put(new Coords(r, 2 * c), '[');
                        grid.put(new Coords(r, 2 * c + 1), ']');
                    } else if (line.charAt(c) == '.') {
                        grid.put(new Coords(r, 2 * c), '.');
                        grid.put(new Coords(r, 2 * c + 1), '.');
                    } else {
                        grid.put(new Coords(r, 2 * c), '@');
                        grid.put(new Coords(r, 2 * c + 1), '.');
                    }
                }
                r++;
            }
        }

        return new Solver(grid, instructions);
    }

    public static Solver readFromFile(String filePath) {
        try {
            File inputFile = new File(filePath);
            Scanner scanner = new Scanner(inputFile);
            Solver solver = input(scanner);
            scanner.close();
            return solver;
        } catch (FileNotFoundException e) {
            System.out.println("Could not open file");
            e.printStackTrace();
            return new Solver(new HashMap<>(), "");
        }
    }

    public static void main(String[] args) {
        Solver solver = readFromFile("Day 15/input.txt");
        System.out.println(solver.solve());
    }
}
