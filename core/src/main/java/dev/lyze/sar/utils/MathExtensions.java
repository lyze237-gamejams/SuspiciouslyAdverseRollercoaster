package dev.lyze.sar.utils;

public class MathExtensions {
    public static float approach(float start, float target, float increment) {
        increment = Math.abs(increment);
        if (start < target) {
            start += increment;

            if (start > target) {
                start = target;
            }
        } else {
            start -= increment;

            if (start < target) {
                start = target;
            }
        }
        return start;
    }
}