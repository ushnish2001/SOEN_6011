//package com.example.arccosfunction;

public class ArccosCalculator {
    public double arccos(double x) {
        if (x < -1 || x > 1) {
            throw new IllegalArgumentException("Input must be between -1 and 1.");
        }
        double result = Math.PI / 2;
        double term = x;
        double xSquared = x * x;
        int n = 1;
        while (Math.abs(term) > 1e-15) {
            result -= term / (2 * n - 1);
            term *= xSquared * (2 * n - 1) / (2 * n + 1);
            n++;
        }
        return result;
    }

    public double toDegrees(double radians) {
        return radians * (180 / Math.PI);
    }
}
