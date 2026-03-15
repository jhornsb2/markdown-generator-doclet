package com.jhornsb2.doclet.generator.markdown.util;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class StringUtilTest {

	@Test
	void formatLombokToStringReturnsNullLiteralForNullInput() {
		assertEquals("null", StringUtil.formatLombokToString(null));
	}

	@Test
	void formatLombokToStringFormatsFlatObjectWithIndentation() {
		String input = "Foo(a=1, b=2)";
		String expected = """
			Foo(
			  a=1,
			  b=2
			)
			""".stripIndent()
			.trim();

		assertEquals(expected, StringUtil.formatLombokToString(input));
	}

	@Test
	void formatLombokToStringKeepsEmptyParenthesesCompact() {
		String input = "Foo()";
		assertEquals("Foo()", StringUtil.formatLombokToString(input));
	}

	@Test
	void formatLombokToStringFormatsNestedStructures() {
		String input = "Foo(a=Bar(x=1, y=2), b=[one, two])";
		String expected = """
			Foo(
			  a=Bar(
			    x=1,
			    y=2
			  ),
			  b=[
			    one,
			    two
			  ]
			)
			""".stripIndent()
			.trim();

		assertEquals(expected, StringUtil.formatLombokToString(input));
	}

	@Test
	void formatLombokToStringDoesNotSplitDelimitersInsideQuotes() {
		String input = "Foo(label=\"a,b(c)\", code='x,y[z]')";
		String expected = """
			Foo(
			  label=\"a,b(c)\",
			  code='x,y[z]'
			)
			""".stripIndent()
			.trim();

		assertEquals(expected, StringUtil.formatLombokToString(input));
	}
}
