package com.jhornsb2.doclet.generator.markdown.elements;

import java.util.Set;
import lombok.Builder;
import lombok.Value;

/**
 * Data class representing a module element in the documentation.
 */
@Value
@Builder
public class ModuleData
	implements IElementData, ISimpleName, IQualifiedName, IKind, IDocComment
{

	/**
	 * The simple name of the module.
	 */
	String simpleName;

	/**
	 * The qualified name of the module.
	 */
	String qualifiedName;

	/**
	 * The kind of the module.
	 */
	String kind;

	/**
	 * The documentation comment of the module.
	 */
	String docComment;

	/**
	 * The fully qualified names of the module contents, including packages.
	 */
	Set<String> moduleContents;
}
