import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class D24Wires {
    private final Pattern KNOWN_PATTERN =
        Pattern.compile("(\\w+): ([01])");
    private final Pattern GATE_PATTERN =
        Pattern.compile("(\\w+) (AND|OR|XOR) (\\w+) -> (\\w+)");
    
    public HashMap<String, Integer> knownWires = new HashMap<>();
    public HashMap<String, D24Gate> gateWires = new HashMap<>();

    public boolean exists(String wire) {
        return knownWires.containsKey(wire) || gateWires.containsKey(wire);
    }

    public int getValue(String wire) {
        if (knownWires.containsKey(wire)) {
            return knownWires.get(wire);
        }

        D24Gate gate = gateWires.get(wire);
        int inputValue1 = getValue(gate.input1());
        int inputValue2 = getValue(gate.input2());
        int outputValue = switch (gate.type()) {
            case "AND" -> inputValue1 & inputValue2;
            case "OR"  -> inputValue1 | inputValue2;
            case "XOR" -> inputValue1 ^ inputValue2;
            default    -> 0;
        };
        System.out.println(
            "evaluated " +
            gate.input1() + " " + gate.type() + " " +
            gate.input2() + " -> " + wire);

        knownWires.put(wire, outputValue);
        return outputValue;
    }

    public D24Wires(ArrayList<String> lines) {
        int inputPart = 1;

        for (String line : lines) {
            if (line == "") {
                inputPart = 2;
            } else if (inputPart == 1) {
                Matcher knownMatcher = KNOWN_PATTERN.matcher(line);
                knownMatcher.find();
                String wire = knownMatcher.group(1);
                int value = Integer.parseInt(knownMatcher.group(2));
                knownWires.put(wire, value);
            } else {
                Matcher gateMatcher = GATE_PATTERN.matcher(line);
                gateMatcher.find();

                String input1 = gateMatcher.group(1);
                String type = gateMatcher.group(2);
                String input2 = gateMatcher.group(3);
                String output = gateMatcher.group(4);

                D24Gate gate = new D24Gate(type, input1, input2);
                gateWires.put(output, gate);
            }
        }
    }
}
