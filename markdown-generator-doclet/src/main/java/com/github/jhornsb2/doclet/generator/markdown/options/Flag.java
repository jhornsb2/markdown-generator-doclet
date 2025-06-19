package com.github.jhornsb2.doclet.generator.markdown.options;

import java.util.List;

import jdk.javadoc.doclet.Doclet.Option;

public class Flag implements Option {

	private final String name;
	private final String description;
	private boolean value;

	public Flag(final String name, final String description) {
		this.name = name;
		this.description = description;
		this.value = false;
	}

	@Override
	public int getArgumentCount() {
		return 0;
	}

	@Override
	public String getDescription() {
		return this.description;
	}

	@Override
	public Kind getKind() {
		return Kind.STANDARD;
	}

	@Override
	public List<String> getNames() {
		return List.of(this.name);
	}

	@Override
	public String getParameters() {
		return "";
	}

	@Override
	public boolean process(final String arg0, final List<String> arg1) {
		this.value = true;
		return true;
	}

	public boolean isSet() {
		return this.value;
	}

}
