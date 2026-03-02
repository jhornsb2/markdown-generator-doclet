package com.jhornsb2.doclet.generator.markdown.template;

/**
 * Logical template categories used to render markdown pages.
 * <p>
 * Each value maps to one markdown template file and a corresponding
 * {@link com.jhornsb2.doclet.generator.markdown.elements.IElementData}
 * implementation.
 */
public enum TemplateKind {
	/** Project-level index template. */
	PROJECT,
	/** Java module documentation template. */
	MODULE,
	/** Java package documentation template. */
	PACKAGE,
	/** Interface type documentation template. */
	INTERFACE,
	/** Annotation type documentation template. */
	ANNOTATION,
	/** Class type documentation template. */
	CLASS,
	/** Record type documentation template. */
	RECORD,
	/** Enum type documentation template. */
	ENUM
}
