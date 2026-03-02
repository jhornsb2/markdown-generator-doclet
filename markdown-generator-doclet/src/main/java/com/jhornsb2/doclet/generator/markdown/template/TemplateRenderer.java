package com.jhornsb2.doclet.generator.markdown.template;

import lombok.NonNull;

/**
 * Renders markdown from a template kind and render context.
 */
public interface TemplateRenderer {
	String render(
		@NonNull TemplateKind templateKind,
		@NonNull TemplateRenderContext context
	);
}
