package com.jhornsb2.doclet.generator.markdown.elements;

import java.util.List;

/**
 * Interface representing an element that has parameters (e.g., method or
 * constructor).
 */
public interface IParameters {
	/**
	 * Returns a {@link List} of {@link VariableData} representing the
	 * parameters of the element.
	 * @return a list of variable data for the parameters
	 */
	List<VariableData> getParameters();
}
