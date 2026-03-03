package com.jhornsb2.doclet.generator.markdown.elements;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class IQualifiedNameTest {

	@Test
	void returnsQualifiedNameFromImplementations() {
		IQualifiedName data = ModuleData.builder()
			.simpleName("app")
			.qualifiedName("com.example.app")
			.kind("module")
			.docComment("")
			.moduleContents(java.util.Set.of())
			.build();

		assertEquals("com.example.app", data.getQualifiedName());
	}
}
