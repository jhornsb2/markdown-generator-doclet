package com.jhornsb2.doclet.generator.markdown.template;

import lombok.NonNull;

/**
 * Renders markdown text for a template kind and render context.
 * <p>
 * Implementations combine a {@link TemplateRegistry} and one or more
 * bookmark-resolver collaborators to replace placeholders in template content.
 */
public interface TemplateRenderer {
	/**
	 * Renders markdown for a specific template kind.
	 *
	 * @param templateKind kind of template to render.
	 * @param context render-time data used for placeholder substitution.
	 * @return rendered markdown content ready to be written to disk.
	 */
	String render(
		@NonNull TemplateKind templateKind,
		@NonNull TemplateRenderContext context
	);
}
