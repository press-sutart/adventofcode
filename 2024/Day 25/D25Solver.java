import java.util.ArrayList;

public class D25Solver {
    private final int SCHEMATIC_ROWS = 7;
    private final int SCHEMATIC_COLS = 5;
    private final char FILL_CHAR = '#';
    
    private ArrayList<ArrayList<Integer>> locks = new ArrayList<>();
    private ArrayList<ArrayList<Integer>> keys = new ArrayList<>();

    private void processGrid(ArrayList<String> grid) {
        boolean isLock = grid.get(0).charAt(0) == FILL_CHAR;
        ArrayList<Integer> heights = new ArrayList<>();

        if (!isLock) {
            // invert the grid vertically
            for (int r = 0; r < SCHEMATIC_ROWS / 2; r++) {
                String temp = grid.get(r);
                grid.set(r, grid.get(SCHEMATIC_ROWS - 1 - r));
                grid.set(SCHEMATIC_ROWS - 1 - r, temp);
            }
        }

        for (int c = 0; c < SCHEMATIC_COLS; c++) {
            for (int r = SCHEMATIC_ROWS - 1; r >= 0; r--) {
                if (grid.get(r).charAt(c) == FILL_CHAR) {
                    heights.add(r);
                    break;
                }
            }
        }

        if (isLock) locks.add(heights);
        else keys.add(heights);
    }

    private boolean fitPair(ArrayList<Integer> lock, ArrayList<Integer> key) {
        for (int c = 0; c < SCHEMATIC_COLS; c++) {
            if (lock.get(c) + key.get(c) > SCHEMATIC_ROWS - 2) {
                return false;
            }
        }
        return true;
    }

    public int solve() {
        int cnt = 0;

        for (ArrayList<Integer> lock : locks) {
            for (ArrayList<Integer> key : keys) {
                if (fitPair(lock, key)) cnt++;
            }
        }

        return cnt;
    }

    public D25Solver(ArrayList<String> inputLines) {
        ArrayList<String> grid = new ArrayList<>();

        for (String line : inputLines) {
            if (line.equals("")) {
                processGrid(grid);
                grid.clear();
            } else {
                grid.add(line);
            }
        }

        processGrid(grid);
    }
}
