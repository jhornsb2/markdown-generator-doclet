package com.jhornsb2.doclet.generator.markdown.elements;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class AnnotationData
	implements IElementData, ISimpleName, IQualifiedName, IKind, IDocComment
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
}
