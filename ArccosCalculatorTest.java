//package com.example.arccosfunction;

import org.junit.Test;
import static org.junit.Assert.*;

public class ArccosCalculatorTest {
    @Test
    public void testArccos() {
        ArccosCalculator calculator = new ArccosCalculator();
        double result = calculator.arccos(0.5);
        double expected = Math.acos(0.5); // This is for testing purposes
        assertEquals(expected, result, 1e-7);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidInput() {
        ArccosCalculator calculator = new ArccosCalculator();
        calculator.arccos(2);
    }

    @Test
    public void testToDegrees() {
        ArccosCalculator calculator = new ArccosCalculator();
        double radians = Math.PI;
        double degrees = calculator.toDegrees(radians);
        assertEquals(180.0, degrees, 1e-7);
    }
}
