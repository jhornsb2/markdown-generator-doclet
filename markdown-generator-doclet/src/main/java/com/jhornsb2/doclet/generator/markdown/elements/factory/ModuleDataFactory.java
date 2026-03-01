package com.jhornsb2.doclet.generator.markdown.elements.factory;

import com.jhornsb2.doclet.generator.markdown.elements.IElementData;
import com.jhornsb2.doclet.generator.markdown.elements.ModuleData;
import com.jhornsb2.doclet.generator.markdown.util.DocCommentUtil;
import com.sun.source.doctree.DocCommentTree;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ModuleElement;
import javax.lang.model.element.PackageElement;
import lombok.NonNull;
import lombok.Value;

/**
 * Factory for creating {@link ModuleData} instances from Java module
 * {@link Element} values.
 */
@Value
public class ModuleDataFactory {

	/**
	 * Cache for {@link IElementData} instances to ensure consistent and
	 * efficient reuse across factories.
	 */
	final ElementDataCache elementDataCache;

	/**
	 * Creates a {@link ModuleData} instance for the given {@link Element},
	 * which is expected to be a module element. The method first checks the
	 * cache for an existing instance and returns it if found. If not found, it
	 * creates a new instance, stores it in the cache, and then returns it.
	 *
	 * @param element The {@link Element} representing the module for which to
	 *                create the {@link ModuleData}. This element is expected to
	 *                be a {@link ModuleElement}.
	 * @return A {@link ModuleData} instance representing the module.
	 */
	public ModuleData create(@NonNull final Element element) {
		final String key = this.elementDataCache.keyFor(element);
		return (ModuleData) this.elementDataCache.getOrCompute(key, () ->
			this.createUncached(element)
		);
	}

	/**
	 * Creates a {@link ModuleData} instance for the given {@link Element}
	 * without checking the cache. This method is intended to be called
	 * internally by the caching mechanism when a cache miss occurs.
	 *
	 * @param element The {@link Element} representing the module for which to
	 *                create the {@link ModuleData}. This {@link Element} is
	 *                expected to be a {@link ModuleElement}.
	 * @return A {@link ModuleData} instance representing the module.
	 */
	ModuleData createUncached(@NonNull final Element element) {
		final ModuleElement moduleElement = (ModuleElement) element;
		final Set<String> moduleContents =
			this.resolveModuleContents(moduleElement);
		final String simpleName = moduleElement.getSimpleName().toString();
		final String qualifiedName = moduleElement.getQualifiedName().toString();
		final String kind = moduleElement.getKind().name().toLowerCase();
		final String docComment = DocCommentUtil.getDocCommentTree(moduleElement)
			.map(DocCommentTree::getFullBody)
			.map(List::toString)
			.orElse("");

		return ModuleData.builder()
			.simpleName(simpleName)
			.qualifiedName(qualifiedName)
			.kind(kind)
			.docComment(docComment)
			.moduleContents(moduleContents)
			.build();
	}

	/**
	 * Resolves the contents of the given {@link ModuleElement} and returns
	 * them as a {@link Set} of fully qualified package names. If the module does
	 * not contain any supported elements, an empty {@link Set} is returned.
	 *
	 * @param moduleElement The {@link ModuleElement} for which to resolve the
	 *                      module contents.
	 * @return A {@link Set} of fully qualified package names representing the
	 *         module contents. If no contents are found, an empty {@link Set} is
	 *         returned.
	 */
	private Set<String> resolveModuleContents(
		final ModuleElement moduleElement
	) {
		final List<? extends Element> enclosedElements =
			moduleElement.getEnclosedElements();
		if (enclosedElements == null || enclosedElements.isEmpty()) {
			return Collections.emptySet();
		}
		final Set<String> contents = enclosedElements
			.stream()
			.filter(element -> element.getKind() == ElementKind.PACKAGE)
			.filter(PackageElement.class::isInstance)
			.map(PackageElement.class::cast)
			.map(packageElement ->
				packageElement.getQualifiedName().toString()
			)
			.collect(Collectors.toCollection(LinkedHashSet::new));
		return Collections.unmodifiableSet(contents);
	}
}
