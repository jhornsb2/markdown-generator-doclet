package com.jhornsb2.doclet.generator.markdown.elements;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class JavaModifierTest {

	@Test
	void exposesExpectedKeywordValues() {
		assertEquals("public", JavaModifier.PUBLIC.getKeyword());
		assertEquals("non-sealed", JavaModifier.NON_SEALED.getKeyword());
	}

	@Test
	void toStringMatchesKeyword() {
		assertEquals(
			JavaModifier.STATIC.getKeyword(),
			JavaModifier.STATIC.toString()
		);
		assertEquals(
			JavaModifier.STRICTFP.getKeyword(),
			JavaModifier.STRICTFP.toString()
		);
	}
}
