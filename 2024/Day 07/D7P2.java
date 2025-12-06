import java.io.File;
import java.io.FileNotFoundException;
import java.lang.Long;
import java.util.Scanner;

public class D7P2 {
    public static long[] mapStringsToLongs(String[] strings) {
        long[] result = new long[strings.length];
        for (int i = 0; i < strings.length; i++) {
            result[i] = Long.parseLong(strings[i]);
        }
        return result;
    }

    public static int intPow(int base, int exponent) {
        int result = 1;
        while (exponent > 0) {
            if (exponent % 2 == 1) {
                result = result * base;
            }
            base = base * base;
            exponent /= 2;
        }
        return result;
    }

    public static long checkCalibration(String lineData) {
        String[] firstSplit = lineData.split(": ");
        assert(firstSplit.length == 2);
        long testValue = Long.parseLong(firstSplit[0]);
        long[] operands = mapStringsToLongs(firstSplit[1].split(" "));
        int opLength = operands.length;
        int limit = intPow(3, opLength - 1);
        Boolean isPossible = false;

        for (int maskValue = 0; maskValue < limit; maskValue++) {
            D7Tritmask mask = new D7Tritmask(maskValue);
            long result = operands[0];
            for (int i = 0; i < opLength - 1; i++) {
                result = switch (mask.getTritAtIndex(i)) {
                             case 0  -> result + operands[i + 1];
                             case 1  -> result * operands[i + 1];
                             case 2  -> Long.parseLong(
                                            String.valueOf(result) +
                                            String.valueOf(operands[i + 1])
                                        );
                             default -> 0L;
                         };
            }
            if (result == testValue) {
                isPossible = true;
                break;
            }
        }

        return isPossible ? testValue : 0L;
    }

    public static void run(Scanner scanner) {
        long total = 0;
        while (scanner.hasNextLine()) {
            String lineData = scanner.nextLine();
            total += checkCalibration(lineData);
        }
        System.out.println(total);
    }

    public static void main(String[] args) {
        try {
            File inputFile = new File("Day 07/input.txt");
            Scanner scanner = new Scanner(inputFile);
            run(scanner);
            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("Could not open file");
            e.printStackTrace();
        }
    }
}