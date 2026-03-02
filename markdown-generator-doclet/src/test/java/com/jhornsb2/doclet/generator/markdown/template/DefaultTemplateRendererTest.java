package com.jhornsb2.doclet.generator.markdown.template;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.jhornsb2.doclet.generator.markdown.elements.PackageData;
import com.jhornsb2.doclet.generator.markdown.elements.ProjectData;
import com.jhornsb2.doclet.generator.markdown.template.resolver.CommonBookmarkResolver;
import com.jhornsb2.doclet.generator.markdown.template.resolver.PackageBookmarkResolver;
import com.jhornsb2.doclet.generator.markdown.template.resolver.ProjectBookmarkResolver;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.Test;

class DefaultTemplateRendererTest {

	@Test
	void rendersProjectAndPackageTemplatesWithResolvedBookmarks() {
		TemplateRenderer templateRenderer = new DefaultTemplateRenderer(
			new BuiltInTemplateRegistry(),
			List.of(
				new CommonBookmarkResolver(),
				new ProjectBookmarkResolver(),
				new PackageBookmarkResolver()
			)
		);

		ProjectData projectData = ProjectData.builder()
			.simpleName("Test Project")
			.qualifiedName("project")
			.kind("project")
			.docComment("Generated markdown documentation.")
			.moduleNames(Set.of("app.core"))
			.packageNames(Set.of("com.example", "com.example.api"))
			.build();

		String projectMarkdown = templateRenderer.render(
			TemplateKind.PROJECT,
			TemplateRenderContext.builder()
				.templateKind(TemplateKind.PROJECT)
				.elementData(projectData)
				.build()
		);

		assertTrue(projectMarkdown.contains("# Test Project"));
		assertTrue(projectMarkdown.contains("## Modules (1)"));
		assertTrue(projectMarkdown.contains("- app.core"));
		assertTrue(projectMarkdown.contains("## Packages (2)"));
		assertTrue(projectMarkdown.contains("- com.example"));

		PackageData packageData = PackageData.builder()
			.simpleName("example")
			.qualifiedName("com.example")
			.kind("package")
			.docComment("Package docs")
			.packageContents(Set.of("com.example.Api", "com.example.Impl"))
			.build();

		String packageMarkdown = templateRenderer.render(
			TemplateKind.PACKAGE,
			TemplateRenderContext.builder()
				.templateKind(TemplateKind.PACKAGE)
				.elementData(packageData)
				.build()
		);

		assertTrue(packageMarkdown.contains("# com.example"));
		assertTrue(packageMarkdown.contains("package"));
		assertTrue(packageMarkdown.contains("Package docs"));
		assertTrue(packageMarkdown.contains("- com.example.Api"));
	}

	@Test
	void unresolvedBookmarksAreRenderedAsEmptyStrings() {
		TemplateRegistry templateRegistry = templateKind ->
			"A ${known} B ${unknown}";
		TemplateRenderer templateRenderer = new DefaultTemplateRenderer(
			templateRegistry,
			List.of(
				new BookmarkResolver() {
					@Override
					public boolean supports(
						final TemplateRenderContext context
					) {
						return true;
					}

					@Override
					public java.util.Map<String, String> resolve(
						final TemplateRenderContext context
					) {
						return java.util.Map.of("known", "value");
					}
				}
			)
		);

		ProjectData projectData = ProjectData.builder()
			.simpleName("Test Project")
			.qualifiedName("project")
			.kind("project")
			.docComment("")
			.build();

		String rendered = templateRenderer.render(
			TemplateKind.PROJECT,
			TemplateRenderContext.builder()
				.templateKind(TemplateKind.PROJECT)
				.elementData(projectData)
				.build()
		);

		assertTrue(rendered.contains("A value B"));
		assertFalse(rendered.contains("${unknown}"));
	}
}
