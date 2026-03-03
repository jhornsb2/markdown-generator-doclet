package com.jhornsb2.doclet.generator.markdown.elements;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class IKindTest {

	@Test
	void returnsKindFromImplementations() {
		IKind element = PackageData.builder()
			.simpleName("example")
			.qualifiedName("com.example")
			.kind("package")
			.docComment("")
			.packageContents(java.util.Set.of())
			.build();

		assertEquals("package", element.getKind());
	}
}
