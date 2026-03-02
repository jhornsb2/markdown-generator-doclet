package com.jhornsb2.doclet.generator.markdown.elements;

import java.util.Set;
import lombok.Builder;
import lombok.Singular;
import lombok.Value;

/**
 * Data class representing the overall generated project documentation.
 */
@Value
@Builder
public class ProjectData
	implements IElementData, ISimpleName, IQualifiedName, IKind, IDocComment
{

	/**
	 * Display name for the documentation project.
	 */
	String simpleName;

	/**
	 * Qualified name for the documentation project.
	 */
	String qualifiedName;

	/**
	 * Kind of this data element.
	 */
	String kind;

	/**
	 * Description text for the project documentation.
	 */
	String docComment;

	/**
	 * Included module qualified names.
	 */
	@Singular
	Set<String> moduleNames;

	/**
	 * Included package qualified names.
	 */
	@Singular
	Set<String> packageNames;
}
