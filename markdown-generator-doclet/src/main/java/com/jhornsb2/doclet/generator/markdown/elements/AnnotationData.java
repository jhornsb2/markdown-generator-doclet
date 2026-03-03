package com.jhornsb2.doclet.generator.markdown.elements;

import java.util.Collections;
import java.util.Set;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class AnnotationData
	implements
		IElementData,
		ISimpleName,
		IQualifiedName,
		IKind,
		IDocComment,
		IModifiers
{

	/**
	 * The simple name of the annotation (e.g., "MyAnnotation").
	 */
	String simpleName;

	/**
	 * The fully qualified name of the annotation (e.g., "com.example.MyAnnotation").
	 */
	String qualifiedName;

	/**
	 * The kind of the annotation (e.g., "annotation").
	 */
	String kind;

	/**
	 * The documentation comment of the annotation.
	 */
	String docComment;

	/**
	 * The {@link Set} of declaration modifiers on the annotation type.
	 */
	@Builder.Default
	Set<JavaModifier> modifiers = Collections.emptySet();
}
