package com.jhornsb2.doclet.generator.markdown.logging;

import java.util.Arrays;
import java.util.Locale;
import java.util.stream.Collectors;

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

	/**
	 * Returns the normalized option token for this level.
	 *
	 * @return lower-case option value token.
	 */
	public String getOptionValue() {
		return this.name().toLowerCase(Locale.ROOT);
	}

	/**
	 * Parses a doclet option value into a logging level.
	 *
	 * @param optionValue option text.
	 * @return resolved logging level.
	 * @throws IllegalArgumentException when the value is unsupported.
	 */
	public static DocletLoggingLevel fromOptionValue(final String optionValue) {
		for (DocletLoggingLevel level : values()) {
			if (level.getOptionValue().equals(optionValue)) {
				return level;
			}
		}
		throw new IllegalArgumentException(
			"Unsupported -log-level value: '" +
				optionValue +
				"'. Supported values are: " +
				supportedOptionValues()
		);
	}

	/**
	 * Returns all supported option values.
	 *
	 * @return comma-separated list of accepted tokens.
	 */
	public static String supportedOptionValues() {
		return Arrays.stream(values())
			.map(DocletLoggingLevel::getOptionValue)
			.collect(Collectors.joining(", "));
	}
}
