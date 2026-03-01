package com.jhornsb2.doclet.generator.markdown.elements.factory;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

import com.jhornsb2.doclet.generator.markdown.elements.AnnotationData;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.TypeElement;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class AnnotationDataFactoryTest {

	@BeforeAll
	static void beforeAll() {
		FactoryTestFixtures.ensureDocletEnvironment();
	}

	@Test
	void createUncachedBuildsAnnotationData() {
		TypeElement annotationElement = FactoryTestFixtures.minimalTypeElement(
			"MyAnnotation",
			"example.MyAnnotation",
			ElementKind.ANNOTATION_TYPE
		);

		AnnotationDataFactory factory = new AnnotationDataFactory(
			new ElementDataCache()
		);
		AnnotationData annotationData = factory.createUncached(annotationElement);

		assertEquals("MyAnnotation", annotationData.getSimpleName());
		assertEquals("example.MyAnnotation", annotationData.getQualifiedName());
		assertEquals("annotation_type", annotationData.getKind());
		assertEquals("", annotationData.getDocComment());
	}

	@Test
	void createUsesCache() {
		ElementDataCache cache = new ElementDataCache();
		AnnotationDataFactory factory = new AnnotationDataFactory(cache);
		TypeElement annotationElement = FactoryTestFixtures.minimalTypeElement(
			"MyAnnotation",
			"example.MyAnnotation",
			ElementKind.ANNOTATION_TYPE
		);

		AnnotationData first = factory.create(annotationElement);
		AnnotationData second = factory.create(annotationElement);

		assertSame(first, second);
	}
}
