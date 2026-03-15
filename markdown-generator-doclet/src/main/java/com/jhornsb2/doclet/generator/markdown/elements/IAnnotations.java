package com.jhornsb2.doclet.generator.markdown.elements;

import java.util.List;

/**
 * Interface for elements that can have annotations.
 */
public interface IAnnotations {

	/**
	 * Gets the list of fully qualified annotation type names applied to the
	 * element.
	 *
	 * @return List of fully qualified annotation type names.
	 */
	List<String> getAnnotations();

}
