package com.jhornsb2.doclet.generator.markdown.elements.factory;

import com.jhornsb2.doclet.generator.markdown.elements.InterfaceData;
import com.jhornsb2.doclet.generator.markdown.util.DocCommentUtil;
import com.sun.source.doctree.DocCommentTree;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeMirror;
import lombok.NonNull;
import lombok.Value;

/**
 * Creates cached {@link InterfaceData} instances from interface
 * {@link javax.lang.model.element.TypeElement} values.
 * <p>
 * This factory extracts naming, kind, Javadoc body text, and declared
 * super-interface metadata for template rendering.
 */
@Value
public class InterfaceDataFactory {

	/** Shared cache for element-data instances keyed by element identity. */
	ElementDataCache elementDataCache;
	/** Utility used to read parsed Javadoc comments for language model elements. */
	DocCommentUtil docCommentUtil;

	/**
	 * Returns cached or newly-created interface metadata.
	 *
	 * @param element interface element to transform.
	 * @return cached or computed {@link InterfaceData} representation.
	 */
	public InterfaceData create(@NonNull final Element element) {
		final String key = this.elementDataCache.keyFor(element);
		return (InterfaceData) this.elementDataCache.getOrCompute(key, () ->
			this.createUncached(element)
		);
	}

	/**
	 * Builds interface metadata without consulting the cache.
	 *
	 * @param element interface element to transform.
	 * @return new {@link InterfaceData} instance.
	 */
	InterfaceData createUncached(@NonNull final Element element) {
		final TypeElement typeElement = (TypeElement) element;
		final Set<String> superInterfaces = this.resolveSuperInterfaces(
			typeElement
		);

		return InterfaceData.builder()
			.simpleName(typeElement.getSimpleName().toString())
			.qualifiedName(typeElement.getQualifiedName().toString())
			.kind(typeElement.getKind().name().toLowerCase())
			.modifiers(ModifierMapper.toJavaModifiers(typeElement.getModifiers()))
			.docComment(
				this.docCommentUtil.getDocCommentTree(typeElement)
					.map(DocCommentTree::getFullBody)
					.map(List::toString)
					.orElse("")
			)
			.superInterfaces(superInterfaces)
			.build();
	}

	/**
	 * Resolves all directly-declared super-interface qualified names.
	 *
	 * @param typeElement interface element to inspect.
	 * @return immutable ordered set of declared super-interface names.
	 */
	private Set<String> resolveSuperInterfaces(final TypeElement typeElement) {
		final List<? extends TypeMirror> interfaceMirrors =
			typeElement.getInterfaces();
		if (interfaceMirrors == null || interfaceMirrors.isEmpty()) {
			return Collections.emptySet();
		}
		final Set<String> interfaces = interfaceMirrors
			.stream()
			.filter(DeclaredType.class::isInstance)
			.map(DeclaredType.class::cast)
			.map(DeclaredType::asElement)
			.filter(TypeElement.class::isInstance)
			.map(TypeElement.class::cast)
			.map(superTypeElement ->
				superTypeElement.getQualifiedName().toString()
			)
			.collect(Collectors.toCollection(LinkedHashSet::new));
		return Collections.unmodifiableSet(interfaces);
	}
}
