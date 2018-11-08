package net.softwaregeek.rpn.impl;

/**
 * Represents a problem which happened during expression evaluation.
 *
 * @author Andrew Polunin
 */
public class EvaluationException extends RuntimeException {
    public EvaluationException(final String message) {
        super(message);
    }
}
