package net.softwaregeek.rpn.impl;

import java.util.Deque;
import java.util.LinkedList;

/**
 * Represents calculator execution context.
 * Stores operands and operation results. Works as LIFO.
 *
 * @author Andrew Polunin
 */
public class Context {

    private Deque<Double> deque = new LinkedList<>();

    /**
     * Pushes the value into the context.
     *
     * @param value number to push
     */
    public void push(final double value) {
        deque.push(value);
    }

    /**
     * Pops the value from the context and returns it.
     *
     * @return popped value
     */
    public double pop() {
        return deque.pop();
    }

    /**
     * Gets the total number of values in the context.
     *
     * @return context size
     */
    public int size() {
        return deque.size();
    }
}
