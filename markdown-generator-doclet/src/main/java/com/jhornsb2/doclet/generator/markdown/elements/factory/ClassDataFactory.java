package com.jhornsb2.doclet.generator.markdown.elements.factory;

import com.jhornsb2.doclet.generator.markdown.elements.ClassData;
import com.jhornsb2.doclet.generator.markdown.elements.IElementData;
import com.jhornsb2.doclet.generator.markdown.util.CollectionUtil;
import com.jhornsb2.doclet.generator.markdown.util.DocCommentUtil;
import com.sun.source.doctree.DocCommentTree;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import lombok.NonNull;
import lombok.Value;

/**
 * Factory for creating {@link ClassData} instances from Java class
 * {@link Element} values.
 */
@Value
public class ClassDataFactory {

	/**
	 * Cache for {@link IElementData} instances to ensure consistent and
	 * efficient reuse across factories.
	 */
	final ElementDataCache elementDataCache;
	final DocCommentUtil docCommentUtil;

	/**
	 * Creates a {@link ClassData} instance for the given {@link Element},
	 * which is expected to be a class element. The method first checks the
	 * cache for an existing instance and returns it if found. If not found,
	 * it creates a new instance, stores it in the cache, and then returns it.
	 *
	 * @param element The {@link Element} representing the class for which to
	 *                create the {@link ClassData}. This element is expected to
	 *                be a {@link TypeElement}.
	 * @return A {@link ClassData} instance representing the class.
	 */
	public ClassData create(@NonNull final Element element) {
		final String key = this.elementDataCache.keyFor(element);
		return (ClassData) this.elementDataCache.getOrCompute(key, () ->
			this.createUncached(element)
		);
	}

	/**
	 * Creates a {@link ClassData} instance for the given {@link Element}
	 * without checking the cache. This method is intended to be called
	 * internally by the caching mechanism when a cache miss occurs.
	 *
	 * @param element The {@link Element} representing the class for which to
	 *                create the {@link ClassData}. This {@link Element} is
	 *                expected to be a {@link TypeElement}.
	 * @return A {@link ClassData} instance representing the class.
	 */
	ClassData createUncached(@NonNull final Element element) {
		final TypeElement typeElement = (TypeElement) element;
		final Optional<String> superClass = this.resolveSuperClass(typeElement);
		final Set<String> superInterfaces = this.resolveSuperInterfaces(
			typeElement
		);
		final String simpleName = typeElement.getSimpleName().toString();
		final String qualifiedName = typeElement.getQualifiedName().toString();
		final String kind = typeElement.getKind().name().toLowerCase();
		final String docComment = this.docCommentUtil.getDocCommentTree(
				typeElement
			)
			.map(DocCommentTree::getFullBody)
			.map(List::toString)
			.orElse("");

		// Create ClassData instance based on the provided TypeElement
		return ClassData.builder()
			.simpleName(simpleName)
			.qualifiedName(qualifiedName)
			.kind(kind)
			.docComment(docComment)
			.superClass(superClass)
			.superInterfaces(superInterfaces)
			.build();
	}

	/**
	 * Resolves the superclass of the given {@link TypeElement} and returns it
	 * as an {@link Optional} containing the fully qualified name of the
	 * superclass. If the class does not have a superclass or if the superclass
	 * cannot be resolved, an empty {@link Optional} is returned.
	 *
	 * @param typeElement The {@link TypeElement} for which to resolve the
	 *                    superclass.
	 * @return An {@link Optional} containing the fully qualified name of the
	 *         superclass if it exists and can be resolved, or an empty
	 *         {@link Optional} otherwise.
	 */
	private Optional<String> resolveSuperClass(final TypeElement typeElement) {
		final TypeMirror superclassMirror = typeElement.getSuperclass();
		if (superclassMirror.getKind() == TypeKind.NONE) {
			return Optional.empty();
		}
		if (!(superclassMirror instanceof DeclaredType declaredType)) {
			return Optional.empty();
		}
		final Element superElement = declaredType.asElement();
		if (!(superElement instanceof TypeElement)) {
			return Optional.empty();
		}
		return Optional.of(
			((TypeElement) superElement).getQualifiedName().toString()
		);
	}

	/**
	 * Resolves the superinterfaces of the given {@link TypeElement} and
	 * returns them as a {@link Set} of fully qualified interface names. If the
	 * class does not implement any interfaces or if the interfaces cannot be
	 * resolved, an empty {@link Set} is returned.
	 *
	 * @param typeElement The {@link TypeElement} for which to resolve the
	 *                    superinterfaces.
	 * @return A {@link Set} of fully qualified interface names representing the
	 *         superinterfaces implemented by the class. If no superinterfaces
	 *         are implemented or if they cannot be resolved, an empty
	 *         {@link Set} is returned.
	 */
	private Set<String> resolveSuperInterfaces(final TypeElement typeElement) {
		final List<? extends TypeMirror> interfaceMirrors =
			typeElement.getInterfaces();
		if (CollectionUtil.isEmpty(interfaceMirrors)) {
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
