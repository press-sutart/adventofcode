import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class D24Main {
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
            readFromFile("Day 24/input.txt");
        D24Wires wires = new D24Wires(inputLines);
        D24Solver solver = new D24Solver(wires);
        //System.out.println(solver.solveP1());
        solver.assistP2(8);
    }
}