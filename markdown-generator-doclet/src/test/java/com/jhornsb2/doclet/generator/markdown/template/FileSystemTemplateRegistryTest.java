package com.jhornsb2.doclet.generator.markdown.template;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

class FileSystemTemplateRegistryTest {

	private static final TemplateRenderContext EMPTY_CONTEXT =
		TemplateRenderContext.builder()
			.templateKind(TemplateKind.PACKAGE)
			.elementData(
				com.jhornsb2.doclet.generator.markdown.elements.PackageData.builder()
					.simpleName("example")
					.qualifiedName("com.example")
					.kind("package")
					.docComment("")
					.build()
			)
			.build();

	@Test
	void returnsCustomTemplateWhenTemplateFileExists(@TempDir Path tempDir)
		throws IOException {
		Files.writeString(
			tempDir.resolve("project.md"),
			"# Custom Project Template",
			StandardCharsets.UTF_8
		);
		ITemplateRegistry fallbackTemplateRegistry = (templateKind, context) ->
			"fallback";
		ITemplateRegistry templateRegistry = new FileSystemTemplateRegistry(
			tempDir,
			fallbackTemplateRegistry
		);

		String template = templateRegistry.getTemplate(
			TemplateKind.PROJECT,
			EMPTY_CONTEXT
		);

		assertEquals("# Custom Project Template", template);
	}

	@Test
	void fallsBackToBuiltInWhenCustomTemplateFileIsMissing(
		@TempDir Path tempDir
	) {
		ITemplateRegistry fallbackTemplateRegistry = (templateKind, context) ->
			"fallback";
		ITemplateRegistry templateRegistry = new FileSystemTemplateRegistry(
			tempDir,
			fallbackTemplateRegistry
		);

		String template = templateRegistry.getTemplate(
			TemplateKind.PACKAGE,
			EMPTY_CONTEXT
		);

		assertEquals("fallback", template);
	}

	@Test
	void returnsSpecificTemplateWhenOutputRelativePathTemplateExists(
		@TempDir Path tempDir
	) throws IOException {
		Path specificTemplatePath = tempDir.resolve("com/example/README.md");
		Files.createDirectories(specificTemplatePath.getParent());
		Files.writeString(
			specificTemplatePath,
			"# Specific Template",
			StandardCharsets.UTF_8
		);
		Files.writeString(
			tempDir.resolve("package.md"),
			"# Kind Template",
			StandardCharsets.UTF_8
		);
		ITemplateRegistry fallbackTemplateRegistry = (templateKind, context) ->
			"fallback";
		ITemplateRegistry templateRegistry = new FileSystemTemplateRegistry(
			tempDir,
			fallbackTemplateRegistry
		);
		TemplateRenderContext context = TemplateRenderContext.builder()
			.templateKind(TemplateKind.PACKAGE)
			.elementData(EMPTY_CONTEXT.getElementData())
			.outputRelativeFilepath("com/example/README.md")
			.build();

		String template = templateRegistry.getTemplate(
			TemplateKind.PACKAGE,
			context
		);

		assertEquals("# Specific Template", template);
	}

	@Test
	void returnsKindTemplateWhenSpecificTemplateIsMissing(@TempDir Path tempDir)
		throws IOException {
		Files.writeString(
			tempDir.resolve("package.md"),
			"# Kind Template",
			StandardCharsets.UTF_8
		);
		ITemplateRegistry fallbackTemplateRegistry = (templateKind, context) ->
			"fallback";
		ITemplateRegistry templateRegistry = new FileSystemTemplateRegistry(
			tempDir,
			fallbackTemplateRegistry
		);
		TemplateRenderContext context = TemplateRenderContext.builder()
			.templateKind(TemplateKind.PACKAGE)
			.elementData(EMPTY_CONTEXT.getElementData())
			.outputRelativeFilepath("com/example/README.md")
			.build();

		String template = templateRegistry.getTemplate(
			TemplateKind.PACKAGE,
			context
		);

		assertEquals("# Kind Template", template);
	}
}
