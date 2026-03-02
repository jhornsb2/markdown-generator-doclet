package com.jhornsb2.doclet.generator.markdown.options;

import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.util.Optional;
import lombok.NonNull;

/**
 * Resolves and validates raw doclet options into typed runtime values.
 */
public class DocletOptionsResolver {

	/**
	 * Resolves and validates doclet options.
	 *
	 * @param docletOptions raw option values parsed from command-line input.
	 * @return validated, typed option values.
	 * @throws DocletOptionValidationException when any option value is invalid.
	 */
	public ResolvedDocletOptions resolve(
		@NonNull final DocletOptions docletOptions
	) {
		final OutputPathLayout outputPathLayout;
		try {
			outputPathLayout = docletOptions.getOutputPathLayout();
		} catch (IllegalArgumentException ex) {
			throw new DocletOptionValidationException(
				"Invalid -path-layout option: " +
				docletOptions.getPathLayout().getValue() +
				". Supported values are: " +
				OutputPathLayout.supportedOptionValues(),
				ex
			);
		}

		Optional<Path> templateDirectoryPath = Optional.empty();
		if (docletOptions.hasTemplateDirectory()) {
			final Path candidatePath;
			try {
				candidatePath = Path.of(docletOptions.getTemplateDirectory());
			} catch (InvalidPathException ex) {
				throw new DocletOptionValidationException(
					"Invalid -template-dir option: " +
						docletOptions.getTemplateDirectory(),
					ex
				);
			}

			if (!Files.isDirectory(candidatePath)) {
				throw new DocletOptionValidationException(
					"Invalid -template-dir option: " +
						candidatePath +
						" is not an existing directory"
				);
			}
			templateDirectoryPath = Optional.of(candidatePath);
		}

		return new ResolvedDocletOptions(outputPathLayout, templateDirectoryPath);
	}
}
