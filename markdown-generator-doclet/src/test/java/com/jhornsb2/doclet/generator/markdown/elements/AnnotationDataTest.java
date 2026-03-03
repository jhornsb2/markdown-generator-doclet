package com.jhornsb2.doclet.generator.markdown.elements;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Set;
import org.junit.jupiter.api.Test;

class AnnotationDataTest {

	@Test
	void builderSetsAllFields() {
		AnnotationData annotationData = AnnotationData.builder()
			.simpleName("MyAnnotation")
			.qualifiedName("example.MyAnnotation")
			.kind("annotation_type")
			.docComment("docs")
			.build();

		assertEquals("MyAnnotation", annotationData.getSimpleName());
		assertEquals("example.MyAnnotation", annotationData.getQualifiedName());
		assertEquals("annotation_type", annotationData.getKind());
		assertEquals("docs", annotationData.getDocComment());
		assertEquals(Set.of(), annotationData.getModifiers());
	}

	@Test
	void builderSetsModifiers() {
		AnnotationData annotationData = AnnotationData.builder()
			.simpleName("MyAnnotation")
			.qualifiedName("example.MyAnnotation")
			.kind("annotation_type")
			.docComment("docs")
			.modifiers(Set.of(JavaModifier.PUBLIC))
			.build();

		assertEquals(Set.of(JavaModifier.PUBLIC), annotationData.getModifiers());
	}
}
