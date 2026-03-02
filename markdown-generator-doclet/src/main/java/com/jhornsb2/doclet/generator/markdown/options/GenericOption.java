package com.jhornsb2.doclet.generator.markdown.options;

import java.util.List;
import jdk.javadoc.doclet.Doclet.Option;

/**
 * Single-value doclet option implementation.
 * <p>
 * This option expects one argument and stores the latest parsed value.
 */
public class GenericOption implements Option {

	/** Primary option token (for example {@code -d}). */
	private final String name;
	/** Human-readable description used by javadoc help output. */
	private final String description;
	/** Current option value, initialized from a default. */
	private String value;

	/**
	 * Creates a new single-value option descriptor.
	 *
	 * @param name option name token.
	 * @param description option description text.
	 * @param defaultValue default value used when the option is omitted.
	 */
	public GenericOption(
		final String name,
		final String description,
		final String defaultValue
	) {
		this.name = name;
		this.description = description;
		this.value = defaultValue;
	}

	/**
	 * Returns the number of required option arguments.
	 *
	 * @return {@code 1} because this option consumes a single value.
	 */
	@Override
	public int getArgumentCount() {
		return 1;
	}

	/**
	 * Returns option description text for javadoc help output.
	 *
	 * @return description shown in option help.
	 */
	@Override
	public String getDescription() {
		return this.description;
	}

	/**
	 * Returns option classification expected by the Doclet API.
	 *
	 * @return {@link Kind#STANDARD}.
	 */
	@Override
	public Kind getKind() {
		return Kind.STANDARD;
	}

	/**
	 * Returns supported option aliases.
	 *
	 * @return single-name list containing this option token.
	 */
	@Override
	public List<String> getNames() {
		return List.of(this.name);
	}

	/**
	 * Returns the option parameter usage string.
	 *
	 * @return empty string to let javadoc render only the description text.
	 */
	@Override
	public String getParameters() {
		return "";
	}

	/**
	 * Processes this option occurrence and stores the provided argument.
	 *
	 * @param optionName option token provided by the doclet framework.
	 * @param values parsed option argument values.
	 * @return {@code true} when at least one value is present, otherwise
	 *         {@code false}.
	 */
	@Override
	public boolean process(final String optionName, final List<String> values) {
		if (values.size() >= 1) {
			this.value = values.get(0);
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Returns the current configured option value.
	 *
	 * @return current value after defaults and parsed overrides are applied.
	 */
	public String getValue() {
		return this.value;
	}
}
