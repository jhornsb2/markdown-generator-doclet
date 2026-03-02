package com.jhornsb2.doclet.generator.markdown.options;

/**
 * Indicates that one or more doclet options are invalid.
 */
public class DocletOptionValidationException extends RuntimeException {

	/**
	 * Creates a new validation exception.
	 *
	 * @param message validation failure message suitable for user-facing logs.
	 */
	public DocletOptionValidationException(final String message) {
		super(message);
	}

	/**
	 * Creates a new validation exception with an underlying cause.
	 *
	 * @param message validation failure message suitable for user-facing logs.
	 * @param cause underlying cause.
	 */
	public DocletOptionValidationException(
		final String message,
		final Throwable cause
	) {
		super(message, cause);
	}
}
