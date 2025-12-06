import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;

public class P1 {
    public static int[] mapStringsToInts(String[] strings) {
        int[] ints = new int[strings.length];
        for (int i = 0; i < strings.length; i++) {
            ints[i] = Integer.parseInt(strings[i]);
        }
        return ints;
    }

    public static void processRuleLine(
        Boolean[][] orderingIsAllowed, String ruleLine
    ) {
        int[] lineInts = mapStringsToInts(ruleLine.split("\\|"));
        assert(lineInts.length == 2);
        orderingIsAllowed[lineInts[1]][lineInts[0]] = false;
    }

    // return 0 if ordering is illegal, else return middle page
    public static int processUpdateLine(
        Boolean[][] orderingIsAllowed, String updateLine
    ) {
        int[] lineInts = mapStringsToInts(updateLine.split(","));
        Boolean isCorrectlyOrdered = true;

        for (int i = 0; i < lineInts.length; i++) {
            for (int j = i + 1; j < lineInts.length; j++) {
                if (!orderingIsAllowed[lineInts[i]][lineInts[j]]) {
                    isCorrectlyOrdered = false;
                }
            }
        }

        return isCorrectlyOrdered
               ? lineInts[(lineInts.length - 1) / 2]
               : 0;
    }

    public static void execute(Scanner scanner) {
        Boolean[][] orderingIsAllowed = new Boolean[100][100];
        for (Boolean[] row : orderingIsAllowed) {
            Arrays.fill(row, true);
        }
        
        int inputSection = 1;
        int sumOfMiddles = 0;

        while (scanner.hasNextLine()) {
            String lineData = scanner.nextLine();

            if (lineData == "") {
                inputSection = 2;
            } else if (inputSection == 1) {
                processRuleLine(orderingIsAllowed, lineData);
            } else {
                sumOfMiddles += processUpdateLine(orderingIsAllowed, lineData);
            }
        }

        System.out.println(sumOfMiddles);
    }

    public static void main(String[] args) {
        try {
            File inputFile = new File("Day 05/input.txt");
            Scanner inputFileScanner = new Scanner(inputFile);
            execute(inputFileScanner);
            inputFileScanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("Could not open file");
            e.printStackTrace();
        }
    }
}