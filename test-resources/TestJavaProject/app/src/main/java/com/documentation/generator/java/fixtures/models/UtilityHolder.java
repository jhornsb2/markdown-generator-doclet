package com.documentation.generator.java.fixtures.models;

/**
 * Utility class with a private constructor.
 */
public final class UtilityHolder {

	private UtilityHolder() {
		throw new IllegalStateException("No instances.");
	}

	/**
	 * Normalizes an input text.
	 *
	 * @param value source value
	 * @return normalized text
	 */
	public static String normalize(String value) {
		return value == null ? "" : value.trim();
	}
}
