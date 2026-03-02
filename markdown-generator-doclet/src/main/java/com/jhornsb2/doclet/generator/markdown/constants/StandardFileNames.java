package com.jhornsb2.doclet.generator.markdown.constants;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * Shared filenames used when writing generated markdown output.
 * <p>
 * Keeping these names centralized avoids accidental drift between file path
 * strategies and renderer output.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class StandardFileNames {

	/**
	 * Default project index markdown filename.
	 */
	public static final String INDEX_FILE_NAME = "index.md";
}
