package com.jhornsb2.doclet.generator.markdown.options;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class OutputPathLayoutTest {

	@Test
	void fromOptionValueSupportsLongAndShortNames() {
		assertEquals(
			OutputPathLayout.HIERARCHICAL,
			OutputPathLayout.fromOptionValue("hierarchical")
		);
		assertEquals(
			OutputPathLayout.HIERARCHICAL,
			OutputPathLayout.fromOptionValue("h")
		);
		assertEquals(
			OutputPathLayout.FLAT,
			OutputPathLayout.fromOptionValue("flat")
		);
		assertEquals(
			OutputPathLayout.FLAT,
			OutputPathLayout.fromOptionValue("f")
		);
	}

	@Test
	void fromOptionValueIsCaseInsensitive() {
		assertEquals(
			OutputPathLayout.HIERARCHICAL,
			OutputPathLayout.fromOptionValue("H")
		);
		assertEquals(
			OutputPathLayout.FLAT,
			OutputPathLayout.fromOptionValue("FLAT")
		);
	}

	@Test
	void fromOptionValueDefaultsToHierarchicalWhenBlank() {
		assertEquals(
			OutputPathLayout.HIERARCHICAL,
			OutputPathLayout.fromOptionValue(null)
		);
		assertEquals(
			OutputPathLayout.HIERARCHICAL,
			OutputPathLayout.fromOptionValue("   ")
		);
	}

	@Test
	void fromOptionValueRejectsInvalidValue() {
		IllegalArgumentException ex = assertThrows(
			IllegalArgumentException.class,
			() -> OutputPathLayout.fromOptionValue("invalid")
		);
		assertTrue(ex.getMessage().contains("Unsupported -path-layout value"));
		assertTrue(ex.getMessage().contains("Expected one of"));
	}
}
