package com.jhornsb2.doclet.generator.markdown.elements.factory;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

import com.jhornsb2.doclet.generator.markdown.elements.ClassData;
import java.util.concurrent.atomic.AtomicInteger;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.TypeElement;
import org.junit.jupiter.api.Test;

class ElementDataCacheTest {

	@Test
	void getOrComputeReturnsSameInstanceForSameKey() {
		ElementDataCache cache = new ElementDataCache();
		AtomicInteger computeCalls = new AtomicInteger();

		ClassData first = (ClassData) cache.getOrCompute("key", () -> {
			computeCalls.incrementAndGet();
			return ClassData.builder()
				.simpleName("A")
				.qualifiedName("example.A")
				.kind("class")
				.docComment("")
				.build();
		});

		ClassData second = (ClassData) cache.getOrCompute("key", () -> {
			computeCalls.incrementAndGet();
			return ClassData.builder()
				.simpleName("B")
				.qualifiedName("example.B")
				.kind("class")
				.docComment("")
				.build();
		});

		assertSame(first, second);
		assertEquals(1, computeCalls.get());
	}

	@Test
	void keyForUsesQualifiedNameForQualifiedElements() {
		ElementDataCache cache = new ElementDataCache();
		TypeElement typeElement = FactoryTestFixtures.minimalTypeElement(
			"MyClass",
			"example.MyClass",
			ElementKind.CLASS
		);

		assertEquals("CLASS:example.MyClass", cache.keyFor(typeElement));
	}

	@Test
	void keyForFallsBackToToStringForNonQualifiedElements() {
		ElementDataCache cache = new ElementDataCache();
		Element element = FactoryTestFixtures.plainElement(
			ElementKind.METHOD,
			"run()"
		);

		assertEquals("METHOD:run()", cache.keyFor(element));
	}
}
