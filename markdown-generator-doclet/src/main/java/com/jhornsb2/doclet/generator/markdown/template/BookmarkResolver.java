package com.jhornsb2.doclet.generator.markdown.template;

import java.util.Map;
import lombok.NonNull;

/**
 * Resolves bookmark values for a render context.
 */
public interface BookmarkResolver {
	boolean supports(@NonNull TemplateRenderContext context);

	Map<String, String> resolve(@NonNull TemplateRenderContext context);
}
