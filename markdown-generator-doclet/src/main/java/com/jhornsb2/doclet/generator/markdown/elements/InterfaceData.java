package com.jhornsb2.doclet.generator.markdown.elements;

import java.util.Collections;
import java.util.Set;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class InterfaceData
	implements
		IElementData,
		ISimpleName,
		IQualifiedName,
		IKind,
		IDocComment,
		ISuperInterfaces
{

	/**
	 * The simple name of the interface (e.g., "MyInterface").
	 */
	String simpleName;

	/**
	 * The fully qualified name of the interface (e.g., "com.example.MyInterface").
	 */
	String qualifiedName;

	/**
	 * The kind of the interface (e.g., "interface").
	 */
	String kind;

	/**
	 * The documentation comment of the interface.
	 */
	String docComment;

	/**
	 * The {@link Set} of fully qualified names of interfaces that this
	 * interface extends, if any.
	 */
	@Builder.Default
	Set<String> superInterfaces = Collections.emptySet();
}
