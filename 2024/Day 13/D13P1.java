import java.io.File;
import java.io.FileNotFoundException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.Scanner;

public class D13P1 {
    public record Coords(int x, int y) {}
    private static Pattern numberPattern = Pattern.compile("\\d+");

    public static Coords extractCoords(String str) {
        Matcher matcher = numberPattern.matcher(str);
        matcher.find();
        int x = Integer.parseInt(matcher.group());
        matcher.find();
        int y = Integer.parseInt(matcher.group());
        return new Coords(x, y);
    }

    public static int inputAndExecuteTC(Scanner scanner) {
        String lineButtonA = scanner.nextLine();
        String lineButtonB = scanner.nextLine();
        String linePrize = scanner.nextLine();

        Coords moveA = extractCoords(lineButtonA);
        Coords moveB = extractCoords(lineButtonB);
        Coords prize = extractCoords(linePrize);
        final int IMPOSSIBLE = 999999999;
        int minCost = IMPOSSIBLE;

        for (int pressA = 0; pressA <= 100; pressA++) {
            int remX = prize.x() - pressA * moveA.x();
            int remY = prize.y() - pressA * moveA.y();
            if (
                remX < 0 || remX % moveB.x() != 0 ||
                remY < 0 || remY % moveB.y() != 0
            ) continue;

            int pressBX = remX / moveB.x();
            int pressBY = remY / moveB.y();
            if (pressBX != pressBY) continue;

            int cost = 3 * pressA + pressBX;
            minCost = Math.min(minCost, cost);
        }

        return minCost == IMPOSSIBLE ? 0 : minCost;
    }

    public static void main(String[] args) {
        try {
            File inputFile = new File("Day 13/input.txt");
            Scanner scanner = new Scanner(inputFile);
            int totalCost = 0;

            while (scanner.hasNextLine()) {
                totalCost += inputAndExecuteTC(scanner);
                if (scanner.hasNextLine()) {
                    scanner.nextLine();
                }
            }
            
            System.out.println(totalCost);
            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("Could not open file");
            e.printStackTrace();
        }
    }
}
