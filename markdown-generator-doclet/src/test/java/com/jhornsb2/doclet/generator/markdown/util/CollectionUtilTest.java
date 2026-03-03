package com.jhornsb2.doclet.generator.markdown.util;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import org.junit.jupiter.api.Test;

class CollectionUtilTest {

	@Test
	void isEmptyReturnsTrueForNullCollection() {
		assertTrue(CollectionUtil.isEmpty(null));
	}

	@Test
	void isEmptyReturnsTrueForEmptyCollection() {
		assertTrue(CollectionUtil.isEmpty(List.of()));
	}

	@Test
	void isEmptyReturnsFalseForNonEmptyCollection() {
		assertFalse(CollectionUtil.isEmpty(List.of("value")));
	}
}
