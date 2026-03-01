package com.jhornsb2.doclet.generator.markdown.elements;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;
import java.util.Set;
import org.junit.jupiter.api.Test;

class EnumDataTest {

	@Test
	void builderDefaultsToEmptyHierarchyData() {
		EnumData enumData = EnumData.builder()
			.simpleName("Status")
			.qualifiedName("example.Status")
			.kind("enum")
			.docComment("")
			.build();

		assertEquals(Optional.empty(), enumData.getSuperClass());
		assertTrue(enumData.getSuperInterfaces().isEmpty());
	}

	@Test
	void builderSetsHierarchyData() {
		EnumData enumData = EnumData.builder()
			.simpleName("Status")
			.qualifiedName("example.Status")
			.kind("enum")
			.docComment("docs")
			.superClass(Optional.of("java.lang.Enum"))
			.superInterfaces(Set.of("java.io.Serializable"))
			.build();

		assertEquals(Optional.of("java.lang.Enum"), enumData.getSuperClass());
		assertEquals(Set.of("java.io.Serializable"), enumData.getSuperInterfaces());
	}
}
