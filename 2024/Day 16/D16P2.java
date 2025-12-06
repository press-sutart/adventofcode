import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.PriorityQueue;
import java.util.Scanner;

public class D16P2 {
    public record Coords(int r, int c) {
        public Coords add(Coords rhs) {
            return new Coords(r + rhs.r, c + rhs.c);
        }
        public Coords rotateCW() {
            return new Coords(c, -r);
        }
        public Coords rotateCCW() {
            return new Coords(-c, r);
        }
    }

    public static class Solver {
        public record Position(Coords location, Coords direction) {
            public Position moveForward() {
                return new Position(location.add(direction), direction);
            }
            public Position rotateCW() {
                return new Position(location, direction.rotateCW());
            }
            public Position rotateCCW() {
                return new Position(location, direction.rotateCCW());
            }
        }

        public record DijkstraState(int cost, Position position)
        implements Comparable<DijkstraState> {
            public int compareTo(DijkstraState rhs) {
                return Integer.compare(cost, rhs.cost);
            }
        }

        private final int MOVE_COST = 1;
        private final int ROTATE_COST = 1000;
        private final char WALL_CHAR = '#';
        private final char START_CHAR = 'S';
        private final char END_CHAR = 'E';
        private final Coords EAST_DIRECTION = new Coords(0, 1);

        private Coords startCoords;
        private Coords endCoords;
        private HashMap<Coords, Character> grid;

        public void checkAndUpdateMin(
            HashMap<Position, Integer> minCost, PriorityQueue<DijkstraState> pq,
            Position position, int cost
        ) {
            if (grid.get(position.location()) == WALL_CHAR) return;
            if (minCost.containsKey(position) && cost >= minCost.get(position))
                return;
            minCost.put(position, cost);
            pq.add(new DijkstraState(cost, position));
        }

        public HashMap<Position, Integer> getMinCost(Position startPos) {
            HashMap<Position, Integer> minCost = new HashMap<>();
            PriorityQueue<DijkstraState> pq = new PriorityQueue<>();

            minCost.put(startPos, 0);
            pq.add(new DijkstraState(0, startPos));

            while (!pq.isEmpty()) {
                DijkstraState curDijkstraState = pq.poll();
                int curCost = curDijkstraState.cost();
                Position curPos = curDijkstraState.position();
                if (curCost > minCost.get(curPos)) continue;

                checkAndUpdateMin(
                    minCost, pq,
                    curPos.moveForward(), curCost + MOVE_COST
                );
                checkAndUpdateMin(
                    minCost, pq,
                    curPos.rotateCW(), curCost + ROTATE_COST
                );
                checkAndUpdateMin(
                    minCost, pq,
                    curPos.rotateCCW(), curCost + ROTATE_COST
                );
            }

            return minCost;
        }

        public int solve() {
            Position startPos = new Position(startCoords, EAST_DIRECTION);
            HashMap<Position, Integer> minFromStart = getMinCost(startPos);
            ArrayList<HashMap<Position, Integer>> minFromEnds =
                new ArrayList<>();
            Position endPos = new Position(endCoords, EAST_DIRECTION);
            minFromEnds.add(getMinCost(endPos));
            int minCost = minFromStart.get(endPos);

            for (int i = 0; i < 3; i++) {
                endPos = endPos.rotateCW();
                minFromEnds.add(getMinCost(endPos));
                minCost = Math.min(minCost, minFromStart.get(endPos));
            }

            int cntTiles = 0;
            for (Coords midCoords : grid.keySet()) {
                Position midPos = new Position(midCoords, EAST_DIRECTION);
                boolean onBestPath = false;

                for (int midDirIndex = 0; midDirIndex < 4; midDirIndex++) {
                    for (HashMap<Position, Integer> minFromEnd : minFromEnds) {
                        if (minFromStart.get(midPos) == null) continue;
                        int cost = minFromStart.get(midPos) +
                                   minFromEnd.get(midPos.rotateCW().rotateCW());
                        if (cost == minCost) onBestPath = true;
                    }
                    midPos = midPos.rotateCW();
                }

                if (onBestPath) cntTiles++;
            }

            return cntTiles;
        }

        public Solver(HashMap<Coords, Character> grid) {
            this.grid = grid;
            for (Coords co : grid.keySet()) {
                if (grid.get(co) == START_CHAR) startCoords = co;
                if (grid.get(co) == END_CHAR) endCoords = co;
            }
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
            readFromFile("Day 16/input.txt");
        Solver solver = new Solver(grid);
        System.out.println(solver.solve());
    }
}
