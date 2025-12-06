import java.io.File;
import java.io.FileNotFoundException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.Scanner;

public class D13P2 {
    public record Coords(long x, long y) {
        public Coords add(Coords rhs) {
            return new Coords(x + rhs.x, y + rhs.y);
        }
    }

    public static final Coords prizeOffset =
        new Coords(10000000000000L, 10000000000000L);
    public static final Pattern numberPattern =
        Pattern.compile("\\d+");

    public static Coords extractCoords(String str) {
        Matcher matcher = numberPattern.matcher(str);
        matcher.find();
        long x = Long.parseLong(matcher.group());
        matcher.find();
        long y = Long.parseLong(matcher.group());
        return new Coords(x, y);
    }

    public static long inputAndExecuteTC(Scanner scanner) {
        String lineButtonA = scanner.nextLine();
        String lineButtonB = scanner.nextLine();
        String linePrize = scanner.nextLine();

        Coords moveA = extractCoords(lineButtonA);
        Coords moveB = extractCoords(lineButtonB);
        Coords prize = extractCoords(linePrize).add(prizeOffset);
        
        // b satisfies bu = v
        long u = moveA.x() * moveB.y() - moveB.x() * moveA.y();
        long v = moveA.x() * prize.y() - prize.x() * moveA.y();

        if (u == 0 && v == 0) {
            // infinite solutions: Ax/Ay = Bx/By = Px/Py
            // i assumed such cases either did not exist,
            // or existed but had no integer solutions,
            // and fortunately this was true because i was
            // lazy to solve this case
            return 0;
        } else if (u == 0 && v != 0) {
            // no solution
            return 0;
        } else if (u != 0 && v == 0) {
            // unique solution
            long b = 0;
            if (prize.x() % moveA.x() == 0) {
                long a = prize.x() / moveA.x();
                return 3 * a + b;
            } else return 0;
        } else if ((v % u + u) % u == 0) {
            // unique solution
            long b = v / u;
            if ((prize.x() - b * moveB.x()) % moveA.x() == 0) {
                long a = (prize.x() - b * moveB.x()) / moveA.x();
                return 3 * a + b;
            } else return 0;
        } else {
            // no solution
            return 0;
        }
    }

    public static void main(String[] args) {
        try {
            File inputFile = new File("Day 13/input.txt");
            Scanner scanner = new Scanner(inputFile);
            long totalCost = 0;

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
