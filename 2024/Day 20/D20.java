import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Queue;
import java.util.Scanner;

public class D20 {
    public record Coords(int r, int c) {
        public Coords add(Coords rhs) {
            return new Coords(r + rhs.r, c + rhs.c);
        }
        public Coords scale(int k) {
            return new Coords(k * r, k * c);
        }
        public int length() {
            return Math.abs(r) + Math.abs(c);
        }
    }

    public static class Solver {
        private final char WALL_CHAR = '#';
        private final char START_CHAR = 'S';
        private final Coords[] DC = {
            new Coords(1, 0), new Coords(0, 1),
            new Coords(-1, 0), new Coords(0, -1)
        };
        private final int CHEAT_THRESHOLD = 100;
        private final int CHEAT_LENGTH_P1 = 2;
        private final int CHEAT_LENGTH_P2 = 20;
        private final HashMap<Coords, Character> grid;

        // returns null if no start is found
        public Coords findStart() {
            Coords start = null;
            for (Coords co : grid.keySet()) {
                if (grid.get(co) == START_CHAR) {
                    start = co;
                    break;
                }
            }
            return start;
        }

        public HashSet<Coords> getCheatMoves(int cheat_length) {
            HashSet<Coords> cheatMoves = new HashSet<>();
            for (int r = - cheat_length; r <= cheat_length; r++) {
                for (int c = - cheat_length; c <= cheat_length; c++) {
                    Coords testCo = new Coords(r, c);
                    if (testCo.length() <= cheat_length) {
                        cheatMoves.add(testCo);
                    }
                }
            }
            return cheatMoves;
        }

        public boolean isAccessible(Coords co) {
            return grid.containsKey(co) &&
                   grid.get(co) != WALL_CHAR;
        }

        public HashMap<Coords, Integer> bfs() {
            Coords start = findStart();
            HashMap<Coords, Integer> dists = new HashMap<>();
            HashSet<Coords> processed = new HashSet<>();
            Queue<Coords> bfsQueue = new ArrayDeque<>();
            dists.put(start, 0);
            bfsQueue.add(start);

            while (!bfsQueue.isEmpty()) {
                Coords curCoords = bfsQueue.remove();
                if (processed.contains(curCoords)) continue;
                processed.add(curCoords);

                for (int i = 0; i < 4; i++) {
                    Coords nextCoords = curCoords.add(DC[i]);
                    if (
                        isAccessible(nextCoords) &&
                        !dists.containsKey(nextCoords)
                    ) {
                        dists.put(nextCoords, dists.get(curCoords) + 1);
                        bfsQueue.add(nextCoords);
                    }
                }
            }

            return dists;
        }

        public int solve(int cheat_length) {
            HashMap<Coords, Integer> dists = bfs();
            HashSet<Coords> cheatMoves = getCheatMoves(cheat_length);
            int pathDist = dists.size() - 1;
            int cntGoodCheats = 0;

            for (Coords cheatStart : dists.keySet()) {
                for (Coords cheatMove : cheatMoves) {
                    Coords cheatEnd = cheatStart.add(cheatMove);
                    if (!dists.containsKey(cheatEnd)) continue;

                    int cheatDist = dists.get(cheatStart) +
                                    cheatMove.length() +
                                    pathDist - dists.get(cheatEnd);
                    
                    if (cheatDist <= pathDist - CHEAT_THRESHOLD) {
                        cntGoodCheats++;
                    }
                }
            }

            return cntGoodCheats;
        }

        public int solveP1() {
            return solve(CHEAT_LENGTH_P1);
        }

        public int solveP2() {
            return solve(CHEAT_LENGTH_P2);
        }

        public Solver(HashMap<Coords, Character> grid) {
            this.grid = grid;
        }
    }

    public static HashMap<Coords, Character> input(Scanner scanner) {
        HashMap<Coords, Character> grid = new HashMap<>();
        int r = 0;

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            for (int c = 0; c < line.length(); c++) {
                grid.put(new Coords(r, c), line.charAt(c));
            }
            r++;
        }

        return grid;
    }

    public static HashMap<Coords, Character> readFromFile(String filePath) {
        try {
            File inputFile = new File(filePath);
            Scanner scanner = new Scanner(inputFile);
            HashMap<Coords, Character> grid = input(scanner);
            scanner.close();
            return grid;
        } catch (FileNotFoundException e) {
            System.out.println("Could not open file");
            e.printStackTrace();
            return new HashMap<>();
        }
    }

    public static void main(String[] args) {
        HashMap<Coords, Character> grid =
            readFromFile("Day 20/input.txt");
        Solver solver = new Solver(grid);
        System.out.println(solver.solveP1());
        System.out.println(solver.solveP2());
    }
}