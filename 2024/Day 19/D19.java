import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Scanner;

public class D19 {
    public static class Solver {
        private final HashSet<String> towels;
        private final ArrayList<String> designs;

        public boolean isMatchable(String design, int beginIndex) {
            int len = design.length();
            if (beginIndex == len) return true;

            for (int endIndex = beginIndex + 1; endIndex <= len; endIndex++) {
                if (
                    towels.contains(design.substring(beginIndex, endIndex)) &&
                    isMatchable(design, endIndex)
                ) return true;
            }
            return false;
        }

        public int solveP1() {
            int cnt = 0;
            for (String design : designs) {
                if (isMatchable(design, 0)) cnt++;
            }
            return cnt;
        }

        private final long SENTINEL = -1;

        public long countMatches(String design, int beginIndex, long[] memo) {
            int len = design.length();
            if (beginIndex == len) return 1;
            if (memo[beginIndex] != SENTINEL) return memo[beginIndex];

            long sum = 0;
            for (int endIndex = beginIndex + 1; endIndex <= len; endIndex++) {
                if (towels.contains(design.substring(beginIndex, endIndex))) {
                    sum += countMatches(design, endIndex, memo);
                }
            }
            memo[beginIndex] = sum;
            return sum;
        }

        public long solveP2() {
            long cnt = 0;
            for (String design : designs) {
                long[] memo = new long[design.length()];
                Arrays.fill(memo, SENTINEL);
                cnt += countMatches(design, 0, memo);
            }
            return cnt;
        }

        public Solver(HashSet<String> towels, ArrayList<String> designs) {
            this.towels = towels;
            this.designs = designs;
        }
    }

    public static Solver input(Scanner scanner) {
        String towelLine = scanner.nextLine();
        HashSet<String> towels = new HashSet<>(
            Arrays.asList(towelLine.split(", "))
        );

        scanner.nextLine();

        ArrayList<String> designs = new ArrayList<>();
        while (scanner.hasNextLine()) {
            String design = scanner.nextLine();
            designs.add(design);
        }

        return new Solver(towels, designs);
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
            return new Solver(new HashSet<>(), new ArrayList<>());
        }
    }

    public static void main(String[] args) {
        Solver solver = readFromFile("Day 19/input.txt");
        System.out.println(solver.solveP1());
        System.out.println(solver.solveP2());
    }
}
