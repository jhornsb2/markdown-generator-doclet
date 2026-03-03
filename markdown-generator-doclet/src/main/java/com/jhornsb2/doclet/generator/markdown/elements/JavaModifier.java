package com.jhornsb2.doclet.generator.markdown.elements;

/**
 * Java language modifiers with canonical keyword text.
 */
public enum JavaModifier {

	PUBLIC("public"),
	PROTECTED("protected"),
	PRIVATE("private"),
	ABSTRACT("abstract"),
	DEFAULT("default"),
	STATIC("static"),
	SEALED("sealed"),
	NON_SEALED("non-sealed"),
	FINAL("final"),
	TRANSIENT("transient"),
	VOLATILE("volatile"),
	SYNCHRONIZED("synchronized"),
	NATIVE("native"),
	STRICTFP("strictfp");

	private final String keyword;

	JavaModifier(final String keyword) {
		this.keyword = keyword;
	}

	public String getKeyword() {
		return this.keyword;
	}

	@Override
	public String toString() {
		return this.keyword;
	}
}
