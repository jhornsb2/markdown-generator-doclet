package com.jhornsb2.doclet.generator.markdown.options;

import com.jhornsb2.doclet.generator.markdown.filepath.OutputFilepathStrategy;
import com.jhornsb2.doclet.generator.markdown.filepath.OutputFilepathStrategyResolver;
import lombok.NonNull;
import lombok.Value;

/**
 * Immutable, typed view of parsed doclet command-line options.
 * <p>
 * Instances are created from low-level {@link GenericOption} values exposed by
 * {@link jdk.javadoc.doclet.Doclet.Option}, then used by generator code to
 * obtain validated, domain-specific configuration.
 */
@Value
public class DocletOptions {

	/** Option that captures the output destination directory. */
	@NonNull
	GenericOption destinationDir;

	/** Option that captures the output path layout strategy selector. */
	@NonNull
	GenericOption pathLayout;

	/** Option that captures an optional custom template directory path. */
	@NonNull
	GenericOption templateDir;

	/**
	 * Returns the configured output destination directory.
	 *
	 * @return the destination directory path string.
	 */
	public String getDestinationDirectory() {
		return this.destinationDir.getValue();
	}

	/**
	 * Resolves the selected output path layout from option text.
	 *
	 * @return the parsed {@link OutputPathLayout} value.
	 * @throws IllegalArgumentException when the option value is not recognized.
	 */
	public OutputPathLayout getOutputPathLayout() {
		return OutputPathLayout.fromOptionValue(this.pathLayout.getValue());
	}

	/**
	 * Resolves the filepath strategy that corresponds to the selected layout.
	 *
	 * @return the output filepath strategy implementation to use for generation.
	 */
	public OutputFilepathStrategy getOutputFilepathStrategy() {
		return OutputFilepathStrategyResolver.resolve(
			this.getOutputPathLayout()
		);
	}

	/**
	 * Returns the configured custom template directory.
	 *
	 * @return the template directory path, or blank when not configured.
	 */
	public String getTemplateDirectory() {
		return this.templateDir.getValue();
	}

	/**
	 * Indicates whether a custom template directory has been provided.
	 *
	 * @return {@code true} when a non-blank template directory is configured.
	 */
	public boolean hasTemplateDirectory() {
		return !this.getTemplateDirectory().isBlank();
	}
}
