package com.jhornsb2.doclet.generator.markdown.template;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Locale;
import lombok.NonNull;
import lombok.Value;

/**
 * Template registry that reads template files from a user-provided directory.
 * Missing templates are resolved from a fallback registry.
 */
@Value
public class FileSystemTemplateRegistry implements TemplateRegistry {

	@NonNull
	Path templateDirectory;

	@NonNull
	TemplateRegistry fallbackTemplateRegistry;

	@Override
	public String getTemplate(
		@NonNull final TemplateKind templateKind,
		@NonNull final TemplateRenderContext context
	) {
		final Path specificTemplatePath = this.resolveSpecificTemplatePath(
			context
		);
		if (specificTemplatePath != null) {
			return this.readTemplate(specificTemplatePath);
		}

		final Path kindTemplatePath = this.templateDirectory.resolve(
			templateKind.name().toLowerCase(Locale.ROOT) + ".md"
		);
		if (Files.isRegularFile(kindTemplatePath)) {
			return this.readTemplate(kindTemplatePath);
		}

		return this.fallbackTemplateRegistry.getTemplate(templateKind, context);
	}

	private Path resolveSpecificTemplatePath(
		final TemplateRenderContext context
	) {
		if (!context.hasOutputRelativeFilepath()) {
			return null;
		}

		final Path templateDirectoryPath =
			this.templateDirectory.toAbsolutePath().normalize();
		final Path candidatePath = templateDirectoryPath
			.resolve(context.getOutputRelativeFilepath())
			.normalize();
		if (!candidatePath.startsWith(templateDirectoryPath)) {
			return null;
		}

		if (!Files.isRegularFile(candidatePath)) {
			return null;
		}

		return candidatePath;
	}

	private String readTemplate(final Path templatePath) {
		try {
			return Files.readString(templatePath, StandardCharsets.UTF_8);
		} catch (IOException ex) {
			throw new IllegalStateException(
				"Failed to read template file: " + templatePath,
				ex
			);
		}
	}
}
