package com.jhornsb2.doclet.generator.markdown.elements;

import java.util.Optional;

/**
 * Provides the fully qualified name of a superclass, if present.
 */
public interface ISuperClass {
	/**
	 * Returns the fully qualified name of the superclass for this element.
	 *
	 * @return An {@link Optional} containing the fully qualified superclass
	 *         name, or {@link Optional#empty()} if there is no superclass.
	 */
	Optional<String> getSuperClass();
}
