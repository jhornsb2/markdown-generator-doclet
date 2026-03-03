package com.jhornsb2.doclet.generator.markdown.elements;

import java.util.Collections;
import java.util.Optional;
import java.util.Set;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class EnumData
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
	 * The simple name of the enum (e.g., "MyEnum").
	 */
	String simpleName;

	/**
	 * The fully qualified name of the enum (e.g., "com.example.MyEnum").
	 */
	String qualifiedName;

	/**
	 * The kind of the enum (e.g., "enum").
	 */
	String kind;

	/**
	 * The documentation comment of the enum.
	 */
	String docComment;

	/**
	 * The {@link Set} of declaration modifiers on the enum.
	 */
	@Builder.Default
	Set<JavaModifier> modifiers = Collections.emptySet();

	/**
	 * The fully qualified name of the superclass, if any.
	 */
	@Builder.Default
	Optional<String> superClass = Optional.empty();

	/**
	 * The {@link Set} of fully qualified names of interfaces that this enum
	 * implements, if any.
	 */
	@Builder.Default
	Set<String> superInterfaces = Collections.emptySet();
}
