package net.softwaregeek.rpn.api;

import net.softwaregeek.rpn.impl.Context;
import net.softwaregeek.rpn.impl.EvaluationException;
import net.softwaregeek.rpn.impl.Operation;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Scanner;

/**
 * This is a kind of calculator "entry point".
 * It ties different pieces of calculator in a single place.
 *
 * @author Andrew Polunin
 */
public interface Calculator {
    /**
     * Reads data from input, evaluates expressions and writes the result into output
     *
     * @param input  stream to get data from
     * @param output stream to write data into
     */
    static void evaluate(final InputStream input, final OutputStream output) {
        final Context ctx = new Context();

        try (final Scanner in = new Scanner(input);
             final PrintStream out = new PrintStream(output)) {

            while (in.hasNext()) {
                if (in.hasNextDouble()) {
                    ctx.push(in.nextDouble());
                    continue;
                }

                try {
                    final String token = in.next();
                    final Operation operation = Operation.fromString(token);

                    if (operation == Operation.QUIT) {
                        break;
                    }

                    final double result = operation.apply(ctx);
                    out.println(result);
                } catch (EvaluationException e) {
                    out.printf("evaluation error: %s%n", e.getMessage());
                }
            }
        }
    }
}
