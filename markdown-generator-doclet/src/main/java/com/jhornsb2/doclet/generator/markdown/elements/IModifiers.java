package com.jhornsb2.doclet.generator.markdown.elements;

import java.util.Set;

/**
 * An interface for elements that have modifiers (e.g., public, static).
 */
public interface IModifiers {

	/**
	 * Returns the set of modifiers of the element.
	 *
	 * @return the set of modifiers of the element
	 */
	Set<JavaModifier> getModifiers();

}
