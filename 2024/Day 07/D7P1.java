import java.io.File;
import java.io.FileNotFoundException;
import java.lang.Long;
import java.util.Scanner;

public class D7P1 {
    public static long[] mapStringsToLongs(String[] strings) {
        long[] result = new long[strings.length];
        for (int i = 0; i < strings.length; i++) {
            result[i] = Long.parseLong(strings[i]);
        }
        return result;
    }

    public static long checkCalibration(String lineData) {
        String[] firstSplit = lineData.split(": ");
        assert(firstSplit.length == 2);
        long testValue = Long.parseLong(firstSplit[0]);
        long[] operands = mapStringsToLongs(firstSplit[1].split(" "));
        int opLength = operands.length;
        Boolean isPossible = false;

        for (int bitmask = 0; bitmask < (1 << (opLength - 1)); bitmask++) {
            long result = operands[0];
            for (int i = 0; i < opLength - 1; i++) {
                result = (bitmask & (1 << i)) != 0
                         ? result + operands[i + 1]
                         : result * operands[i + 1];
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