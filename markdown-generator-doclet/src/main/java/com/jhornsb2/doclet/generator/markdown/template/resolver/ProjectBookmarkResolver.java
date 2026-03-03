package com.jhornsb2.doclet.generator.markdown.template.resolver;

import com.jhornsb2.doclet.generator.markdown.elements.ProjectData;
import com.jhornsb2.doclet.generator.markdown.template.IBookmarkResolver;
import com.jhornsb2.doclet.generator.markdown.template.TemplateKind;
import com.jhornsb2.doclet.generator.markdown.template.TemplateRenderContext;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.NonNull;

/**
 * Resolves project-specific bookmarks.
 */
public class ProjectBookmarkResolver implements IBookmarkResolver {

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
			.getModules()
			.stream()
			.sorted()
			.map(module -> "- " + module)
			.collect(Collectors.joining("\n"));
		final String packages = projectData
			.getPackages()
			.stream()
			.sorted()
			.map(packageName -> "- " + packageName)
			.collect(Collectors.joining("\n"));

		return Map.of(
			"project.description",
			projectData.getDescription(),
			"project.moduleCount",
			String.valueOf(projectData.getModules().size()),
			"project.modules",
			modules,
			"project.packageCount",
			String.valueOf(projectData.getPackages().size()),
			"project.packages",
			packages
		);
	}
}
