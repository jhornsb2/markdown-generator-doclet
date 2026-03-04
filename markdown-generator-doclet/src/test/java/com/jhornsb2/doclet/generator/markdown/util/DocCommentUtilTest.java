package com.jhornsb2.doclet.generator.markdown.util;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

import javax.lang.model.element.Element;
import jdk.javadoc.doclet.DocletEnvironment;
import org.junit.jupiter.api.Test;

class DocCommentUtilTest {

	@Test
	void exposesConfiguredEnvironment() {
		DocletEnvironment environment = null;

		DocCommentUtil util = new DocCommentUtil(environment);

		assertSame(environment, util.getEnvironment());
	}

	@Test
	void getDocCommentTreeRejectsNullElement() {
		DocCommentUtil util = new DocCommentUtil(null);

		assertThrows(NullPointerException.class, () ->
			util.getDocCommentTree((Element) null)
		);
	}
}
