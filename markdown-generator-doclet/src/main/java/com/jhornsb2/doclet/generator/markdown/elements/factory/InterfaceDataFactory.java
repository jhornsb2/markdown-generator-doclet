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

@Value
public class InterfaceDataFactory {

	ElementDataCache elementDataCache;
	DocCommentUtil docCommentUtil;

	public InterfaceData create(@NonNull final Element element) {
		final String key = this.elementDataCache.keyFor(element);
		return (InterfaceData) this.elementDataCache.getOrCompute(key, () ->
			this.createUncached(element)
		);
	}

	InterfaceData createUncached(@NonNull final Element element) {
		final TypeElement typeElement = (TypeElement) element;
		final Set<String> superInterfaces = this.resolveSuperInterfaces(
			typeElement
		);

		return InterfaceData.builder()
			.simpleName(typeElement.getSimpleName().toString())
			.qualifiedName(typeElement.getQualifiedName().toString())
			.kind(typeElement.getKind().name().toLowerCase())
			.docComment(
				this.docCommentUtil.getDocCommentTree(typeElement)
					.map(DocCommentTree::getFullBody)
					.map(List::toString)
					.orElse("")
			)
			.superInterfaces(superInterfaces)
			.build();
	}

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
