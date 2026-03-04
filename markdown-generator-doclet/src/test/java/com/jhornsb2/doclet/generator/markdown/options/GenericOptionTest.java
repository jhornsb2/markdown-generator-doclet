package com.jhornsb2.doclet.generator.markdown.options;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import org.junit.jupiter.api.Test;

class GenericOptionTest {

	@Test
	void usesDefaultValueUntilProcessed() {
		GenericOption option = new GenericOption(
			"-d",
			"Destination directory",
			"default-output"
		);

		assertEquals("default-output", option.getValue());
	}

	@Test
	void processStoresFirstProvidedValue() {
		GenericOption option = new GenericOption(
			"-d",
			"Destination",
			"default"
		);

		boolean processed = option.process("-d", List.of("custom", "ignored"));

		assertTrue(processed);
		assertEquals("custom", option.getValue());
	}

	@Test
	void processReturnsFalseWhenNoValuesProvided() {
		GenericOption option = new GenericOption(
			"-d",
			"Destination",
			"default"
		);

		boolean processed = option.process("-d", List.of());

		assertFalse(processed);
		assertEquals("default", option.getValue());
	}
}
