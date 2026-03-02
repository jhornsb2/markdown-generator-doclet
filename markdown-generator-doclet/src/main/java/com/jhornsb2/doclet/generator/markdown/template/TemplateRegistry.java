package com.jhornsb2.doclet.generator.markdown.template;

import lombok.NonNull;

/**
 * Registry for loading templates by kind.
 */
public interface TemplateRegistry {
	String getTemplate(@NonNull TemplateKind templateKind);
}
