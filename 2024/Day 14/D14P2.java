import java.io.File;
import java.io.FileWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.Scanner;

public class D14P2 {
    public static final int START_DURATION = 11;
    public static final int TIME_STEP = 101;
    public static final int END_DURATION = 100000;
    public static final int WIDTH = 101;
    public static final int HEIGHT = 103;
    public static final int MIDWIDTH = (WIDTH - 1) / 2;
    public static final int MIDHEIGHT = (HEIGHT - 1) / 2;
    public static final Pattern numberPattern = Pattern.compile("-?\\d+");

    public record Coords(int x, int y) {
        public int getQuadrant() {
            return x < MIDWIDTH && y < MIDHEIGHT ? 0
                 : x < MIDWIDTH && y > MIDHEIGHT ? 1
                 : x > MIDWIDTH && y < MIDHEIGHT ? 2
                 : x > MIDWIDTH && y > MIDHEIGHT ? 3 : 4;
        }

        public Coords add(Coords rhs) {
            return new Coords(x + rhs.x, y + rhs.y);
        }

        public Coords scale(int k) {
            return new Coords(k * x, k * y);
        }

        public Coords teleport() {
            return new Coords(
                Math.floorMod(x, WIDTH), Math.floorMod(y, HEIGHT)
            );
        }
    }

    public record Robot(Coords startPosition, Coords velocity) {
        public Coords getPosition(int t) {
            return velocity.scale(t).add(startPosition).teleport();
        }
    }

    public static ArrayList<Robot> input(Scanner scanner) {
        ArrayList<Robot> robots = new ArrayList<>();

        while (scanner.hasNextLine()) {
            String inputLine = scanner.nextLine();
            Matcher matcher = numberPattern.matcher(inputLine);
            int[] intsInLine = new int[4];

            for (int i = 0; i < 4; i++) {
                matcher.find();
                intsInLine[i] = Integer.parseInt(matcher.group());
            }

            robots.add(new Robot(
                new Coords(intsInLine[0], intsInLine[1]),
                new Coords(intsInLine[2], intsInLine[3])
            ));
        }

        return robots;
    }

    public static ArrayList<Robot> readFromFile(String filePath) {
        try {
            File inputFile = new File(filePath);
            Scanner scanner = new Scanner(inputFile);
            ArrayList<Robot> robots = input(scanner);
            scanner.close();
            return robots;
        } catch (FileNotFoundException e) {
            System.out.println("Could not open file");
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public static void writeToFile(String filePath, ArrayList<String> lines) {
        try {
            FileWriter writer = new FileWriter(filePath);
            for (String line : lines) {
                writer.append(line + "\n");
            }
            writer.close();
        } catch (IOException e) {
            System.out.println("Could not write to file");
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        ArrayList<Robot> robots = readFromFile("Day 14/input.txt");
        ArrayList<String> outputLines = new ArrayList<>();

        for (int t = START_DURATION; t <= END_DURATION; t += TIME_STEP) {
            boolean[][] existsRobot = new boolean[HEIGHT][WIDTH];
            for (int i = 0; i < HEIGHT; i++) {
                Arrays.fill(existsRobot[i], false);
            }

            for (Robot robot : robots) {
                Coords pos = robot.getPosition(t);
                existsRobot[pos.y()][pos.x()] = true;
            }

            outputLines.add("Time: " + String.valueOf(t));
            for (int i = 0; i < HEIGHT; i++) {
                String line = "";
                for (int j = 0; j < WIDTH; j++) {
                    line = line + (existsRobot[i][j] ? "*" : ".");
                }
                outputLines.add(line);
            }
            outputLines.add("");
        }

        writeToFile("Day 14/output.txt", outputLines);
    }
}
