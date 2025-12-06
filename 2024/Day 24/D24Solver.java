import java.util.HashMap;
import java.util.Random;

public class D24Solver {
    D24Wires wires;

    public String getSpecialWireName(String s, int i) {
        if (i < 10) return s + "0" + String.valueOf(i);
        else return s + String.valueOf(i);
    }

    // must be run before all calls to tryAdd/assistP2
    public long solveP1() {
        long ans = 0;
        int bit = 0;

        while (wires.exists(getSpecialWireName("z", bit))) {
            int wireValue = wires.getValue(getSpecialWireName("z", bit));
            if (wireValue == 1) {
                ans |= 1L << bit;
            }
            bit++;
        }

        return ans;
    }

    public long tryAdd(long x, long y) {
        // overwrite start bits
        wires.knownWires = new HashMap<>();

        for (int bit = 0; bit <= 44; bit++) {
            wires.knownWires.put(
                getSpecialWireName("x", bit),
                (x & (1L << bit)) != 0 ? 1 : 0
            );
            wires.knownWires.put(
                getSpecialWireName("y", bit),
                (y & (1L << bit)) != 0 ? 1 : 0
            );
        }

        return solveP1();
    }

    public void assistP2(int bits) {
        Random rand = new Random();
        long aimSum = (1L << bits) - 1;
        long r = rand.nextLong(aimSum);
        long sum = tryAdd(r, aimSum - r);
        if (aimSum == sum) System.out.println("Sum is correct");
        else System.out.println("Sum is wrong");
    }

    public D24Solver(D24Wires wires) {
        this.wires = wires;
    }
}
