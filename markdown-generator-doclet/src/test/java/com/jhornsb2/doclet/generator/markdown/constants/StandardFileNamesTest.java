package com.jhornsb2.doclet.generator.markdown.constants;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class StandardFileNamesTest {

	@Test
	void indexFileNameIsStable() {
		assertEquals("index.md", StandardFileNames.INDEX_FILE_NAME);
	}
}
