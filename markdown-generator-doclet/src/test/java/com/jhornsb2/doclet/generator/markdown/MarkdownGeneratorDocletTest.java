package com.jhornsb2.doclet.generator.markdown;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

class MarkdownGeneratorDocletTest {

	@Test
	void getNameReturnsSimpleClassName() {
		MarkdownGeneratorDoclet doclet = new MarkdownGeneratorDoclet();

		assertEquals("MarkdownGeneratorDoclet", doclet.getName());
	}

	@Test
	void getSupportedSourceVersionIsAvailable() {
		MarkdownGeneratorDoclet doclet = new MarkdownGeneratorDoclet();

		assertNotNull(doclet.getSupportedSourceVersion());
	}
}
