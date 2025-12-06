public class D7Tritmask {
    private int value;
    private int size;
    private int[] trits;

    public int getSize() {
        return size;
    }

    public int getTritAtIndex(int i) {
        return i >= size ? 0 : trits[i];
    }

    public D7Tritmask(int val) {
        value = val;

        size = 0;
        for (int v = value; v != 0; v /= 3) {
            size++;
        }

        trits = new int[size];
        for (int i = 0, v = value; v != 0; i++, v /= 3) {
            trits[i] = v % 3;
        }
    }
}