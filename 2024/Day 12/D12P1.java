import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Queue;
import java.util.Scanner;

public class D12P1 {
    public record Coords(int r, int c) {
        public Coords add(Coords rhs) {
            return new Coords(r + rhs.r, c + rhs.c);
        }
    }

    public static class Fencing {
        private char[][] garden;
        private Boolean[][] isVisited;
        private int rows;
        private int cols;
        private final Coords[] DC = {
            new Coords(1, 0), new Coords(0, 1),
            new Coords(-1, 0), new Coords(0, -1)
        };

        public Boolean inBounds(Coords co) {
            return 0 <= co.r() && co.r() < rows &&
                   0 <= co.c() && co.c() < cols;
        }

        public long getRegionPrice(Coords source) {
            char regionPlant = garden[source.r()][source.c()];
            long area = 0;
            long perimeter = 0;
            Queue<Coords> bfsQueue = new ArrayDeque<Coords>();
            bfsQueue.add(source);

            while (!bfsQueue.isEmpty()) {
                Coords curCoords = bfsQueue.remove();
                if (isVisited[curCoords.r()][curCoords.c()]) continue;
                isVisited[curCoords.r()][curCoords.c()] = true;
                area++;
                for (int i = 0; i < 4; i++) {
                    Coords nextCoords = curCoords.add(DC[i]);
                    if (
                        !inBounds(nextCoords) ||
                        regionPlant != garden[nextCoords.r()][nextCoords.c()]
                    ) {
                        perimeter++;
                    } else if (!isVisited[nextCoords.r()][nextCoords.c()]) {
                        bfsQueue.add(nextCoords);
                    }
                }
            }

            return area * perimeter;
        }

        public long getTotalPrice() {
            long total = 0L;
            for (int r = 0; r < rows; r++) {
                for (int c = 0; c < cols; c++) {
                    if (!isVisited[r][c]) {
                        total += getRegionPrice(new Coords(r, c));
                    }
                }
            }
            return total;
        }

        public Fencing(char[][] grid) {
            rows = grid.length;
            cols = (rows == 0) ? 0 : grid[0].length;
            garden = grid;
            isVisited = new Boolean[rows][cols];
            for (int r = 0; r < rows; r++) {
                Arrays.fill(isVisited[r], false);
            }
        }
    }

    public static char[][] input(Scanner scanner) {
        ArrayList<String> inputLines = new ArrayList<>();
        while (scanner.hasNextLine()) {
            String inputLine = scanner.nextLine();
            inputLines.add(inputLine);
        }
        int rows = inputLines.size();
        int cols = (rows == 0) ? 0 : inputLines.get(0).length();
        char[][] grid = new char[rows][cols];
        for (int r = 0; r < rows; r++) {
            grid[r] = inputLines.get(r).toCharArray();
        }
        return grid;
    }

    public static char[][] readFromFile(String filePath) {
        try {
            File inputFile = new File(filePath);
            Scanner scanner = new Scanner(inputFile);
            char[][] grid = input(scanner);
            scanner.close();
            return grid;
        } catch (FileNotFoundException e) {
            System.out.println("Could not open file");
            e.printStackTrace();
            return new char[0][0];
        }
    }

    public static void main(String[] args) {
        char[][] garden = readFromFile("Day 12/input.txt");
        Fencing fencing = new Fencing(garden);
        long answer = fencing.getTotalPrice();
        System.out.println(answer);
    }
}
