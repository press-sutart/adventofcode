import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class D25Main {
    public static ArrayList<String> readFromFile(String filePath) {
        try {
            File file = new File(filePath);
            Scanner scanner = new Scanner(file);
            ArrayList<String> inputLines = new ArrayList<>();

            while (scanner.hasNextLine()) {
                inputLines.add(scanner.nextLine());
            }

            scanner.close();
            return inputLines;
        } catch (FileNotFoundException e) {
            System.out.println("Could not open file");
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public static void main(String[] args) {
        ArrayList<String> inputLines =
            readFromFile("Day 25/input.txt");
        D25Solver solver = new D25Solver(inputLines);
        System.out.println(solver.solve());
    }
}