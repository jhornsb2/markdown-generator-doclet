package com.jhornsb2.doclet.generator.markdown.template;

import lombok.NonNull;

/**
 * Resolves markdown templates by kind and render context.
 * <p>
 * Registries may load built-in templates, filesystem overrides, or delegate to
 * chained fallback registries.
 */
public interface TemplateRegistry {
	/**
	 * Returns template text for the supplied template kind.
	 *
	 * @param templateKind kind of template to resolve.
	 * @param context render context that can influence template selection.
	 * @return template content used by a {@link TemplateRenderer}.
	 */
	String getTemplate(
		@NonNull TemplateKind templateKind,
		@NonNull TemplateRenderContext context
	);
}
