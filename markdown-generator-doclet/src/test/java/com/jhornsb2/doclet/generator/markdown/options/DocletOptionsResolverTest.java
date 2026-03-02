package com.jhornsb2.doclet.generator.markdown.options;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.file.Path;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

class DocletOptionsResolverTest {

	private final DocletOptionsResolver resolver = new DocletOptionsResolver();

	@Test
	void resolveUsesHierarchicalLayoutAndNoTemplateDirectoryByDefault() {
		DocletOptions options = this.createOptions("/tmp", "", "");

		ResolvedDocletOptions resolved = this.resolver.resolve(options);

		assertEquals(OutputPathLayout.HIERARCHICAL, resolved.getOutputPathLayout());
		assertFalse(resolved.hasTemplateDirectory());
		assertTrue(resolved.getTemplateDirectoryPath().isEmpty());
	}

	@Test
	void resolveRejectsInvalidPathLayout() {
		DocletOptions options = this.createOptions(
			"/tmp",
			"invalid-layout",
			""
		);

		DocletOptionValidationException ex = assertThrows(
			DocletOptionValidationException.class,
			() -> this.resolver.resolve(options)
		);

		assertTrue(ex.getMessage().contains("Invalid -path-layout option"));
		assertTrue(ex.getMessage().contains("Supported values are"));
	}

	@Test
	void resolveRejectsInvalidTemplateDirectoryPath() {
		String invalidPath = "bad" + '\0' + "path";
		DocletOptions options = this.createOptions(
			"/tmp",
			OutputPathLayout.HIERARCHICAL.getOptionValue(),
			invalidPath
		);

		DocletOptionValidationException ex = assertThrows(
			DocletOptionValidationException.class,
			() -> this.resolver.resolve(options)
		);

		assertTrue(ex.getMessage().contains("Invalid -template-dir option"));
	}

	@Test
	void resolveRejectsTemplateDirectoryThatDoesNotExist() {
		DocletOptions options = this.createOptions(
			"/tmp",
			OutputPathLayout.HIERARCHICAL.getOptionValue(),
			"missing-template-dir-" + System.nanoTime()
		);

		DocletOptionValidationException ex = assertThrows(
			DocletOptionValidationException.class,
			() -> this.resolver.resolve(options)
		);

		assertTrue(
			ex
				.getMessage()
				.contains("is not an existing directory")
		);
	}

	@Test
	void resolveAcceptsExistingTemplateDirectory(@TempDir final Path tempDir) {
		DocletOptions options = this.createOptions(
			"/tmp",
			"flat",
			tempDir.toString()
		);

		ResolvedDocletOptions resolved = this.resolver.resolve(options);

		assertEquals(OutputPathLayout.FLAT, resolved.getOutputPathLayout());
		assertTrue(resolved.hasTemplateDirectory());
		assertEquals(tempDir, resolved.getTemplateDirectoryPath().orElseThrow());
	}

	private DocletOptions createOptions(
		final String destinationDirectory,
		final String pathLayoutValue,
		final String templateDirectory
	) {
		GenericOption destinationOption = new GenericOption(
			"-d",
			"Destination directory for output files",
			destinationDirectory
		);
		GenericOption pathLayoutOption = new GenericOption(
			"-path-layout",
			"Output path layout for generated docs: hierarchical|h|flat|f",
			OutputPathLayout.HIERARCHICAL.getOptionValue()
		);
		if (!pathLayoutValue.isBlank()) {
			pathLayoutOption.process("-path-layout", List.of(pathLayoutValue));
		}

		GenericOption templateDirOption = new GenericOption(
			"-template-dir",
			"Directory containing custom templates (*.md)",
			""
		);
		if (!templateDirectory.isBlank()) {
			templateDirOption.process("-template-dir", List.of(templateDirectory));
		}

		return new DocletOptions(
			destinationOption,
			pathLayoutOption,
			templateDirOption
		);
	}
}
