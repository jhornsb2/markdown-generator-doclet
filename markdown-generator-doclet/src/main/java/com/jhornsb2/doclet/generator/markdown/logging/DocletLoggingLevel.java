package com.jhornsb2.doclet.generator.markdown.logging;

public enum DocletLoggingLevel {
	DEBUG,
	INFO,
	WARN,
	ERROR;

	public String getNormalizedPrefix() {
		final String prefix = String.format("[%s]", this.name());
		return String.format("%-7s", prefix); // Pad to 7 characters for alignment
	}
}
