package com.jhornsb2.doclet.generator.markdown.logging;

import java.util.stream.Stream;
import javax.tools.Diagnostic.Kind;
import jdk.javadoc.doclet.Reporter;
import lombok.NonNull;
import lombok.Value;

/**
 * A simple logger for the doclet that uses the JDK's Reporter to log messages.
 */
@Value(staticConstructor = "forClass")
public class DocletLogger {

	/**
	 * The reporter used to log messages.
	 */
	private static Reporter reporter;

	/**
	 * Sets the reporter to be used by this logger.
	 * <p>
	 * This method should be called only once, typically during the initialization
	 * of the doclet.
	 *
	 * @param reporter the reporter to set
	 * @throws IllegalStateException if the reporter has already been set
	 */
	public static void setReporter(final Reporter reporter) {
		if (DocletLogger.reporter != null) {
			throw new IllegalStateException("Reporter has already been set.");
		}
		DocletLogger.reporter = reporter;
	}

	/**
	 * Checks if the reporter has been set.
	 *
	 * @throws IllegalStateException if the reporter has not been set
	 */
	private static void checkReporter() {
		if (reporter == null) {
			throw new IllegalStateException(
				"Reporter has not been set. Call DocletLogger.setReporter() first."
			);
		}
	}

	/**
	 * The class that is using this logger.
	 */
	@NonNull
	Class<?> clazz;

	/**
	 * Logs a debug message.
	 *
	 * @param message the message to log
	 * @param args    the arguments to format the message
	 * @throws IllegalStateException if the reporter has not been set
	 */
	public void debug(final String message, final Object... args) {
		checkReporter();
		reporter.print(
			Kind.NOTE,
			this.formatMessage(DocletLoggingLevel.DEBUG, message, args)
		);
	}

	/**
	 * Logs an info message.
	 *
	 * @param message the message to log
	 * @param args    the arguments to format the message
	 * @throws IllegalStateException if the reporter has not been set
	 */
	public void info(final String message, final Object... args) {
		checkReporter();
		reporter.print(
			Kind.NOTE,
			this.formatMessage(DocletLoggingLevel.INFO, message, args)
		);
	}

	/**
	 * Logs a warning message.
	 *
	 * @param message the message to log
	 * @param args    the arguments to format the message
	 * @throws IllegalStateException if the reporter has not been set
	 */
	public void warn(final String message, final Object... args) {
		checkReporter();
		reporter.print(
			Kind.WARNING,
			this.formatMessage(DocletLoggingLevel.WARN, message, args)
		);
	}

	/**
	 * Logs an error message.
	 *
	 * @param message the message to log
	 * @param args    the arguments to format the message
	 * @throws IllegalStateException if the reporter has not been set
	 */
	public void error(final String message, final Object... args) {
		checkReporter();
		reporter.print(
			Kind.ERROR,
			this.formatMessage(DocletLoggingLevel.ERROR, message, args)
		);
	}

	/**
	 * Logs an error message with a throwable.
	 *
	 * @param message   the message to log
	 * @param throwable the throwable to log
	 * @param args      the arguments to format the message
	 */
	public void error(
		final String message,
		final Throwable throwable,
		final Object... args
	) {
		checkReporter();
		final String formattedMessage = this.formatMessage(
			DocletLoggingLevel.ERROR,
			message,
			args
		);
		reporter.print(
			Kind.ERROR,
			formattedMessage + "\n" + throwable.getMessage()
		);
		if (
			throwable.getStackTrace() != null &&
			throwable.getStackTrace().length > 0
		) {
			reporter.print(
				Kind.OTHER,
				"Stack trace: " + throwable.getStackTrace()[0]
			);
		}
	}

	/**
	 * Formats a message by replacing placeholders with the provided arguments.
	 *
	 * @param message the message to format
	 * @param args    the arguments to replace the placeholders
	 * @return the formatted message
	 */
	private String formatMessage(
		final DocletLoggingLevel level,
		final String message,
		final Object... args
	) {
		// Create a logging message prefix with timestamp, logging level, and logger name
		final String logTimestamp = java.time.LocalDateTime.now().toString();
		final String loggingLevel = level.getNormalizedPrefix();
		final String loggerName = this.clazz.getName();
		final String loggingMessagePrefix = String.format(
			"%s %s [%s] ",
			logTimestamp,
			loggingLevel,
			loggerName
		);

		// Create the formatted message by replacing placeholders with the provided arguments
		final String providedMessageTemplate = message.replace("{}", "%s");
		final Object[] formattedArgs = Stream.of(args)
			.map(arg -> arg == null ? "null" : arg.toString())
			.toArray(Object[]::new);
		final String formattedMessage = String.format(
			providedMessageTemplate,
			formattedArgs
		);

		// The final log message includes the logging message prefix and the formatted message
		return loggingMessagePrefix + formattedMessage;
	}
}
