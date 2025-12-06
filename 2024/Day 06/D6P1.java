import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class D6P1 {
    public static char readGrid(ArrayList<String> grid, int r, int c) {
        return grid.get(r).charAt(c);
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
        final int[] DR = {-1, 0, 1, 0};
        final int[] DC = {0, 1, 0, -1};
        int curr = -1;
        int curc = -1;
        int curd = 0;
        Boolean[][] isVisited = new Boolean[rows][cols];

        // find '^' to initialize curr and curc
        // and conveniently initialize isVisited
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                if (readGrid(grid, r, c) == '^') {
                    curr = r;
                    curc = c;
                }
                isVisited[r][c] = false;
            }
        }

        // simulate guard's movement until leaving the map area
        // assuming that the guard eventually leaves the map area
        while (
            0 <= curr && curr < rows &&
            0 <= curc && curc < cols
        ) {
            isVisited[curr][curc] = true;
            int nextr = curr + DR[curd];
            int nextc = curc + DC[curd];

            if (
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

        int countVisited = 0;
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                if (isVisited[r][c]) countVisited++;
            }
        }

        System.out.println(countVisited);
    }
}
