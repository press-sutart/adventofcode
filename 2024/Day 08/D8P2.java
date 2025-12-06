import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;

public class D8P2 {
    public record Coords(int r, int c) {
        public Coords add(Coords rhs) {
            return new Coords(r + rhs.r, c + rhs.c);
        }
        public Coords subtract(Coords rhs) {
            return new Coords(r - rhs.r, c - rhs.c);
        }
    }

    public static Character[][] input(Scanner scanner) {
        ArrayList<String> tempInput = new ArrayList<>();
        while (scanner.hasNextLine()) {
            tempInput.add(scanner.nextLine());
        }

        int rows = tempInput.size();
        assert(rows > 0);
        int cols = tempInput.get(0).length();
        assert(cols > 0);

        Character[][] finalInput = new Character[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                finalInput[i][j] = Character.valueOf(
                    tempInput.get(i).charAt(j)
                );
            }
        }
        return finalInput;
    }

    public static void main(String[] args) {
        Character[][] grid;

        try {
            File inputFile = new File("Day 08/input.txt");
            Scanner scanner = new Scanner(inputFile);
            grid = input(scanner);
            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("Could not open file");
            e.printStackTrace();
            return;
        }

        int rows = grid.length;
        int cols = grid[0].length;

        HashMap<Character, ArrayList<Coords>> antennas = new HashMap<>();
        HashSet<Coords> antinodes = new HashSet<>();

        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                if (grid[r][c] == '.') continue;
                if (antennas.containsKey(grid[r][c]) == false) {
                    antennas.put(grid[r][c], new ArrayList<>());
                }
                antennas.get(grid[r][c]).add(new Coords(r, c));
            }
        }

        for (Character key : antennas.keySet()) {
            for (Coords ant1 : antennas.get(key)) {
                for (Coords ant2 : antennas.get(key)) {
                    if (ant1.equals(ant2)) continue;
                    Coords diff = ant1.subtract(ant2);
                    Coords curr = ant1;
                    while (
                        0 <= curr.r() && curr.r() < rows &&
                        0 <= curr.c() && curr.c() < cols
                    ) {
                        antinodes.add(curr);
                        curr = curr.add(diff);
                    }
                }
            }
        }
        
        System.out.println(antinodes.size());
    }
}
