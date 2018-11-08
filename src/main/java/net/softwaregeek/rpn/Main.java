package net.softwaregeek.rpn;

import net.softwaregeek.rpn.api.Calculator;

/**
 * Calculator starting point.
 *
 * @author Andrew Polunin
 */
public class Main {
    public static void main(final String[] args) {
        Calculator.evaluate(System.in, System.out);
    }
}
