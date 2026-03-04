package com.jhornsb2.doclet.generator.markdown.template;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.jhornsb2.doclet.generator.markdown.elements.PackageData;
import org.junit.jupiter.api.Test;

class ITemplateRegistryTest {

	@Test
	void resolvesBuiltInTemplateForSupportedKind() {
		ITemplateRegistry registry = new BuiltInTemplateRegistry();
		TemplateRenderContext context = TemplateRenderContext.builder()
			.templateKind(TemplateKind.PACKAGE)
			.elementData(
				PackageData.builder()
					.simpleName("pkg")
					.qualifiedName("com.example")
					.kind("package")
					.docComment("")
					.packageContents(java.util.Set.of())
					.build()
			)
			.build();

		String template = registry.getTemplate(TemplateKind.PACKAGE, context);

		assertTrue(template.contains("${common.qualifiedName}"));
	}

	@Test
	void returnsEmptyTemplateForMissingBuiltInKind() {
		ITemplateRegistry registry = new BuiltInTemplateRegistry();
		TemplateRenderContext context = TemplateRenderContext.builder()
			.templateKind(TemplateKind.MODULE)
			.elementData(
				PackageData.builder()
					.simpleName("pkg")
					.qualifiedName("com.example")
					.kind("package")
					.docComment("")
					.packageContents(java.util.Set.of())
					.build()
			)
			.build();

		assertEquals("", registry.getTemplate(TemplateKind.MODULE, context));
		assertFalse(
			registry.getTemplate(TemplateKind.MODULE, context).contains("${")
		);
	}
}
