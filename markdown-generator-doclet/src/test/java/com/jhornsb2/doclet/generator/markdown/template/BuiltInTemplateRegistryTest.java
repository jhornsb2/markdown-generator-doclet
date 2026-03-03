package com.jhornsb2.doclet.generator.markdown.template;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.jhornsb2.doclet.generator.markdown.elements.PackageData;
import org.junit.jupiter.api.Test;

class BuiltInTemplateRegistryTest {

	private static final TemplateRenderContext CONTEXT =
		TemplateRenderContext.builder()
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

	@Test
	void returnsTemplateForSupportedKinds() {
		BuiltInTemplateRegistry registry = new BuiltInTemplateRegistry();

		String projectTemplate = registry.getTemplate(TemplateKind.PROJECT, CONTEXT);
		String packageTemplate = registry.getTemplate(TemplateKind.PACKAGE, CONTEXT);
		String interfaceTemplate = registry.getTemplate(TemplateKind.INTERFACE, CONTEXT);
		String classTemplate = registry.getTemplate(TemplateKind.CLASS, CONTEXT);

		assertTrue(projectTemplate.contains("${project.name}"));
		assertTrue(packageTemplate.contains("${package.contents}"));
		assertTrue(interfaceTemplate.contains("${interface.methods}"));
		assertTrue(classTemplate.contains("${class.methods}"));
	}

	@Test
	void returnsEmptyTemplateForUnsupportedKinds() {
		BuiltInTemplateRegistry registry = new BuiltInTemplateRegistry();

		assertEquals("", registry.getTemplate(TemplateKind.MODULE, CONTEXT));
		assertEquals("", registry.getTemplate(TemplateKind.ANNOTATION, CONTEXT));
		assertEquals("", registry.getTemplate(TemplateKind.RECORD, CONTEXT));
		assertEquals("", registry.getTemplate(TemplateKind.ENUM, CONTEXT));
	}

}
