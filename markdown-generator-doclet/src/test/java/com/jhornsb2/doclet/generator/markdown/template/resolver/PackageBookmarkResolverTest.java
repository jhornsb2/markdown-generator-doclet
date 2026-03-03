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

class PackageBookmarkResolverTest {

	@Test
	void supportsOnlyPackageTemplateWithPackageData() {
		PackageBookmarkResolver resolver = new PackageBookmarkResolver();

		TemplateRenderContext supportedContext = TemplateRenderContext.builder()
			.templateKind(TemplateKind.PACKAGE)
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
		TemplateRenderContext unsupportedContext =
			TemplateRenderContext.builder()
				.templateKind(TemplateKind.PACKAGE)
				.elementData(
					ProjectData.builder()
						.name("example")
						.description("")
						.modules(Set.of())
						.packages(Set.of())
						.build()
				)
				.build();

		assertTrue(resolver.supports(supportedContext));
		assertFalse(resolver.supports(unsupportedContext));
	}

	@Test
	void resolvesContentsAndFlatContentsAsSortedList() {
		PackageBookmarkResolver resolver = new PackageBookmarkResolver();
		TemplateRenderContext context = TemplateRenderContext.builder()
			.templateKind(TemplateKind.PACKAGE)
			.elementData(
				PackageData.builder()
					.simpleName("example")
					.qualifiedName("com.example")
					.kind("package")
					.docComment("package docs")
					.packageContents(
						Set.of(
							"com.zeta.Beta",
							"com.alpha.Alpha",
							"com.example.Api",
							"com.example.nested.Internal"
						)
					)
					.build()
			)
			.build();

		Map<String, String> bookmarks = resolver.resolve(context);
		String expectedContents = """
			- com.alpha.Alpha
			- com.example.Api
			- com.example.nested.Internal
			- com.zeta.Beta""";
		String expectedTree = """
			- Api
			- com
			  - alpha
			    - Alpha
			  - zeta
			    - Beta
			- nested
			  - Internal""";

		assertEquals(expectedContents, bookmarks.get("package.contents"));
		assertEquals(expectedContents, bookmarks.get("package.contents.flat"));
		assertEquals(expectedTree, bookmarks.get("package.contents.tree"));
	}
}
