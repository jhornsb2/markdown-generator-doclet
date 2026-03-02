package com.jhornsb2.doclet.generator.markdown.options;

import java.util.List;
import jdk.javadoc.doclet.Doclet.Option;

/**
 * Boolean doclet option implementation.
 * <p>
 * This option consumes no arguments and is considered enabled when present on
 * the command line.
 */
public class Flag implements Option {

	/** Primary option token (for example {@code -notimestamp}). */
	private final String name;
	/** Human-readable option description used by javadoc help output. */
	private final String description;
	/** Tracks whether the option token was provided by the caller. */
	private boolean value;

	/**
	 * Creates a new boolean option descriptor.
	 *
	 * @param name option name token.
	 * @param description option description text.
	 */
	public Flag(final String name, final String description) {
		this.name = name;
		this.description = description;
		this.value = false;
	}

	/**
	 * Returns the number of required option arguments.
	 *
	 * @return {@code 0} because this is a flag option.
	 */
	@Override
	public int getArgumentCount() {
		return 0;
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
	 * @return single-name list containing this flag token.
	 */
	@Override
	public List<String> getNames() {
		return List.of(this.name);
	}

	/**
	 * Returns the option parameter usage string.
	 *
	 * @return empty string because the flag has no parameters.
	 */
	@Override
	public String getParameters() {
		return "";
	}

	/**
	 * Processes this option occurrence.
	 *
	 * @param arg0 option name token provided by the doclet framework.
	 * @param arg1 parsed option arguments (always empty for this option).
	 * @return {@code true} to indicate successful processing.
	 */
	@Override
	public boolean process(final String arg0, final List<String> arg1) {
		this.value = true;
		return true;
	}

	/**
	 * Indicates whether this option was explicitly set.
	 *
	 * @return {@code true} when {@link #process(String, List)} was invoked.
	 */
	public boolean isSet() {
		return this.value;
	}
}
