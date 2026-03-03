package com.jhornsb2.doclet.generator.markdown.template.resolver;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.jhornsb2.doclet.generator.markdown.elements.PackageData;
import com.jhornsb2.doclet.generator.markdown.elements.ProjectData;
import com.jhornsb2.doclet.generator.markdown.template.TemplateKind;
import com.jhornsb2.doclet.generator.markdown.template.TemplateRenderContext;
import java.util.Map;
import java.util.Set;
import org.junit.jupiter.api.Test;

class ProjectBookmarkResolverTest {

	@Test
	void supportsOnlyProjectTemplateWithProjectData() {
		ProjectBookmarkResolver resolver = new ProjectBookmarkResolver();

		TemplateRenderContext supportedContext = TemplateRenderContext.builder()
			.templateKind(TemplateKind.PROJECT)
			.elementData(
				ProjectData.builder()
					.name("example")
					.kind("project")
					.description("")
					.modules(Set.of())
					.packages(Set.of())
					.build()
			)
			.build();
		TemplateRenderContext unsupportedContext =
			TemplateRenderContext.builder()
				.templateKind(TemplateKind.PROJECT)
				.elementData(
					PackageData.builder()
						.simpleName("example")
						.qualifiedName("com.example")
						.kind("package")
						.docComment("")
						.packageContents(Set.of())
						.build()
				)
				.build();

		assertTrue(resolver.supports(supportedContext));
		assertFalse(resolver.supports(unsupportedContext));
	}

	@Test
	void resolvesCountsAndLists() {
		ProjectBookmarkResolver resolver = new ProjectBookmarkResolver();
		TemplateRenderContext context = TemplateRenderContext.builder()
			.templateKind(TemplateKind.PROJECT)
			.elementData(
				ProjectData.builder()
					.name("example")
					.kind("project")
					.description("project description")
					.modules(Set.of("z.module", "a.module"))
					.packages(Set.of("com.zeta", "com.alpha"))
					.build()
			)
			.build();

		Map<String, String> bookmarks = resolver.resolve(context);

		assertEquals("example", bookmarks.get("project.name"));

		assertEquals(
			"project description",
			bookmarks.get("project.description")
		);
		assertEquals("2", bookmarks.get("project.moduleCount"));
		assertEquals("2", bookmarks.get("project.packageCount"));
		assertEquals(
			"- a.module\n- z.module",
			bookmarks.get("project.modules")
		);
		assertEquals(
			"- com.alpha\n- com.zeta",
			bookmarks.get("project.packages")
		);
	}
}
