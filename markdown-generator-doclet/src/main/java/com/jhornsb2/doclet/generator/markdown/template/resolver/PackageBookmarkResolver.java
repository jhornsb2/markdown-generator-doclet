package com.jhornsb2.doclet.generator.markdown.template.resolver;

import com.jhornsb2.doclet.generator.markdown.elements.PackageData;
import com.jhornsb2.doclet.generator.markdown.template.IBookmarkResolver;
import com.jhornsb2.doclet.generator.markdown.template.TemplateKind;
import com.jhornsb2.doclet.generator.markdown.template.TemplateRenderContext;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.NonNull;

/**
 * Resolves package-specific bookmarks.
 */
public class PackageBookmarkResolver implements IBookmarkResolver {

	@Override
	public boolean supports(@NonNull final TemplateRenderContext context) {
		return (
			context.getTemplateKind() == TemplateKind.PACKAGE &&
			context.getElementData() instanceof PackageData
		);
	}

	@Override
	public Map<String, String> resolve(
		@NonNull final TemplateRenderContext context
	) {
		final PackageData packageData = (PackageData) context.getElementData();
		final String packageContents = packageData
			.getPackageContents()
			.stream()
			.sorted()
			.map(content -> "- " + content)
			.collect(Collectors.joining("\n"));
		return Map.of("package.contents", packageContents);
	}
}
