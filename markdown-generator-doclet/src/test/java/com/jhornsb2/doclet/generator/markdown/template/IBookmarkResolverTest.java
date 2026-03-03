package com.jhornsb2.doclet.generator.markdown.template;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.jhornsb2.doclet.generator.markdown.elements.PackageData;
import com.jhornsb2.doclet.generator.markdown.elements.ProjectData;
import com.jhornsb2.doclet.generator.markdown.template.resolver.ProjectBookmarkResolver;
import java.util.Set;
import org.junit.jupiter.api.Test;

class IBookmarkResolverTest {

	@Test
	void supportsAndResolvesMatchingProjectContext() {
		IBookmarkResolver resolver = new ProjectBookmarkResolver();
		TemplateRenderContext context = TemplateRenderContext.builder()
			.templateKind(TemplateKind.PROJECT)
			.elementData(
				ProjectData.builder()
					.name("Project")
					.description("Docs")
					.modules(Set.of("m1", "m2"))
					.packages(Set.of("p1"))
					.build()
			)
			.build();

		assertTrue(resolver.supports(context));
		assertEquals("2", resolver.resolve(context).get("project.moduleCount"));
	}

	@Test
	void doesNotSupportMismatchedContext() {
		IBookmarkResolver resolver = new ProjectBookmarkResolver();
		TemplateRenderContext context = TemplateRenderContext.builder()
			.templateKind(TemplateKind.PACKAGE)
			.elementData(
				PackageData.builder()
					.simpleName("pkg")
					.qualifiedName("com.example")
					.kind("package")
					.docComment("")
					.packageContents(Set.of())
					.build()
			)
			.build();

		assertFalse(resolver.supports(context));
	}
}
