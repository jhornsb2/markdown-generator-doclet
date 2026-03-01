package com.jhornsb2.doclet.generator.markdown.elements;

import java.util.Set;
import lombok.Builder;
import lombok.Value;

/**
 * Data class representing a package element in the documentation.
 */
@Value
@Builder
public class PackageData
	implements IElementData, ISimpleName, IQualifiedName, IKind, IDocComment
{

	/**
	 * The simple name of the package.
	 */
	String simpleName;

	/**
	 * The qualified name of the package.
	 */
	String qualifiedName;

	/**
	 * The kind of the package.
	 */
	String kind;

	/**
	 * The documentation comment of the package.
	 */
	String docComment;

	/**
	 * The fully qualified names of package contents, including classes,
	 * interfaces, enums, and annotations.
	 */
	Set<String> packageContents;
}
