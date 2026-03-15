package com.jhornsb2.doclet.generator.markdown.elements.factory;

import com.jhornsb2.doclet.generator.markdown.elements.IElementData;
import com.jhornsb2.doclet.generator.markdown.elements.MethodData;
import com.jhornsb2.doclet.generator.markdown.elements.VariableData;
import com.jhornsb2.doclet.generator.markdown.naming.QualifiedNameResolver;
import com.jhornsb2.doclet.generator.markdown.util.DocCommentUtil;
import com.sun.source.doctree.DocCommentTree;
import java.util.List;
import java.util.stream.Collectors;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import lombok.NonNull;
import lombok.Value;

/**
 * Factory for creating {@link MethodData} instances from Java method
 * {@link Element} values.
 */
@Value
public class MethodDataFactory {

	/**
	 * Cache for {@link IElementData} instances to ensure consistent and
	 * efficient reuse across factories.
	 */
	final ElementDataCache elementDataCache;
	final DocCommentUtil docCommentUtil;

	/**
	 * Creates a {@link MethodData} instance for the given {@link Element},
	 * which is expected to be a method element. The method first checks the
	 * cache for an existing instance and returns it if found. If not found,
	 * it creates a new instance, stores it in the cache, and then returns it.
	 *
	 * @param element The {@link Element} representing the method for which to
	 *                create the {@link MethodData}. This element is expected to
	 *                be an {@link ExecutableElement}.
	 * @return A {@link MethodData} instance representing the method.
	 */
	public MethodData create(@NonNull final Element element) {
		final String key = this.elementDataCache.keyFor(element);
		return (MethodData) this.elementDataCache.getOrCompute(key, () ->
			this.createUncached(element)
		);
	}

	/**
	 * Creates a {@link MethodData} instance for the given {@link Element}
	 * without checking the cache. This method is intended to be called
	 * internally by the caching mechanism when a cache miss occurs.
	 *
	 * @param element The {@link Element} representing the method for which to
	 *                create the {@link MethodData}. This {@link Element} is
	 *                expected to be an {@link ExecutableElement}.
	 * @return A {@link MethodData} instance representing the method.
	 */
	MethodData createUncached(@NonNull final Element element) {
		final ExecutableElement executableElement = (ExecutableElement) element;
		final String simpleName = executableElement.getSimpleName().toString();
		final String qualifiedName = QualifiedNameResolver.qualifiedNameOf(
			executableElement
		);
		final String kind = executableElement.getKind().name().toLowerCase();
		final String docComment = this.docCommentUtil.getDocCommentTree(
				executableElement
			)
			.map(DocCommentTree::getFullBody)
			.map(List::toString)
			.orElse("");
		final String returnType = executableElement.getReturnType().toString();
		final String declaringType = QualifiedNameResolver.qualifiedNameOf(
			executableElement.getEnclosingElement()
		);
		final List<VariableData> parameters = executableElement
			.getParameters()
			.stream()
			.map(parameter -> VariableData.builder().build())
			.collect(Collectors.toList());

		return MethodData.builder()
			.simpleName(simpleName)
			.qualifiedName(qualifiedName)
			.kind(kind)
			.docComment(docComment)
			.modifiers(
				ModifierMapper.toJavaModifiers(executableElement.getModifiers())
			)
			.returnType(returnType)
			.declaringType(declaringType)
			.parameters(parameters)
			.build();
	}
}
