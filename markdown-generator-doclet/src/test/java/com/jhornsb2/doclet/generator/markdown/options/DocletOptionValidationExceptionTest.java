package com.jhornsb2.doclet.generator.markdown.options;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;

import org.junit.jupiter.api.Test;

class DocletOptionValidationExceptionTest {

	@Test
	void storesMessageWhenCreatedWithMessageOnly() {
		DocletOptionValidationException ex =
			new DocletOptionValidationException("invalid option");

		assertEquals("invalid option", ex.getMessage());
		assertNull(ex.getCause());
	}

	@Test
	void storesMessageAndCauseWhenProvided() {
		IllegalArgumentException cause = new IllegalArgumentException("bad");
		DocletOptionValidationException ex =
			new DocletOptionValidationException("invalid option", cause);

		assertEquals("invalid option", ex.getMessage());
		assertSame(cause, ex.getCause());
	}
}
