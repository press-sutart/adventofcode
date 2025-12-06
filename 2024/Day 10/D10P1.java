import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;

public class D10P1 {
    public record Coords(int r, int c) {
        public Coords add(Coords rhs) {
            return new Coords(r + rhs.r, c + rhs.c);
        }
    }

    public static class MemoReachableNines {
        private ArrayList<ArrayList<Integer>> grid;
        private HashMap<Coords, HashSet<Coords>> memoResults;
        private int rows;
        private int cols;
        private final Coords[] DC = {
            new Coords(1, 0), new Coords(0, 1),
            new Coords(-1, 0), new Coords(0, -1)
        };

        public Integer readGrid(Coords co) {
            return grid.get(co.r()).get(co.c());
        }

        public Boolean inBounds(Coords co) {
            return 0 <= co.r() && co.r() < rows &&
                   0 <= co.c() && co.c() < cols;
        }

        public HashSet<Coords> getReachableNines(Coords co) {
            if (memoResults.containsKey(co)) {
                return memoResults.get(co);
            }

            memoResults.put(co, new HashSet<>());
            HashSet<Coords> memoRef = memoResults.get(co);

            if (!inBounds(co)) {
                return memoRef;
            }

            if (readGrid(co) == 9) {
                memoRef.add(co);
                return memoRef;
            }

            for (int i = 0; i < 4; i++) {
                Coords nextco = co.add(DC[i]);
                if (inBounds(nextco) && readGrid(co) + 1 == readGrid(nextco)) {
                    HashSet<Coords> subset = getReachableNines(nextco);
                    memoRef.addAll(subset);
                }
            }

            return memoRef;
        }

        public MemoReachableNines(ArrayList<ArrayList<Integer>> grid) {
            memoResults = new HashMap<>();
            this.grid = grid;
            rows = grid.size();
            cols = (rows == 0) ? 0 : grid.get(0).size();
        }
    }

    public static ArrayList<ArrayList<Integer>> input(Scanner scanner) {
        ArrayList<ArrayList<Integer>> inputGrid = new ArrayList<>();
        while (scanner.hasNextLine()) {
            String inputLine = scanner.nextLine();
            ArrayList<Integer> lineNumbers = new ArrayList<>();
            for (char inputChar : inputLine.toCharArray()) {
                lineNumbers.add(inputChar - '0');
            }
            inputGrid.add(lineNumbers);
        }
        return inputGrid;
    }

    public static ArrayList<ArrayList<Integer>> readFromFile(String filePath) {
        try {
            File inputFile = new File(filePath);
            Scanner scanner = new Scanner(inputFile);
            ArrayList<ArrayList<Integer>> inputGrid = input(scanner);
            scanner.close();
            return inputGrid;
        } catch (FileNotFoundException e) {
            System.out.println("Could not open file");
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
    
    public static void main(String[] args) {
        ArrayList<ArrayList<Integer>> inputGrid = readFromFile(
            "Day 10/input.txt"
        );

        int rows = inputGrid.size();
        int cols = (rows == 0) ? 0 : inputGrid.get(0).size();
        MemoReachableNines fun = new MemoReachableNines(inputGrid);
        int total = 0;

        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                if (inputGrid.get(r).get(c) == 0) {
                    Coords co = new Coords(r, c);
                    HashSet<Coords> nines = fun.getReachableNines(co);
                    total += nines.size();
                }
            }
        }

        System.out.println(total);
    }
}