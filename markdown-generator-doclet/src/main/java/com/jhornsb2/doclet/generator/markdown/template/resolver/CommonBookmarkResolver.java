package com.jhornsb2.doclet.generator.markdown.template.resolver;

import com.jhornsb2.doclet.generator.markdown.elements.IDocComment;
import com.jhornsb2.doclet.generator.markdown.elements.IKind;
import com.jhornsb2.doclet.generator.markdown.elements.IQualifiedName;
import com.jhornsb2.doclet.generator.markdown.elements.ISimpleName;
import com.jhornsb2.doclet.generator.markdown.template.IBookmarkResolver;
import com.jhornsb2.doclet.generator.markdown.template.TemplateRenderContext;
import java.util.Map;
import lombok.NonNull;

/**
 * Resolves common bookmarks shared across template kinds.
 */
public class CommonBookmarkResolver implements IBookmarkResolver {

	@Override
	public boolean supports(@NonNull final TemplateRenderContext context) {
		return true;
	}

	@Override
	public Map<String, String> resolve(
		@NonNull final TemplateRenderContext context
	) {
		final var elementData = context.getElementData();
		final String simpleName =
			elementData instanceof ISimpleName data ? data.getSimpleName() : "";
		final String qualifiedName =
			elementData instanceof IQualifiedName data
				? data.getQualifiedName()
				: "";
		final String kind =
			elementData instanceof IKind data ? data.getKind() : "";
		final String docComment =
			elementData instanceof IDocComment data ? data.getDocComment() : "";

		return Map.of(
			"common.simpleName",
			simpleName,
			"common.qualifiedName",
			qualifiedName,
			"common.kind",
			kind,
			"common.docComment",
			docComment
		);
	}
}
