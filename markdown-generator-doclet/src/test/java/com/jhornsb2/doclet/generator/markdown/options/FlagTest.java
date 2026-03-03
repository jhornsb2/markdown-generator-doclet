package com.jhornsb2.doclet.generator.markdown.options;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import org.junit.jupiter.api.Test;

class FlagTest {

	@Test
	void defaultsToUnset() {
		Flag flag = new Flag("-verbose", "Verbose output");

		assertFalse(flag.isSet());
	}

	@Test
	void becomesSetAfterProcess() {
		Flag flag = new Flag("-verbose", "Verbose output");

		boolean processed = flag.process("-verbose", List.of());

		assertTrue(processed);
		assertTrue(flag.isSet());
	}

	@Test
	void exposesExpectedArgumentAndNameMetadata() {
		Flag flag = new Flag("-verbose", "Verbose output");

		assertEquals(0, flag.getArgumentCount());
		assertEquals(List.of("-verbose"), flag.getNames());
	}
}
