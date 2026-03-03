package com.jhornsb2.doclet.generator.markdown.elements;

import java.util.Set;
import lombok.Builder;
import lombok.Value;

/**
 * Data class representing project-level documentation metadata.
 */
@Value
@Builder
public class ProjectData implements IElementData {

	/**
	 * The project name.
	 */
	String name;

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
