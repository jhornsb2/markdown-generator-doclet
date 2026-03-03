package com.jhornsb2.doclet.generator.markdown.template;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.jhornsb2.doclet.generator.markdown.elements.PackageData;
import org.junit.jupiter.api.Test;

class TemplateRenderContextTest {

	@Test
	void defaultsToNoOutputFilepathAndNoAllElements() {
		TemplateRenderContext context = TemplateRenderContext.builder()
			.templateKind(TemplateKind.PACKAGE)
			.elementData(
				PackageData.builder()
					.simpleName("example")
					.qualifiedName("com.example")
					.kind("package")
					.docComment("")
					.build()
			)
			.build();

		assertFalse(context.hasOutputRelativeFilepath());
		assertTrue(context.getAllElements().isEmpty());
	}

	@Test
	void reportsOutputFilepathWhenConfigured() {
		TemplateRenderContext context = TemplateRenderContext.builder()
			.templateKind(TemplateKind.PACKAGE)
			.elementData(
				PackageData.builder()
					.simpleName("example")
					.qualifiedName("com.example")
					.kind("package")
					.docComment("")
					.build()
			)
			.outputRelativeFilepath("com/example/index.md")
			.build();

		assertTrue(context.hasOutputRelativeFilepath());
	}
}
