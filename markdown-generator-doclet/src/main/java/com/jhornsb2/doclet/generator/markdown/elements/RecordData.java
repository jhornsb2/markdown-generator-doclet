package com.jhornsb2.doclet.generator.markdown.elements;

import java.util.Collections;
import java.util.Optional;
import java.util.Set;
import lombok.Builder;
import lombok.Value;

/**
 * Data class representing a Java record element.
 */
@Value
@Builder
public class RecordData
	implements
		IElementData,
		ISimpleName,
		IQualifiedName,
		IKind,
		IDocComment,
		IModifiers,
		ISuperClass,
		ISuperInterfaces
{

	/**
	 * The simple name of the record (e.g., "MyRecord").
	 */
	String simpleName;

	/**
	 * The fully qualified name of the record (e.g., "com.example.MyRecord").
	 */
	String qualifiedName;

	/**
	 * The kind of the record (e.g. "record").
	 */
	String kind;

	/**
	 * The documentation comment of the record.
	 */
	String docComment;

	/**
	 * The {@link Set} of declaration modifiers on the record.
	 */
	@Builder.Default
	Set<JavaModifier> modifiers = Collections.emptySet();

	/**
	 * The fully qualified name of the superclass, if any.
	 */
	@Builder.Default
	Optional<String> superClass = Optional.empty();

	/**
	 * The {@link Set} of fully qualified names of interfaces that this record
	 * implements, if any.
	 */
	@Builder.Default
	Set<String> superInterfaces = Collections.emptySet();
}
