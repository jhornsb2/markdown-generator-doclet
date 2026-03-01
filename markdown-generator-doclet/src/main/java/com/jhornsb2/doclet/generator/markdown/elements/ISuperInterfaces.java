package com.jhornsb2.doclet.generator.markdown.elements;

import java.util.Set;

/**
 * Provides the fully qualified names of interfaces implemented or extended.
 */
public interface ISuperInterfaces {
	/**
	 * Returns the fully qualified names of interfaces that are implemented or
	 * extended by the element.
	 *
	 * @return A {@link Set} of fully qualified interface names.
	 */
	Set<String> getSuperInterfaces();
}
