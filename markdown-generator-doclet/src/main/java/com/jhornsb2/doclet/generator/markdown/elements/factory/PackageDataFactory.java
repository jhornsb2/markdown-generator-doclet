package com.jhornsb2.doclet.generator.markdown.elements.factory;

import com.jhornsb2.doclet.generator.markdown.elements.IElementData;
import com.jhornsb2.doclet.generator.markdown.elements.PackageData;
import com.jhornsb2.doclet.generator.markdown.util.DocCommentUtil;
import com.sun.source.doctree.DocCommentTree;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import lombok.NonNull;
import lombok.Value;

/**
 * Factory for creating {@link PackageData} instances from Java package
 * {@link Element} values.
 */
@Value
public class PackageDataFactory {

	/**
	 * Cache for {@link IElementData} instances to ensure consistent and
	 * efficient reuse across factories.
	 */
	final ElementDataCache elementDataCache;
	final DocCommentUtil docCommentUtil;

	/**
	 * Creates a {@link PackageData} instance for the given {@link Element},
	 * which is expected to be a package element. The method first checks the
	 * cache for an existing instance and returns it if found. If not found,
	 * it creates a new instance, stores it in the cache, and then returns it.
	 *
	 * @param element The {@link Element} representing the package for which to
	 *                create the {@link PackageData}. This element is expected to
	 *                be a {@link PackageElement}.
	 * @return A {@link PackageData} instance representing the package.
	 */
	public PackageData create(@NonNull final Element element) {
		final String key = this.elementDataCache.keyFor(element);
		return (PackageData) this.elementDataCache.getOrCompute(key, () ->
			this.createUncached(element)
		);
	}

	/**
	 * Creates a {@link PackageData} instance for the given {@link Element}
	 * without checking the cache. This method is intended to be called
	 * internally by the caching mechanism when a cache miss occurs.
	 *
	 * @param element The {@link Element} representing the package for which to
	 *                create the {@link PackageData}. This {@link Element} is
	 *                expected to be a {@link PackageElement}.
	 * @return A {@link PackageData} instance representing the package.
	 */
	PackageData createUncached(@NonNull final Element element) {
		final PackageElement packageElement = (PackageElement) element;
		final Set<String> packageContents = this.resolvePackageContents(
			packageElement
		);
		final String simpleName = packageElement.getSimpleName().toString();
		final String qualifiedName = packageElement
			.getQualifiedName()
			.toString();
		final String kind = packageElement.getKind().name().toLowerCase();
		final String docComment = this.docCommentUtil.getDocCommentTree(
				packageElement
			)
			.map(DocCommentTree::getFullBody)
			.map(List::toString)
			.orElse("");

		return PackageData.builder()
			.simpleName(simpleName)
			.qualifiedName(qualifiedName)
			.kind(kind)
			.docComment(docComment)
			.packageContents(packageContents)
			.build();
	}

	/**
	 * Resolves the contents of the given {@link PackageElement} and returns
	 * them as a {@link Set} of fully qualified names. If the package does not
	 * contain any supported elements, an empty {@link Set} is returned.
	 *
	 * @param packageElement The {@link PackageElement} for which to resolve the
	 *                       package contents.
	 * @return A {@link Set} of fully qualified names representing the package
	 *         contents. If no contents are found, an empty {@link Set} is
	 *         returned.
	 */
	private Set<String> resolvePackageContents(
		final PackageElement packageElement
	) {
		final List<? extends Element> enclosedElements =
			packageElement.getEnclosedElements();
		if (enclosedElements == null || enclosedElements.isEmpty()) {
			return Collections.emptySet();
		}
		final Set<String> contents = enclosedElements
			.stream()
			.filter(element -> this.isPackageContentKind(element.getKind()))
			.filter(TypeElement.class::isInstance)
			.map(TypeElement.class::cast)
			.map(typeElement -> typeElement.getQualifiedName().toString())
			.collect(Collectors.toCollection(LinkedHashSet::new));
		return Collections.unmodifiableSet(contents);
	}

	/**
	 * Determines whether the given {@link ElementKind} represents a type of
	 * element that should be included in the package contents. Supported kinds
	 * include classes, interfaces, enums, annotation types, and records.
	 *
	 * @param kind The {@link ElementKind} to check.
	 * @return {@code true} if the kind represents a supported package content
	 *         type; {@code false} otherwise.
	 */
	private boolean isPackageContentKind(final ElementKind kind) {
		return (
			kind == ElementKind.CLASS ||
			kind == ElementKind.INTERFACE ||
			kind == ElementKind.ENUM ||
			kind == ElementKind.ANNOTATION_TYPE ||
			kind == ElementKind.RECORD
		);
	}
}
