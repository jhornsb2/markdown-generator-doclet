package com.jhornsb2.doclet.generator.markdown.elements;

import java.util.Collections;
import java.util.Set;
import lombok.Builder;
import lombok.Value;

/**
 * Data class representing a Java field element.
 */
@Value
@Builder
public class FieldData
	implements
		IElementData,
		ISimpleName,
		IQualifiedName,
		IKind,
		IDocComment,
		IModifiers,
		IReturnType,
		IDeclaringType
{

	/**
	 * The simple name of the field (e.g., "myField").
	 */
	String simpleName;

	/**
	 * The fully qualified name of the field (e.g.,
	 * "com.example.MyClass.myField").
	 */
	String qualifiedName;

	/**
	 * The kind of the field (e.g. "field").
	 */
	String kind;

	/**
	 * The documentation comment of the field.
	 */
	String docComment;

	/**
	 * The {@link Set} of declaration modifiers on the field.
	 */
	@Builder.Default
	Set<JavaModifier> modifiers = Collections.emptySet();

	/**
	 * The fully qualified name of the return type of the field.
	 */
	String returnType;

	/**
	 * The fully qualified name of the declaring type of the field.
	 */
	String declaringType;
}
