package nl.arkenbout.geoffrey.angel.engine.util;

import java.util.Arrays;

public class FloatArrayCollector {
    private float[] curr;
    private int size = 0;

    public FloatArrayCollector() {
        curr = new float[64];
    }

    public void add(double d) {
        if (curr.length == size) {
            curr = Arrays.copyOf(curr, size*2);
        }
        curr[size++] = (float) d;
    }

    public void join(FloatArrayCollector other) {
        if (size + other.size > curr.length) {
            curr = Arrays.copyOf(curr, size + other.size);
        }
        System.arraycopy(other.curr, 0, curr, size, other.size);
        size += other.size;
    }

    public float[] toArray() {
        if (size != curr.length) {
            curr = Arrays.copyOf(curr, size);
        }
        return curr;
    }
}
