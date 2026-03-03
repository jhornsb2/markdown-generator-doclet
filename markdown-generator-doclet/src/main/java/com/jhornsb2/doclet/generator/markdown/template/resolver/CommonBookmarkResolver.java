package com.jhornsb2.doclet.generator.markdown.template.resolver;

import com.jhornsb2.doclet.generator.markdown.elements.IDocComment;
import com.jhornsb2.doclet.generator.markdown.elements.IKind;
import com.jhornsb2.doclet.generator.markdown.elements.IModifiers;
import com.jhornsb2.doclet.generator.markdown.elements.IQualifiedName;
import com.jhornsb2.doclet.generator.markdown.elements.ISimpleName;
import com.jhornsb2.doclet.generator.markdown.elements.JavaModifier;
import com.jhornsb2.doclet.generator.markdown.template.IBookmarkResolver;
import com.jhornsb2.doclet.generator.markdown.template.TemplateRenderContext;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.NonNull;

/**
 * Resolves common bookmarks shared across template kinds.
 */
public class CommonBookmarkResolver implements IBookmarkResolver {

	private static final List<JavaModifier> KIND_MODIFIER_ORDER = List.of(
		JavaModifier.ABSTRACT,
		JavaModifier.SEALED,
		JavaModifier.NON_SEALED,
		JavaModifier.FINAL,
		JavaModifier.STATIC
	);

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
		final Set<JavaModifier> modifiers =
			elementData instanceof IModifiers data
				? data.getModifiers()
				: Set.of();
		final String visibility =
			elementData instanceof IModifiers
				? this.createVisibility(modifiers)
				: "";
		final String modifiersText = this.createModifiers(modifiers);
		final String kindWithModifiers = this.createKindWithModifiers(
			kind,
			modifiers
		);
		final String docComment =
			elementData instanceof IDocComment data ? data.getDocComment() : "";

		return Map.of(
			"common.simpleName",
			simpleName,
			"common.qualifiedName",
			qualifiedName,
			"common.kind",
			kind,
			"common.description",
			docComment,
			"common.visibility",
			visibility,
			"common.modifiers",
			modifiersText,
			"common.kindWithModifiers",
			kindWithModifiers
		);
	}

	private String createModifiers(final Set<JavaModifier> modifiers) {
		if (modifiers.isEmpty()) {
			return "";
		}
		return java.util.Arrays.stream(JavaModifier.values())
			.filter(modifiers::contains)
			.map(JavaModifier::getKeyword)
			.collect(Collectors.joining(" "));
	}

	private String createVisibility(final Set<JavaModifier> modifiers) {
		if (modifiers.contains(JavaModifier.PUBLIC)) {
			return JavaModifier.PUBLIC.getKeyword();
		}
		if (modifiers.contains(JavaModifier.PROTECTED)) {
			return JavaModifier.PROTECTED.getKeyword();
		}
		if (modifiers.contains(JavaModifier.PRIVATE)) {
			return JavaModifier.PRIVATE.getKeyword();
		}
		return "package";
	}

	private String createKindWithModifiers(
		final String kind,
		final Set<JavaModifier> modifiers
	) {
		if (kind.isBlank()) {
			return "";
		}
		final String modifierPrefix = KIND_MODIFIER_ORDER.stream()
			.filter(modifiers::contains)
			.map(JavaModifier::getKeyword)
			.collect(Collectors.joining(" "));
		if (modifierPrefix.isBlank()) {
			return kind;
		}
		return modifierPrefix + " " + kind;
	}
}
