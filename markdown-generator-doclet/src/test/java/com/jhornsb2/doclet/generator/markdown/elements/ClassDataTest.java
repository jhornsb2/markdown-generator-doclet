package com.jhornsb2.doclet.generator.markdown.elements;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;
import java.util.Set;
import org.junit.jupiter.api.Test;

class ClassDataTest {

	@Test
	void builderDefaultsToEmptyHierarchyData() {
		ClassData classData = ClassData.builder()
			.simpleName("MyClass")
			.qualifiedName("example.MyClass")
			.kind("class")
			.docComment("")
			.build();

		assertEquals(Optional.empty(), classData.getSuperClass());
		assertTrue(classData.getSuperInterfaces().isEmpty());
	}

	@Test
	void builderSetsHierarchyData() {
		ClassData classData = ClassData.builder()
			.simpleName("MyClass")
			.qualifiedName("example.MyClass")
			.kind("class")
			.docComment("docs")
			.superClass(Optional.of("java.lang.Object"))
			.superInterfaces(Set.of("java.io.Serializable"))
			.build();

		assertEquals(
			Optional.of("java.lang.Object"),
			classData.getSuperClass()
		);
		assertEquals(
			Set.of("java.io.Serializable"),
			classData.getSuperInterfaces()
		);
	}
}
