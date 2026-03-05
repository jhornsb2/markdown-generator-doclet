package com.jhornsb2.doclet.generator.markdown.options;

import com.jhornsb2.doclet.generator.markdown.logging.DocletLoggingLevel;
import java.nio.file.Path;
import java.util.Optional;
import lombok.NonNull;
import lombok.Value;

/**
 * Resolved and validated option values used by doclet runtime logic.
 */
@Value
public class ResolvedDocletOptions {

	@NonNull
	OutputPathLayout outputPathLayout;

	@NonNull
	Optional<Path> templateDirectoryPath;

	@NonNull
	DocletLoggingLevel logLevel;

	/**
	 * Indicates whether a validated template directory was provided.
	 *
	 * @return {@code true} when a template directory is present.
	 */
	public boolean hasTemplateDirectory() {
		return this.templateDirectoryPath.isPresent();
	}
}
