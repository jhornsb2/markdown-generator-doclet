package com.jhornsb2.doclet.generator.markdown.template.resolver;

import com.jhornsb2.doclet.generator.markdown.elements.ProjectData;
import com.jhornsb2.doclet.generator.markdown.template.BookmarkResolver;
import com.jhornsb2.doclet.generator.markdown.template.TemplateKind;
import com.jhornsb2.doclet.generator.markdown.template.TemplateRenderContext;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.NonNull;

/**
 * Resolves project-level bookmarks.
 */
public class ProjectBookmarkResolver implements BookmarkResolver {

	@Override
	public boolean supports(@NonNull final TemplateRenderContext context) {
		return (
			context.getTemplateKind() == TemplateKind.PROJECT &&
			context.getElementData() instanceof ProjectData
		);
	}

	@Override
	public Map<String, String> resolve(
		@NonNull final TemplateRenderContext context
	) {
		final ProjectData projectData = (ProjectData) context.getElementData();
		final String modules = projectData
			.getModuleNames()
			.stream()
			.sorted()
			.map(moduleName -> "- " + moduleName)
			.collect(Collectors.joining("\n"));
		final String packages = projectData
			.getPackageNames()
			.stream()
			.sorted()
			.map(packageName -> "- " + packageName)
			.collect(Collectors.joining("\n"));

		return Map.of(
			"project.moduleCount",
			String.valueOf(projectData.getModuleNames().size()),
			"project.modules",
			modules,
			"project.packageCount",
			String.valueOf(projectData.getPackageNames().size()),
			"project.packages",
			packages
		);
	}
}
