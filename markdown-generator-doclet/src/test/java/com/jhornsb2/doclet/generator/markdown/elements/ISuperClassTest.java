package com.jhornsb2.doclet.generator.markdown.elements;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;
import org.junit.jupiter.api.Test;

class ISuperClassTest {

	@Test
	void defaultsToNoSuperclass() {
		ISuperClass data = ClassData.builder()
			.simpleName("Type")
			.qualifiedName("com.example.Type")
			.kind("class")
			.docComment("")
			.build();

		assertTrue(data.getSuperClass().isEmpty());
	}

	@Test
	void returnsConfiguredSuperclass() {
		ISuperClass data = ClassData.builder()
			.simpleName("Type")
			.qualifiedName("com.example.Type")
			.kind("class")
			.docComment("")
			.superClass(Optional.of("java.lang.Object"))
			.build();

		assertEquals(Optional.of("java.lang.Object"), data.getSuperClass());
	}
}
