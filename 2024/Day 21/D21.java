import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class D21 {
    public record Coords(int r, int c) {
        public Coords add(Coords rhs) {
            return new Coords(r + rhs.r, c + rhs.c);
        }
        public Coords rotateCW() {
            return new Coords(c, -r);
        }
    }

    public record Transition(char s, char e) {}

    public static class Solver {
        private HashMap<Character, Coords> NUM_BUTTONS = new HashMap<>(
            Map.ofEntries(
                Map.entry('7', new Coords(0, 0)),
                Map.entry('8', new Coords(0, 1)),
                Map.entry('9', new Coords(0, 2)),
                Map.entry('4', new Coords(1, 0)),
                Map.entry('5', new Coords(1, 1)),
                Map.entry('6', new Coords(1, 2)),
                Map.entry('1', new Coords(2, 0)),
                Map.entry('2', new Coords(2, 1)),
                Map.entry('3', new Coords(2, 2)),
                Map.entry('0', new Coords(3, 1)),
                Map.entry('A', new Coords(3, 2))
            )
        );
        
        private HashMap<Character, Coords> DIR_BUTTONS = new HashMap<>(
            Map.ofEntries(
                Map.entry('^', new Coords(0, 1)),
                Map.entry('A', new Coords(0, 2)),
                Map.entry('<', new Coords(1, 0)),
                Map.entry('v', new Coords(1, 1)),
                Map.entry('>', new Coords(1, 2))
            )
        );

        private ArrayList<String> codes = new ArrayList<>();
        private HashMap<Transition, HashMap<Transition, Long>> numLayers;
        private HashMap<Transition, HashMap<Transition, Long>> dirLayers;

        public HashMap<Transition, HashMap<Transition, Long>> getNumLayers() {
            HashMap<Transition, HashMap<Transition, Long>> layers =
                new HashMap<>();

            for (char c1 : NUM_BUTTONS.keySet()) {
                for (char c2 : NUM_BUTTONS.keySet()) {
                    Transition baseTransition = new Transition(c1, c2);
                    HashMap<Transition, Long> layer = new HashMap<>();

                    Coords co1 = NUM_BUTTONS.get(c1);
                    Coords co2 = NUM_BUTTONS.get(c2);
                    int dr = co2.r() - co1.r();
                    int dc = co2.c() - co1.c();
                    String presses = "A";

                    if (co1.r() == 3 && co2.c() == 0) {
                        presses += "^".repeat(-dr);
                        presses += "<".repeat(-dc);
                    } else if (co1.c() == 0 && co2.r() == 3) {
                        presses += ">".repeat(dc);
                        presses += "v".repeat(dr);
                    } else {
                        if (dc < 0) presses += "<".repeat(-dc);
                        if (dr < 0) presses += "^".repeat(-dr);
                        if (dr > 0) presses += "v".repeat(dr);
                        if (dc > 0) presses += ">".repeat(dc);
                    }

                    presses += "A";

                    for (int i = 0; i < presses.length() - 1; i++) {
                        Transition layerTransition = new Transition(
                            presses.charAt(i), presses.charAt(i + 1)
                        );
                        if (!layer.keySet().contains(layerTransition)) {
                            layer.put(layerTransition, 0L);
                        }
                        layer.put(
                            layerTransition,
                            layer.get(layerTransition) + 1
                        );
                    }

                    //System.out.println("transition: " + baseTransition + ", layer = " + layer);
                    layers.put(baseTransition, layer);
                }
            }

            return layers;
        }

        public HashMap<Transition, HashMap<Transition, Long>> getDirLayers() {
            HashMap<Transition, HashMap<Transition, Long>> layers =
                new HashMap<>();

            for (char c1 : DIR_BUTTONS.keySet()) {
                for (char c2 : DIR_BUTTONS.keySet()) {
                    Transition baseTransition = new Transition(c1, c2);
                    HashMap<Transition, Long> layer = new HashMap<>();

                    Coords co1 = DIR_BUTTONS.get(c1);
                    Coords co2 = DIR_BUTTONS.get(c2);
                    int dr = co2.r() - co1.r();
                    int dc = co2.c() - co1.c();
                    String presses = "A";

                    if (c1 == '<') {
                        if (dc > 0) presses += ">".repeat(dc);
                        if (dr < 0) presses += "^".repeat(-dr);
                    } else if (c2 == '<') {
                        if (dr > 0) presses += "v".repeat(dr);
                        if (dc < 0) presses += "<".repeat(-dc);
                    } else {
                        if (dc < 0) presses += "<".repeat(-dc);
                        if (dr < 0) presses += "^".repeat(-dr);
                        if (dr > 0) presses += "v".repeat(dr);
                        if (dc > 0) presses += ">".repeat(dc);
                    }

                    presses += "A";

                    for (int i = 0; i < presses.length() - 1; i++) {
                        Transition layerTransition = new Transition(
                            presses.charAt(i), presses.charAt(i + 1)
                        );
                        if (!layer.keySet().contains(layerTransition)) {
                            layer.put(layerTransition, 0L);
                        }
                        layer.put(
                            layerTransition,
                            layer.get(layerTransition) + 1
                        );
                    }

                    //System.out.println("transition: " + baseTransition + ", layer = " + layer);
                    layers.put(baseTransition, layer);
                }
            }

            return layers;
        }

        public long getNumberOfMoves(String code, int dirKeypadCnt) {
            code = "A" + code;
            HashMap<Transition, Long> transitions = new HashMap<>();

            for (int i = 0; i < code.length() - 1; i++) {
                Transition baseTransition =
                    new Transition(code.charAt(i), code.charAt(i + 1));
                HashMap<Transition, Long> layer = numLayers.get(baseTransition);

                for (Transition layerTransition : layer.keySet()) {
                    if (!transitions.keySet().contains(layerTransition)) {
                        transitions.put(layerTransition, 0L);
                    }
                    transitions.put(
                        layerTransition,
                        transitions.get(layerTransition) +
                        layer.get(layerTransition)
                    );
                }
            }

            for (int i = 0; i < dirKeypadCnt; i++) {
                HashMap<Transition, Long> temp = new HashMap<>();

                for (Transition baseTransition : transitions.keySet()) {
                    HashMap<Transition, Long> layer =
                        dirLayers.get(baseTransition);
                    long freq = transitions.get(baseTransition);
                    
                    for (Transition layerTransition : layer.keySet()) {
                        if (!temp.keySet().contains(layerTransition)) {
                            temp.put(layerTransition, 0L);
                        }
                        temp.put(
                            layerTransition,
                            temp.get(layerTransition) +
                            layer.get(layerTransition) * freq
                        );
                    }
                }

                transitions = temp;
            }

            long numberOfMoves = 0;

            for (Transition transition : transitions.keySet()) {
                numberOfMoves += transitions.get(transition);
            }

            return numberOfMoves;
        }

        public long solve(int dirKeypadCnt) {
            long sumOfComplexities = 0;

            for (String code : codes) {
                long moves = getNumberOfMoves(code, dirKeypadCnt);
                long numericPart = Long.valueOf(
                    code.substring(0, 3)
                );
                sumOfComplexities += moves * numericPart;

                System.out.println(
                    "moves = " + moves +
                    ", numeric part = " + numericPart
                );
            }

            return sumOfComplexities;
        }

        public long solveP1() {
            return solve(2);
        }

        public long solveP2() {
            return solve(25);
        }

        public Solver(ArrayList<String> codes) {
            this.codes = codes;
            numLayers = getNumLayers();
            dirLayers = getDirLayers();
        }
    }

    public static ArrayList<String> input(Scanner scanner) {
        ArrayList<String> codes = new ArrayList<>();
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            codes.add(line);
        }
        return codes;
    }

    public static ArrayList<String> readFromFile(String filePath) {
        try {
            File file = new File(filePath);
            Scanner scanner = new Scanner(file);
            ArrayList<String> codes = input(scanner);
            scanner.close();
            return codes;
        } catch (FileNotFoundException e) {
            System.out.println("Could not open file");
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public static void main(String[] args) {
        ArrayList<String> codes = readFromFile("Day 21/input.txt");
        Solver solver = new Solver(codes);
        System.out.println(solver.solveP1());
        System.out.println(solver.solveP2());
    }
}
