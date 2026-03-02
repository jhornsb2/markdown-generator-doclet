package com.jhornsb2.doclet.generator.markdown.template;

import com.jhornsb2.doclet.generator.markdown.elements.IElementData;
import java.util.List;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

/**
 * Immutable render context shared by template registries, renderers, and
 * bookmark resolvers.
 * <p>
 * The context contains the current element being rendered plus optional
 * cross-element data used for building links and navigation sections.
 */
@Value
@Builder
public class TemplateRenderContext {

	/** Template kind currently being rendered. */
	@NonNull
	TemplateKind templateKind;

	/** Primary element data backing the current template render pass. */
	@NonNull
	IElementData elementData;

	/**
	 * Relative output filepath for the current element.
	 * <p>
	 * This value is optional because some rendering flows only need markdown
	 * fragments and not file output metadata.
	 */
	@Builder.Default
	String outputRelativeFilepath = "";

	/**
	 * Optional collection of all extracted elements for cross-reference support.
	 */
	@Builder.Default
	List<IElementData> allElements = List.of();

	/**
	 * Indicates whether an output filepath has been provided.
	 *
	 * @return {@code true} when {@link #outputRelativeFilepath} is non-blank.
	 */
	public boolean hasOutputRelativeFilepath() {
		return !this.outputRelativeFilepath.isBlank();
	}
}
