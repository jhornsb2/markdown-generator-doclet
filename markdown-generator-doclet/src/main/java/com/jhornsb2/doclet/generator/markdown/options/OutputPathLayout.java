package com.jhornsb2.doclet.generator.markdown.options;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Locale;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Supported output layout modes for generated documentation file paths.
 */
public enum OutputPathLayout {
	HIERARCHICAL("hierarchical", "h"),
	FLAT("flat", "f");

	private final String optionValue;
	private final Set<String> optionAliases;

	OutputPathLayout(final String optionValue, final String... optionAliases) {
		this.optionValue = optionValue;
		final Set<String> aliases = new LinkedHashSet<>();
		aliases.add(OutputPathLayout.normalize(optionValue));
		if (optionAliases != null && optionAliases.length > 0) {
			Arrays.stream(optionAliases)
				.filter(alias -> alias != null && !alias.isBlank())
				.map(OutputPathLayout::normalize)
				.forEach(aliases::add);
		}
		this.optionAliases = Set.copyOf(aliases);
	}

	public String getOptionValue() {
		return this.optionValue;
	}

	public static String supportedOptionValues() {
		return Arrays.stream(values())
			.flatMap(layout -> layout.optionAliases.stream())
			.collect(Collectors.joining(", "));
	}

	public static OutputPathLayout fromOptionValue(final String value) {
		if (value == null || value.isBlank()) {
			return HIERARCHICAL;
		}

		final String normalized = normalize(value);
		return Arrays.stream(values())
			.filter(layout -> layout.optionAliases.contains(normalized))
			.findFirst()
			.orElseThrow(() ->
				new IllegalArgumentException(
					"Unsupported -path-layout value: '" +
						value +
						"'. Expected one of: " +
						supportedOptionValues()
				)
			);
	}

	private static String normalize(final String value) {
		return value.trim().toLowerCase(Locale.ROOT);
	}
}
