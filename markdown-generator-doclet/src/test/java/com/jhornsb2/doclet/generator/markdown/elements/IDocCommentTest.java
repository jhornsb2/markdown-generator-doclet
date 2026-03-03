package com.jhornsb2.doclet.generator.markdown.elements;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class IDocCommentTest {

	@Test
	void exposesDocCommentFromImplementations() {
		IDocComment data = PackageData.builder()
			.simpleName("example")
			.qualifiedName("com.example")
			.kind("package")
			.docComment("Package docs")
			.packageContents(java.util.Set.of())
			.build();

		assertEquals("Package docs", data.getDocComment());
	}
}
