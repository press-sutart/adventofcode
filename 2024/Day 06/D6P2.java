import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class D6P2 {
    public static char readGrid(ArrayList<String> grid, int r, int c) {
        return grid.get(r).charAt(c);
    }

    // try to put an obstacle at (obsr, obsc), simulate and see what happens
    public static Boolean test(
        ArrayList<String> grid, int obsr, int obsc,
        int startr, int startc
    ) {
        int rows = grid.size();
        int cols = grid.get(0).length();
        Boolean[][][] isVisited = new Boolean[rows][cols][4];
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                for (int d = 0; d < 4; d++) {
                    isVisited[r][c][d] = false;
                }
            }
        }

        final int[] DR = {-1, 0, 1, 0};
        final int[] DC = {0, 1, 0, -1};
        int curr = startr;
        int curc = startc;
        int curd = 0;

        // simulate guard's movement until map area is exited
        // or cycle is detected
        while (
            0 <= curr && curr < rows &&
            0 <= curc && curc < cols &&
            !isVisited[curr][curc][curd]
        ) {
            isVisited[curr][curc][curd] = true;
            int nextr = curr + DR[curd];
            int nextc = curc + DC[curd];

            if (nextr == obsr && nextc == obsc) {
                curd = (curd + 1) % 4;
            } else if (
                0 <= nextr && nextr < rows &&
                0 <= nextc && nextc < cols &&
                readGrid(grid, nextr, nextc) == '#'
            ) {
                curd = (curd + 1) % 4;
            } else {
                curr = nextr;
                curc = nextc;
            }
        }

        return (
            0 <= curr && curr < rows &&
            0 <= curc && curc < cols
        );
    }

    public static void main(String[] args) {
        ArrayList<String> grid = new ArrayList<>();

        try {
            File inputFile = new File("Day 06/input.txt");
            Scanner scanner = new Scanner(inputFile);
            while (scanner.hasNextLine()) {
                String inputLine = scanner.nextLine();
                grid.add(inputLine);
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("Could not open file");
            e.printStackTrace();
        }

        int rows = grid.size();
        assert(rows > 0);
        int cols = grid.get(0).length();
        int startr = -1;
        int startc = -1;
        int cnt = 0;

        // find '^' to initialize curr and curc
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                if (readGrid(grid, r, c) == '^') {
                    startr = r;
                    startc = c;
                }
            }
        }

        for (int obsr = 0; obsr < rows; obsr++) {
            for (int obsc = 0; obsc < cols; obsc++) {
                if (readGrid(grid, obsr, obsc) != '.') continue;
                if (test(grid, obsr, obsc, startr, startc)) cnt++;
            }
        }

        System.out.println(cnt);
    }
}
