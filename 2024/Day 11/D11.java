import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class D11 {
    public record StateParams(long number, long steps) {}

    public static class MemoStones {
        private HashMap<StateParams, Long> memoResults = new HashMap<>();

        public Long getStones(StateParams params) {
            long s = params.steps();

            if (s == 0) {
                return 1L;
            }

            if (memoResults.containsKey(params)) {
                return memoResults.get(params);
            }

            long n = params.number();
            long ans = 0;

            if (n == 0) {
                ans = getStones(new StateParams(1, s - 1));
            } else {
                String nStr = String.valueOf(n);
                int l = nStr.length();
                if (l % 2 == 0) {
                    String firstHalfStr = nStr.substring(0, l / 2);
                    long firstHalf = Long.parseLong(firstHalfStr);
                    String secondHalfStr = nStr.substring(l / 2);
                    long secondHalf = Long.parseLong(secondHalfStr);
                    ans = getStones(new StateParams(firstHalf, s - 1)) +
                          getStones(new StateParams(secondHalf, s - 1));
                } else {
                    ans = getStones(new StateParams(n * 2024, s - 1));
                }
            }

            memoResults.put(params, ans);
            return ans;
        }

        public MemoStones() {}
    }

    public static ArrayList<Long> input(Scanner scanner) {
        ArrayList<Long> inputStones = new ArrayList<>();
        while (scanner.hasNext()) {
            inputStones.add(scanner.nextLong());
        }
        return inputStones;
    }

    public static ArrayList<Long> readFromFile(String filePath) {
        try {
            File inputFile = new File(filePath);
            Scanner scanner = new Scanner(inputFile);
            ArrayList<Long> inputStones = input(scanner);
            scanner.close();
            return inputStones;
        } catch (FileNotFoundException e) {
            System.out.println("Could not open file");
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
    
    public static void main(String[] args) {
        ArrayList<Long> inputStones = readFromFile(
            "Day 11/input.txt"
        );

        MemoStones fun = new MemoStones();
        long total = 0L;

        for (long n : inputStones) {
            // change 75 to 25 for Part 1
            total += fun.getStones(new StateParams(n, 75));
        }

        System.out.println(total);
    }
}