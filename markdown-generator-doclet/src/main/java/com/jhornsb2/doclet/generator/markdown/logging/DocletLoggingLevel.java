package com.jhornsb2.doclet.generator.markdown.logging;

/**
 * Logging severity levels used by {@link DocletLogger}.
 * <p>
 * Levels are formatted with a normalized prefix to keep reporter output
 * aligned and easy to scan.
 */
public enum DocletLoggingLevel {
	/** Diagnostic details intended for troubleshooting. */
	DEBUG,
	/** High-level lifecycle and progress messages. */
	INFO,
	/** Recoverable problems that do not immediately stop generation. */
	WARN,
	/** Fatal or configuration errors that typically prevent generation. */
	ERROR;

	/**
	 * Returns a fixed-width prefix like {@code [INFO] } for log rendering.
	 *
	 * @return a left-padded level prefix suitable for aligned console output.
	 */
	public String getNormalizedPrefix() {
		final String prefix = String.format("[%s]", this.name());
		return String.format("%-7s", prefix); // Pad to 7 characters for alignment
	}
}
