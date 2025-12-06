import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class D15P1 {
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
        private final char BOX_CHAR = 'O';
        private final char EMPTY_CHAR = '.';
        private final HashMap<Character, Coords> DC = new HashMap<>(Map.of(
            '^', new Coords(-1, 0),
            '<', new Coords(0, -1),
            'v', new Coords(1, 0),
            '>', new Coords(0, 1)
        ));
        
        private final HashMap<Coords, Character> grid;
        private final String instructions;
        private Coords robotCoords;

        public void move(char inst) {
            Coords direction = DC.get(inst);
            Coords endCoords = robotCoords.add(direction);
            boolean existsBox = false;
            while (grid.get(endCoords) == BOX_CHAR) {
                existsBox = true;
                endCoords = endCoords.add(direction);
            }
            boolean endIsEmpty = (grid.get(endCoords) == EMPTY_CHAR);

            if (endIsEmpty && existsBox) {
                // robot pushes boxes into an empty space
                grid.put(robotCoords, EMPTY_CHAR);
                grid.put(robotCoords.add(direction), ROBOT_CHAR);
                grid.put(endCoords, BOX_CHAR);
                robotCoords = robotCoords.add(direction);
            } else if (endIsEmpty && !existsBox) {
                // robot moves into an empty space
                grid.put(robotCoords, EMPTY_CHAR);
                grid.put(endCoords, ROBOT_CHAR);
                robotCoords = endCoords;
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
                move(inst);
            }

            long ans = 0L;
            for (Coords key : grid.keySet()) {
                if (grid.get(key) == BOX_CHAR) {
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
                    grid.put(new Coords(r, c), line.charAt(c));
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
