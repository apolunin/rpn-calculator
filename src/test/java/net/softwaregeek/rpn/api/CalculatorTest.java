package net.softwaregeek.rpn.api;

import org.junit.Test;

import java.io.*;
import java.nio.charset.StandardCharsets;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class CalculatorTest {

    private static double EPSILON = Math.ulp(1.0);

    @Test
    public void testAddition_success() {
        final String input = "5 3 +";
        final double expected = 8;
        final double actual = evaluate(input);
        checkResult(expected, actual);
    }

    @Test
    public void testAddition_missingOperands() {
        final String input = "5 +";
        final String expected = errorMessage("addition requires two operands");
        final String actual = evaluateAsString(input);
        assertEquals(expected, actual);
    }

    @Test
    public void testSubtraction_success() {
        final String input = "5 2 -";
        final double expected = 3;
        final double actual = evaluate(input);
        checkResult(expected, actual);
    }

    @Test
    public void testSubtraction_missingOperands() {
        final String input = "-";
        final String expected = errorMessage("subtraction requires two operands");
        final String actual = evaluateAsString(input);
        assertEquals(expected, actual);
    }

    @Test
    public void testMultiplication_success() {
        final String input = "5 7 *";
        final double expected = 35;
        final double actual = evaluate(input);
        checkResult(expected, actual);
    }

    @Test
    public void testMultiplication_missingOperands() {
        final String input = "8 *";
        final String expected = errorMessage("multiplication requires two operands");
        final String actual = evaluateAsString(input);
        assertEquals(expected, actual);
    }

    @Test
    public void testDivision_success() {
        final String input = "42 7 /";
        final double expected = 6;
        final double actual = evaluate(input);
        checkResult(expected, actual);
    }

    @Test
    public void testDivision_missingOperands() {
        final String input = "/";
        final String expected = errorMessage("division requires two operands");
        final String actual = evaluateAsString(input);
        assertEquals(expected, actual);
    }

    @Test
    public void testInvalidOperation() {
        final String input = "1 3 op";
        final String expected = errorMessage("unknown operation 'op'");
        final String actual = evaluateAsString(input);
        assertEquals(expected, actual);
    }

    private static double evaluate(final String data) {
        return Double.parseDouble(evaluateAsString(data));
    }

    private static String evaluateAsString(final String data) {
        final ByteArrayOutputStream out = new ByteArrayOutputStream();
        final ByteArrayInputStream in = new ByteArrayInputStream(
                data.getBytes(StandardCharsets.UTF_8));

        Calculator.evaluate(in, out);

        return new String(out.toByteArray(), StandardCharsets.UTF_8);
    }

    private static String errorMessage(final String message) {
        return String.format("evaluation error: %s%n", message);
    }

    private static void checkResult(final double expected, final double actual) {
        assertTrue(Math.abs(expected - actual) < EPSILON);
    }
}
