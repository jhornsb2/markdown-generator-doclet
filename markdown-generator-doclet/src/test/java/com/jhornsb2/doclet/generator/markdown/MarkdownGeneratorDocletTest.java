package com.jhornsb2.doclet.generator.markdown;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.lang.reflect.Proxy;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import jdk.javadoc.doclet.Doclet;
import jdk.javadoc.doclet.DocletEnvironment;
import jdk.javadoc.doclet.Reporter;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class MarkdownGeneratorDocletTest {

	private static final Reporter REPORTER = (Reporter) Proxy.newProxyInstance(
		Reporter.class.getClassLoader(),
		new Class<?>[] { Reporter.class },
		(proxy, method, args) -> null
	);

	private static final DocletEnvironment EMPTY_ENVIRONMENT =
		(DocletEnvironment) Proxy.newProxyInstance(
			DocletEnvironment.class.getClassLoader(),
			new Class<?>[] { DocletEnvironment.class },
			(proxy, method, args) -> {
				if ("getIncludedElements".equals(method.getName())) {
					return Collections.emptySet();
				}
				return null;
			}
		);

	@BeforeAll
	static void beforeAll() {
		MarkdownGeneratorDoclet doclet = new MarkdownGeneratorDoclet();
		doclet.init(null, REPORTER);
	}

	@Test
	void getNameReturnsSimpleClassName() {
		MarkdownGeneratorDoclet doclet = new MarkdownGeneratorDoclet();

		assertEquals("MarkdownGeneratorDoclet", doclet.getName());
	}

	@Test
	void getSupportedSourceVersionIsAvailable() {
		MarkdownGeneratorDoclet doclet = new MarkdownGeneratorDoclet();

		assertNotNull(doclet.getSupportedSourceVersion());
	}

	@Test
	void getSupportedOptionsIncludesDestinationAndNoTimestamp() {
		MarkdownGeneratorDoclet doclet = new MarkdownGeneratorDoclet();

		Set<? extends Doclet.Option> options = doclet.getSupportedOptions();
		Set<String> optionNames = options
			.stream()
			.map(Doclet.Option::getNames)
			.flatMap(List::stream)
			.collect(java.util.stream.Collectors.toSet());

		assertEquals(
			Set.of("-d", "-notimestamp", "-path-layout", "-template-dir"),
			optionNames
		);
	}

	@Test
	void runReturnsTrueWhenEnvironmentIsEmpty() {
		MarkdownGeneratorDoclet doclet = new MarkdownGeneratorDoclet();
		doclet.getSupportedOptions();

		boolean result = doclet.run(EMPTY_ENVIRONMENT);

		assertTrue(result);
	}

	@Test
	void runReturnsFalseWhenPathLayoutOptionIsInvalid() {
		MarkdownGeneratorDoclet doclet = new MarkdownGeneratorDoclet();

		Set<? extends Doclet.Option> options = doclet.getSupportedOptions();
		Doclet.Option pathLayoutOption = options
			.stream()
			.filter(option -> option.getNames().contains("-path-layout"))
			.findFirst()
			.orElseThrow();

		pathLayoutOption.process("-path-layout", List.of("invalid-layout"));
		boolean result = doclet.run(EMPTY_ENVIRONMENT);

		assertFalse(result);
	}

	@Test
	void runReturnsFalseWhenTemplateDirectoryDoesNotExist() {
		MarkdownGeneratorDoclet doclet = new MarkdownGeneratorDoclet();

		Set<? extends Doclet.Option> options = doclet.getSupportedOptions();
		Doclet.Option templateDirOption = options
			.stream()
			.filter(option -> option.getNames().contains("-template-dir"))
			.findFirst()
			.orElseThrow();

		templateDirOption.process(
			"-template-dir",
			List.of("missing-template-dir-" + System.nanoTime())
		);
		boolean result = doclet.run(EMPTY_ENVIRONMENT);

		assertFalse(result);
	}
}
