package com.jhornsb2.doclet.generator.markdown.template;

import com.jhornsb2.doclet.generator.markdown.elements.IElementData;
import java.util.List;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

/**
 * Context passed to template bookmark resolvers.
 */
@Value
@Builder
public class TemplateRenderContext {

	@NonNull
	TemplateKind templateKind;

	@NonNull
	IElementData elementData;

	@Builder.Default
	String outputRelativeFilepath = "";

	@Builder.Default
	List<IElementData> allElements = List.of();

	public boolean hasOutputRelativeFilepath() {
		return !this.outputRelativeFilepath.isBlank();
	}
}
