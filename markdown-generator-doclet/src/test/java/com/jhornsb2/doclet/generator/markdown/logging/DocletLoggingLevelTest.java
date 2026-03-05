package com.jhornsb2.doclet.generator.markdown.logging;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class DocletLoggingLevelTest {

	@Test
	void normalizedPrefixUsesBracketsAndFixedWidth() {
		String prefix = DocletLoggingLevel.INFO.getNormalizedPrefix();

		assertTrue(prefix.startsWith("[INFO]"));
		assertEquals(7, prefix.length());
	}

	@Test
	void normalizedPrefixPreservesLevelName() {
		assertTrue(
			DocletLoggingLevel.DEBUG.getNormalizedPrefix().contains("DEBUG")
		);
		assertTrue(
			DocletLoggingLevel.ERROR.getNormalizedPrefix().contains("ERROR")
		);
	}

	@Test
	void fromOptionValueResolvesLowerCaseToken() {
		assertEquals(
			DocletLoggingLevel.WARN,
			DocletLoggingLevel.fromOptionValue("warn")
		);
	}

	@Test
	void fromOptionValueRejectsUnknownToken() {
		assertThrows(IllegalArgumentException.class, () ->
			DocletLoggingLevel.fromOptionValue("verbose")
		);
	}
}
