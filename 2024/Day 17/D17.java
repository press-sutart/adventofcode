import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.Scanner;

public class D17 {
    public static class Computer {
        private long registerAReset = 0;
        private long registerBReset = 0;
        private long registerCReset = 0;

        private long registerA = 0;
        private long registerB = 0;
        private long registerC = 0;
        private long instPtr = 0;
        private ArrayList<Long> program = new ArrayList<>();
        private ArrayList<Long> output = new ArrayList<>();

        public ArrayList<Long> getProgram() {
            return program;
        }

        public long literal(long operand) {
            return operand;
        }

        public long combo(long operand) {
            long value = switch ((int)operand) {
                case 0, 1, 2, 3 -> operand;
                case 4          -> registerA;
                case 5          -> registerB;
                case 6          -> registerC;
                default         -> throw new IllegalArgumentException();
            };
            return value;
        }

        public void adv(long operand) {
            registerA = registerA >> combo(operand);
        }

        public void bxl(long operand) {
            registerB = registerB ^ literal(operand);
        }

        public void bst(long operand) {
            registerB = 7 & combo(operand);
        }

        public void jnz(long operand) {
            if (registerA != 0) instPtr = literal(operand) - 2;
        }

        public void bxc(long operand) {
            registerB = registerB ^ registerC;
        }

        public void out(long operand) {
            output.add(7 & combo(operand));
        }

        public void bdv(long operand) {
            registerB = registerA >> combo(operand);
        }

        public void cdv(long operand) {
            registerC = registerA >> combo(operand);
        }

        public ArrayList<Long> execute() {
            registerB = registerBReset;
            registerC = registerCReset;
            instPtr = 0;
            output = new ArrayList<>();

            while (instPtr < program.size()) {
                long opcode = program.get((int)instPtr);
                long operand = program.get((int)instPtr + 1);

                switch ((int)opcode) {
                    case 0: adv(operand); break;
                    case 1: bxl(operand); break;
                    case 2: bst(operand); break;
                    case 3: jnz(operand); break;
                    case 4: bxc(operand); break;
                    case 5: out(operand); break;
                    case 6: bdv(operand); break;
                    case 7: cdv(operand); break;
                }

                instPtr += 2;
            }

            return output;
        }

        public ArrayList<Long> executeOverwriteA(long a) {
            registerA = a;
            return execute();
        }

        public ArrayList<Long> executeNormal() {
            registerA = registerAReset;
            return execute();
        }

        public Computer(long rA, long rB, long rC, ArrayList<Long> pro) {
            registerAReset = rA;
            registerBReset = rB;
            registerCReset = rC;
            program = pro;
        }

        public Computer() {}
    }

    public static final Pattern registerPattern =
        Pattern.compile("(Register [ABC]: )(\\d+)");
    public static final Pattern programPattern =
        Pattern.compile("(Program: )([0-9,]+)");

    public static Computer input(Scanner scanner) {
        ArrayList<Long> registers = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            String registerLine = scanner.nextLine();
            Matcher matcher = registerPattern.matcher(registerLine);
            matcher.find();
            long number = Long.valueOf(matcher.group(2));
            registers.add(number);
        }

        scanner.nextLine();
        String programLine = scanner.nextLine();
        Matcher matcher = programPattern.matcher(programLine);
        matcher.find();
        String[] programParts = matcher.group(2).split(",");
        ArrayList<Long> program = new ArrayList<>();
        for (String s : programParts) {
            program.add(Long.valueOf(s));
        }

        return new Computer(
            registers.get(0), registers.get(1),
            registers.get(2), program
        );
    }

    public static Computer readFromFile(String filePath) {
        try {
            File file = new File(filePath);
            Scanner scanner = new Scanner(file);
            Computer computer = input(scanner);
            scanner.close();
            return computer;
        } catch (FileNotFoundException e) {
            System.out.println("Could not open file");
            e.printStackTrace();
            return new Computer();
        }
    }

    public static void main(String[] args) {
        Computer computer = readFromFile("Day 17/input.txt");
        System.out.println(computer.executeNormal());
        System.out.println(computer.executeOverwriteA(37221261688308L));
    }
}
