package com.documentation.generator.java.fixtures.contracts;

/**
 * Declares a stable identifier for an object.
 *
 * @param <T> identifier type
 */
@FunctionalInterface
public interface Identifiable<T> {
	/**
	 * Returns the object identifier.
	 *
	 * @return identifier
	 */
	T id();
}
