package net.softwaregeek.rpn.impl;

import java.util.function.BinaryOperator;

/**
 * This enumeration contains all operations supported by calculator.
 * Every operation instance knows how to apply itself. It uses {@link Context}
 * for getting operands and storing the result.
 *
 * @author Andrew Polunin
 */
public enum Operation {
    ADD("+") {
        @Override
        public double apply(final Context ctx) {
            return applyBinary("addition", ctx, (left, right) -> left + right);
        }
    },

    SUBTRACT("-") {
        @Override
        public double apply(final Context ctx) {
            return applyBinary("subtraction", ctx, (left, right) -> left - right);
        }
    },

    MULTIPLY("*") {
        @Override
        public double apply(final Context ctx) {
            return applyBinary("multiplication", ctx, (left, right) -> left * right);
        }
    },

    DIVIDE("/") {
        @Override
        public double apply(final Context ctx) {
            return applyBinary("division", ctx, (left, right) -> left / right);
        }
    },

    QUIT("q") {
        @Override
        public double apply(final Context ctx) {
            throw new UnsupportedOperationException();
        }
    };

    private String token;

    Operation(final String token) {
        this.token = token;
    }

    /**
     * Every operation knows how to evaluate itself by overriding this method.
     * Operation instance should take operands from the context and push
     * the result into it.
     *
     * @param ctx ctx calculator {@link Context context}
     * @return result of operation evaluation
     */
    public abstract double apply(final Context ctx);

    /**
     * Helps to avoid code duplication when evaluating operations.
     *
     * @param opName name of the operation
     * @param ctx    calculator {@link Context context}
     * @param op     operation implementation (presumably, lambda expression)
     * @return result of operation evaluation
     * @throws EvaluationException if context doesn't have enough operands
     */
    double applyBinary(final String opName, final Context ctx,
            final BinaryOperator<Double> op) {

        if (ctx.size() < 2) {
            throw new EvaluationException(
                    String.format("%s requires two operands", opName));
        }

        final double right = ctx.pop();
        final double left = ctx.pop();
        final double result = op.apply(left, right);

        ctx.push(result);

        return result;
    }

    /**
     * Tries to find {@link Operation} instance matching provided token.
     *
     * @param token string representation of the operation
     * @return {@link Operation} instance
     * @throws EvaluationException if there is no match
     */
    public static Operation fromString(final String token) {
        for (final Operation operation : Operation.values()) {
            if (token.equals(operation.token)) {
                return operation;
            }
        }

        throw new EvaluationException(String.format("unknown operation '%s'", token));
    }
}
