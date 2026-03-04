package com.jhornsb2.doclet.generator.markdown.template;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import com.jhornsb2.doclet.generator.markdown.elements.PackageData;
import org.junit.jupiter.api.Test;

class TemplateKindTest {

	@Test
	void valueOfResolvesNamedKinds() {
		assertEquals(TemplateKind.PROJECT, TemplateKind.valueOf("PROJECT"));
		assertEquals(TemplateKind.PACKAGE, TemplateKind.valueOf("PACKAGE"));
	}

	@Test
	void canDriveTemplateResolutionBehavior() {
		BuiltInTemplateRegistry registry = new BuiltInTemplateRegistry();
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

		assertFalse(
			registry.getTemplate(TemplateKind.PROJECT, context).isBlank()
		);
		assertEquals("", registry.getTemplate(TemplateKind.ENUM, context));
	}
}
