package com.jhornsb2.doclet.generator.markdown.elements;

import java.util.Collections;
import java.util.List;
import lombok.Builder;
import lombok.Value;

/**
 * Data class representing a method or constructor parameter.
 */
@Value
@Builder
public class VariableData implements ISimpleName, IReturnType, IAnnotations {

	/**
	 * Parameter name as declared in source.
	 */
	String simpleName;

	/**
	 * Declared parameter type as rendered by the compiler model.
	 */
	String type;

	/**
	 * Fully qualified parameter type when resolvable.
	 */
	String returnType;

	/**
	 * Zero-based position in the parameter list.
	 */
	int position;

	/**
	 * Indicates whether this parameter is the varargs parameter.
	 */
	boolean varArgs;

	/**
	 * Fully qualified annotation type names applied to the parameter.
	 */
	@Builder.Default
	List<String> annotations = Collections.emptyList();
}
