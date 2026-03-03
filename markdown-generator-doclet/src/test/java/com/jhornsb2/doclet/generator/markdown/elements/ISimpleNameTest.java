package com.jhornsb2.doclet.generator.markdown.elements;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class ISimpleNameTest {

	@Test
	void returnsSimpleNameFromImplementations() {
		ISimpleName data = ModuleData.builder()
			.simpleName("app")
			.qualifiedName("com.example.app")
			.kind("module")
			.docComment("")
			.moduleContents(java.util.Set.of())
			.build();

		assertEquals("app", data.getSimpleName());
	}
}
