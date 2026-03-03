package com.jhornsb2.doclet.generator.markdown.template.resolver;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.jhornsb2.doclet.generator.markdown.elements.ClassData;
import com.jhornsb2.doclet.generator.markdown.elements.JavaModifier;
import com.jhornsb2.doclet.generator.markdown.elements.ProjectData;
import com.jhornsb2.doclet.generator.markdown.template.TemplateKind;
import com.jhornsb2.doclet.generator.markdown.template.TemplateRenderContext;
import java.util.Map;
import java.util.Set;
import org.junit.jupiter.api.Test;

class CommonBookmarkResolverTest {

	@Test
	void resolvesKindWithModifiersWhenSupported() {
		CommonBookmarkResolver resolver = new CommonBookmarkResolver();
		TemplateRenderContext context = TemplateRenderContext.builder()
			.templateKind(TemplateKind.CLASS)
			.elementData(
				ClassData.builder()
					.simpleName("AbstractWidget")
					.qualifiedName("example.AbstractWidget")
					.kind("class")
					.docComment("docs")
					.modifiers(
						Set.of(
							JavaModifier.PUBLIC,
							JavaModifier.ABSTRACT,
							JavaModifier.SEALED
						)
					)
					.build()
			)
			.build();

		Map<String, String> bookmarks = resolver.resolve(context);

		assertEquals("class", bookmarks.get("common.kind"));
		assertEquals("public", bookmarks.get("common.visibility"));
		assertEquals(
			"public abstract sealed",
			bookmarks.get("common.modifiers")
		);
		assertEquals(
			"abstract sealed class",
			bookmarks.get("common.kindWithModifiers")
		);
	}

	@Test
	void resolvesKindWithModifiersAsKindWhenNoModifiersPresent() {
		CommonBookmarkResolver resolver = new CommonBookmarkResolver();
		TemplateRenderContext context = TemplateRenderContext.builder()
			.templateKind(TemplateKind.CLASS)
			.elementData(
				ClassData.builder()
					.simpleName("Widget")
					.qualifiedName("example.Widget")
					.kind("class")
					.docComment("docs")
					.build()
			)
			.build();

		Map<String, String> bookmarks = resolver.resolve(context);

		assertEquals("package", bookmarks.get("common.visibility"));
		assertTrue(bookmarks.get("common.modifiers").isEmpty());
		assertEquals("class", bookmarks.get("common.kindWithModifiers"));
	}

	@Test
	void resolvesKindWithModifiersAsEmptyWhenKindNotAvailable() {
		CommonBookmarkResolver resolver = new CommonBookmarkResolver();
		TemplateRenderContext context = TemplateRenderContext.builder()
			.templateKind(TemplateKind.PROJECT)
			.elementData(
				ProjectData.builder()
					.name("example")
					.kind("")
					.description("")
					.modules(Set.of())
					.packages(Set.of())
					.build()
			)
			.build();

		Map<String, String> bookmarks = resolver.resolve(context);

		assertTrue(bookmarks.get("common.visibility").isEmpty());
		assertTrue(bookmarks.get("common.modifiers").isEmpty());
		assertTrue(bookmarks.get("common.kindWithModifiers").isEmpty());
	}
}
