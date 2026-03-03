package com.documentation.generator.java.fixtures.models;

/**
 * Checked exception used by fixture services.
 */
public class OperationException extends Exception {

	/**
	 * Creates an exception with a message.
	 *
	 * @param message reason text
	 */
	public OperationException(String message) {
		super(message);
	}
}
