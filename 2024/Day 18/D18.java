import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Queue;
import java.util.Scanner;

public class D18 {
    public record Coords(int r, int c) {
        public Coords add(Coords rhs) {
            return new Coords(r + rhs.r, c + rhs.c);
        }
    }

    public static class Solver {
        private final int ROWS = 71;
        private final int COLS = 71;
        private final Coords START = new Coords(0, 0);
        private final Coords END = new Coords(ROWS - 1, COLS - 1);
        private final int UNREACHED = -1;
        private final Coords[] DC = {
            new Coords(1, 0), new Coords(0, 1),
            new Coords(-1, 0), new Coords(0, -1)
        };

        private final HashMap<Coords, Integer> obstacles;

        public boolean isAccessible(Coords co, int cnt_obstacles) {
            return 0 <= co.r() && co.r() < ROWS &&
                   0 <= co.c() && co.c() < COLS &&
                   (!obstacles.containsKey(co) ||
                    obstacles.get(co) >= cnt_obstacles);
        }

        public int test(int cnt_obstacles) {
            HashMap<Coords, Integer> dist = new HashMap<>();
            HashSet<Coords> processed = new HashSet<>();
            Queue<Coords> bfsQueue = new ArrayDeque<>();
            dist.put(START, 0);
            bfsQueue.add(START);

            while (!bfsQueue.isEmpty()) {
                Coords curCoords = bfsQueue.remove();
                if (processed.contains(curCoords)) continue;
                processed.add(curCoords);

                for (int i = 0; i < 4; i++) {
                    Coords nextCoords = curCoords.add(DC[i]);
                    if (
                        isAccessible(nextCoords, cnt_obstacles) &&
                        !dist.containsKey(nextCoords)
                    ) {
                        dist.put(nextCoords, dist.get(curCoords) + 1);
                        bfsQueue.add(nextCoords);
                    }
                }
            }

            return dist.containsKey(END) ? dist.get(END) : UNREACHED;
        }

        public int solveP1() {
            return test(1024);
        }

        public String solveP2() {
            int testMin = 0;
            int testMax = obstacles.size();
            int firstBlocked = obstacles.size();

            while (testMin <= testMax) {
                int testObs = (testMin + testMax) / 2;
                int result = test(testObs);
                if (result == UNREACHED) {
                    firstBlocked = testObs;
                    testMax = testObs - 1;
                } else {
                    testMin = testObs + 1;
                }
            }

            for (Coords co : obstacles.keySet()) {
                if (obstacles.get(co).equals(firstBlocked - 1)) {
                    return String.valueOf(co.r()) +
                           "," +
                           String.valueOf(co.c());
                }
            }

            return "Not found";
        }

        public Solver(HashMap<Coords, Integer> obs) {
            obstacles = obs;
        }
    }

    public static ArrayList<Coords> input(Scanner scanner) {
        ArrayList<Coords> obstacles = new ArrayList<>();
        while (scanner.hasNextLine()) {
            String inputLine = scanner.nextLine();
            String[] inputNumStrs = inputLine.split(",");
            Coords obsCoords = new Coords(
                Integer.parseInt(inputNumStrs[0]),
                Integer.parseInt(inputNumStrs[1])
            );
            obstacles.add(obsCoords);
        }
        return obstacles;
    }

    public static ArrayList<Coords> readFromFile(String filePath) {
        try {
            File file = new File(filePath);
            Scanner scanner = new Scanner(file);
            ArrayList<Coords> obstacles = input(scanner);
            scanner.close();
            return obstacles;
        } catch (FileNotFoundException e) {
            System.out.println("Could not open file");
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public static void main(String[] args) {
        ArrayList<Coords> obstacles = readFromFile("Day 18/input.txt");
        HashMap<Coords, Integer> obstacleMap = new HashMap<>();
        for (int i = 0; i < obstacles.size(); i++) {
            Coords obsCoords = obstacles.get(i);
            if (!obstacleMap.containsKey(obsCoords)) {
                obstacleMap.put(obsCoords, i);
            }
        }

        Solver solver = new Solver(obstacleMap);
        System.out.println(solver.solveP1());
        System.out.println(solver.solveP2());
    }
}