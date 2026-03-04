package com.jhornsb2.doclet.generator.markdown.elements;

import static org.junit.jupiter.api.Assertions.assertSame;

import com.jhornsb2.doclet.generator.markdown.template.TemplateKind;
import com.jhornsb2.doclet.generator.markdown.template.TemplateRenderContext;
import org.junit.jupiter.api.Test;

class IElementDataTest {

	@Test
	void canBeStoredAndRetrievedFromTemplateRenderContext() {
		IElementData elementData = ProjectData.builder()
			.name("Test Project")
			.description("Description")
			.modules(java.util.Set.of())
			.packages(java.util.Set.of())
			.build();

		TemplateRenderContext context = TemplateRenderContext.builder()
			.templateKind(TemplateKind.PROJECT)
			.elementData(elementData)
			.build();

		assertSame(elementData, context.getElementData());
	}
}
