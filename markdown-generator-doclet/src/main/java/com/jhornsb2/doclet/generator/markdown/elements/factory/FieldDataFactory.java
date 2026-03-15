package com.jhornsb2.doclet.generator.markdown.elements.factory;

import com.jhornsb2.doclet.generator.markdown.elements.FieldData;
import com.jhornsb2.doclet.generator.markdown.elements.IElementData;
import com.jhornsb2.doclet.generator.markdown.naming.QualifiedNameResolver;
import com.jhornsb2.doclet.generator.markdown.util.DocCommentUtil;
import com.sun.source.doctree.DocCommentTree;
import java.util.List;
import javax.lang.model.element.Element;
import javax.lang.model.element.VariableElement;
import lombok.NonNull;
import lombok.Value;

/**
 * Factory for creating {@link FieldData} instances from Java field
 * {@link Element} values.
 */
@Value
public class FieldDataFactory {

	/**
	 * Cache for {@link IElementData} instances to ensure consistent and
	 * efficient reuse across factories.
	 */
	final ElementDataCache elementDataCache;
	final DocCommentUtil docCommentUtil;

	/**
	 * Creates a {@link FieldData} instance for the given {@link Element},
	 * which is expected to be a field element. The method first checks the
	 * cache for an existing instance and returns it if found. If not found,
	 * it creates a new instance, stores it in the cache, and then returns it.
	 *
	 * @param element The {@link Element} representing the field for which to
	 *                create the {@link FieldData}. This element is expected to
	 *                be a {@link VariableElement}.
	 * @return A {@link FieldData} instance representing the field.
	 */
	public FieldData create(@NonNull final Element element) {
		final String key = this.elementDataCache.keyFor(element);
		return (FieldData) this.elementDataCache.getOrCompute(key, () ->
			this.createUncached(element)
		);
	}

	/**
	 * Creates a {@link FieldData} instance for the given {@link Element}
	 * without checking the cache. This method is intended to be called
	 * internally by the caching mechanism when a cache miss occurs.
	 *
	 * @param element The {@link Element} representing the field for which to
	 *                create the {@link FieldData}. This {@link Element} is
	 *                expected to be a {@link VariableElement}.
	 * @return A {@link FieldData} instance representing the field.
	 */
	FieldData createUncached(@NonNull final Element element) {
		final VariableElement variableElement = (VariableElement) element;
		final String simpleName = variableElement.getSimpleName().toString();
		final String qualifiedName = QualifiedNameResolver.qualifiedNameOf(
			variableElement
		);
		final String kind = variableElement.getKind().name().toLowerCase();
		final String docComment = this.docCommentUtil.getDocCommentTree(
				variableElement
			)
			.map(DocCommentTree::getFullBody)
			.map(List::toString)
			.orElse("");
		final String returnType = variableElement.asType().toString();
		final String declaringType = QualifiedNameResolver.qualifiedNameOf(
			variableElement.getEnclosingElement()
		);

		return FieldData.builder()
			.simpleName(simpleName)
			.qualifiedName(qualifiedName)
			.kind(kind)
			.docComment(docComment)
			.modifiers(
				ModifierMapper.toJavaModifiers(variableElement.getModifiers())
			)
			.returnType(returnType)
			.declaringType(declaringType)
			.build();
	}
}
