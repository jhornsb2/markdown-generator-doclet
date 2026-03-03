package com.jhornsb2.doclet.generator.markdown.elements.factory;

import com.jhornsb2.doclet.generator.markdown.elements.AnnotationData;
import com.jhornsb2.doclet.generator.markdown.elements.IElementData;
import com.jhornsb2.doclet.generator.markdown.util.DocCommentUtil;
import com.sun.source.doctree.DocCommentTree;
import java.util.List;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import lombok.NonNull;
import lombok.Value;

/**
 * Factory for creating {@link AnnotationData} instances from Java annotation
 * {@link Element} values.
 */
@Value
public class AnnotationDataFactory {

	/**
	 * Cache for {@link IElementData} instances to ensure consistent and
	 * efficient reuse across factories.
	 */
	final ElementDataCache elementDataCache;
	final DocCommentUtil docCommentUtil;

	/**
	 * Creates an {@link AnnotationData} instance for the given {@link Element},
	 * which is expected to be an annotation type element. The method first
	 * checks the cache for an existing instance and returns it if found. If not
	 * found, it creates a new instance, stores it in the cache, and then
	 * returns it.
	 *
	 * @param element The {@link Element} representing the annotation for which
	 *                to create the {@link AnnotationData}. This element is
	 *                expected to be a {@link TypeElement}.
	 * @return An {@link AnnotationData} instance representing the annotation.
	 */
	public AnnotationData create(@NonNull final Element element) {
		final String key = this.elementDataCache.keyFor(element);
		return (AnnotationData) this.elementDataCache.getOrCompute(key, () ->
			this.createUncached(element)
		);
	}

	/**
	 * Creates an {@link AnnotationData} instance for the given {@link Element}
	 * without checking the cache. This method is intended to be called
	 * internally by the caching mechanism when a cache miss occurs.
	 *
	 * @param element The {@link Element} representing the annotation for which
	 *                to create the {@link AnnotationData}. This {@link Element}
	 *                is expected to be a {@link TypeElement}.
	 * @return An {@link AnnotationData} instance representing the annotation.
	 */
	AnnotationData createUncached(@NonNull final Element element) {
		final TypeElement typeElement = (TypeElement) element;

		return AnnotationData.builder()
			.simpleName(typeElement.getSimpleName().toString())
			.qualifiedName(typeElement.getQualifiedName().toString())
			.kind(typeElement.getKind().name().toLowerCase())
			.modifiers(
				ModifierMapper.toJavaModifiers(typeElement.getModifiers())
			)
			.docComment(
				this.docCommentUtil.getDocCommentTree(typeElement)
					.map(DocCommentTree::getFullBody)
					.map(List::toString)
					.orElse("")
			)
			.build();
	}
}
