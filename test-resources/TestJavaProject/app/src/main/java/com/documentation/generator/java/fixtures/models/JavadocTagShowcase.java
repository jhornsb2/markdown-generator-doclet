package com.documentation.generator.java.fixtures.models;

/**
 * Demonstrates commonly used and often-missed Javadoc tags.
 *
 * @since 1.0
 * @see java.util.Optional
 */
public class JavadocTagShowcase {

	/**
	 * Returns a stable value.
	 *
	 * @apiNote This method intentionally returns a deterministic value.
	 * @implSpec Implementations should avoid external side effects.
	 * @return stable value
	 */
	public int stableValue() {
		return 42;
	}

	/**
	 * Legacy API retained for migration examples.
	 *
	 * @return legacy value
	 * @deprecated Use {@link #stableValue()} instead.
	 */
	@Deprecated(since = "1.1", forRemoval = false)
	public int legacyValue() {
		return stableValue();
	}
}
