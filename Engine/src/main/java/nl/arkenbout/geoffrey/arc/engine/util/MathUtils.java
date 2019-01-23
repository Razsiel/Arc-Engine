package nl.arkenbout.geoffrey.arc.engine.util;

public class MathUtils {
    public static float remap(float value, float fromMin, float fromMax, float toMin, float toMax) {
        //Y = (X-A)/(B-A) * (D-C) + C
        return (value - fromMin) / (fromMax - fromMin) * (toMax - toMin) + toMin;
    }
}
