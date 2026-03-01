package com.jhornsb2.doclet.generator.markdown.elements;

import java.util.Collections;
import java.util.Optional;
import java.util.Set;
import lombok.Builder;
import lombok.Value;

/**
 * Data class representing a Java class element.
 */
@Value
@Builder
public class ClassData
	implements
		IElementData,
		ISimpleName,
		IQualifiedName,
		IKind,
		IDocComment,
		ISuperClass,
		ISuperInterfaces
{

	/**
	 * The simple name of the class (e.g., "MyClass").
	 */
	String simpleName;

	/**
	 * The fully qualified name of the class (e.g., "com.example.MyClass").
	 */
	String qualifiedName;

	/**
	 * The kind of the class (e.g. "class").
	 */
	String kind;

	/**
	 * The documentation comment of the class.
	 */
	String docComment;

	/**
	 * The fully qualified name of the superclass, if any.
	 */
	@Builder.Default
	Optional<String> superClass = Optional.empty();

	/**
	 * The {@link Set} of fully qualified names of interfaces that this class
	 * implements, if any.
	 */
	@Builder.Default
	Set<String> superInterfaces = Collections.emptySet();
}
