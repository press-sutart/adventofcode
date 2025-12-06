import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class D22 {
    public static class Solver {
        public record Tuple4(long a, long b, long c, long d) {}

        ArrayList<Long> secrets;

        public long getNextSecret(long secret) {
            secret = ((secret << 6) ^ secret) & 16777215L;
            secret = ((secret >> 5) ^ secret) & 16777215L;
            secret = ((secret << 11) ^ secret) & 16777215L;
            return secret;
        }

        public ArrayList<Long> getDayOfPrices(long secret) {
            ArrayList<Long> day = new ArrayList<>();
            day.add(secret);

            for (int i = 0; i < 2000; i++) {
                secret = getNextSecret(secret);
                day.add(secret);
            }

            return day;
        }

        public long solveP1() {
            long sumOfSecrets = 0;

            for (long secret : secrets) {
                ArrayList<Long> dayOfPrices = getDayOfPrices(secret);
                sumOfSecrets += dayOfPrices.get(2000);
            }

            return sumOfSecrets;
        }

        public long solveP2() {
            HashMap<Tuple4, Long> sumEarn = new HashMap<>();

            for (long secret : secrets) {
                ArrayList<Long> day = getDayOfPrices(secret);
                day.replaceAll(x -> x % 10);
                HashMap<Tuple4, Long> earn = new HashMap<>();

                for (int start = 0; start <= 2000 - 4; start++) {
                    Tuple4 change = new Tuple4(
                        day.get(start + 1) - day.get(start),
                        day.get(start + 2) - day.get(start + 1),
                        day.get(start + 3) - day.get(start + 2),
                        day.get(start + 4) - day.get(start + 3)
                    );
                    earn.putIfAbsent(change, day.get(start + 4));
                }

                for (Tuple4 change : earn.keySet()) {
                    if (!sumEarn.keySet().contains(change)) {
                        sumEarn.put(change, 0L);
                    }
                    sumEarn.put(change, sumEarn.get(change) + earn.get(change));
                }
            }

            long maxSumEarn = 0;

            for (Tuple4 change : sumEarn.keySet()) {
                maxSumEarn = Math.max(sumEarn.get(change), maxSumEarn);
            }

            return maxSumEarn;
        }

        public Solver(ArrayList<Long> secrets) {
            this.secrets = secrets;
        }
    }

    public static ArrayList<Long> input(Scanner scanner) {
        ArrayList<Long> secrets = new ArrayList<>();
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            secrets.add(Long.valueOf(line));
        }
        return secrets;
    }

    public static ArrayList<Long> readFromFile(String filePath) {
        try {
            File file = new File(filePath);
            Scanner scanner = new Scanner(file);
            ArrayList<Long> secrets = input(scanner);
            scanner.close();
            return secrets;
        } catch (FileNotFoundException e) {
            System.out.println("Could not open file");
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public static void main(String[] args) {
        ArrayList<Long> secrets = readFromFile("Day 22/input.txt");
        Solver solver = new Solver(secrets);
        System.out.println(solver.solveP1());
        System.out.println(solver.solveP2());
    }
}
