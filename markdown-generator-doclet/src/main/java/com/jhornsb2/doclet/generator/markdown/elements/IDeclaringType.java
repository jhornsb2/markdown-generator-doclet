package com.jhornsb2.doclet.generator.markdown.elements;

/**
 * Interface representing an element that has a declaring type (e.g., method or
 * field).
 */
public interface IDeclaringType {
	/**
	 * Returns the fully qualified name of the declaring type of the element.
	 * @return the fully qualified name of the declaring type
	 */
	String getDeclaringType();
}
