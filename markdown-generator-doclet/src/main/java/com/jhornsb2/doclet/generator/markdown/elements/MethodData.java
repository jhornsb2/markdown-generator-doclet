package com.jhornsb2.doclet.generator.markdown.elements;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import lombok.Builder;
import lombok.Value;

/**
 * Data class representing a Java method element.
 */
@Value
@Builder
public class MethodData
	implements
		IElementData,
		ISimpleName,
		IQualifiedName,
		IKind,
		IDocComment,
		IModifiers,
		IReturnType,
		IDeclaringType,
		IParameters
{

	/**
	 * The simple name of the method (e.g., "myMethod").
	 */
	String simpleName;

	/**
	 * The fully qualified name of the method (e.g.,
	 * "com.example.MyClass.myMethod").
	 */
	String qualifiedName;

	/**
	 * The kind of the element (e.g., "method").
	 */
	String kind;

	/**
	 * The Javadoc comment associated with the method.
	 */
	String docComment;

	/**
	 * The {@link Set} of declaration modifiers on the method.
	 */
	@Builder.Default
	Set<JavaModifier> modifiers = Collections.emptySet();

	/**
	 * The fully qualified name of the return type of the method.
	 */
	String returnType;

	/**
	 * The fully qualified name of the declaring type of the method.
	 */
	String declaringType;

	/**
	 * The {@link List} of {@link VariableData} for the parameters of the
	 * method.
	 */
	@Builder.Default
	List<VariableData> parameters = Collections.emptyList();
}
