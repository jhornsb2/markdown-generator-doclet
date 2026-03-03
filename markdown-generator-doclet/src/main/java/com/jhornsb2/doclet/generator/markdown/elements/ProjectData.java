package com.jhornsb2.doclet.generator.markdown.elements;

import java.util.Set;
import lombok.Builder;
import lombok.Value;

/**
 * Data class representing project-level documentation metadata.
 */
@Value
@Builder
public class ProjectData implements IElementData, ISimpleName, IKind {

	/**
	 * The project name.
	 */
	String simpleName;

	/**
	 * The kind represented by this data.
	 */
	String kind;

	/**
	 * The project description.
	 */
	String description;

	/**
	 * Fully qualified module names in the project.
	 */
	Set<String> modules;

	/**
	 * Fully qualified package names in the project.
	 */
	Set<String> packages;
}
