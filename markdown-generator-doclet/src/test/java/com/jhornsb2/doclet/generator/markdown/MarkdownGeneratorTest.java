package com.jhornsb2.doclet.generator.markdown;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.jhornsb2.doclet.generator.markdown.options.DocletOptions;
import com.jhornsb2.doclet.generator.markdown.options.GenericOption;
import jdk.javadoc.doclet.DocletEnvironment;
import org.junit.jupiter.api.Test;

class MarkdownGeneratorTest {

	@Test
	void createForRejectsNullEnvironment() {
		assertThrows(NullPointerException.class, () ->
			MarkdownGenerator.createFor(null, createDocletOptions())
		);
	}

	@Test
	void createForRejectsNullOptions() {
		assertThrows(NullPointerException.class, () ->
			MarkdownGenerator.createFor(emptyEnvironment(), null)
		);
	}

	@Test
	void createForBuildsGenerator() {
		MarkdownGenerator generator = MarkdownGenerator.createFor(
			emptyEnvironment(),
			createDocletOptions()
		);

		assertNotNull(generator);
	}

	private static DocletEnvironment emptyEnvironment() {
		return (DocletEnvironment) java.lang.reflect.Proxy.newProxyInstance(
			DocletEnvironment.class.getClassLoader(),
			new Class<?>[] { DocletEnvironment.class },
			(proxy, method, args) -> null
		);
	}

	private static DocletOptions createDocletOptions() {
		GenericOption destinationDir = new GenericOption(
			"-d",
			"destination directory",
			"build/docs"
		);
		GenericOption pathLayout = new GenericOption(
			"-path-layout",
			"output path layout",
			"hierarchical"
		);
		GenericOption templateDir = new GenericOption(
			"-template-dir",
			"template directory",
			""
		);
		return new DocletOptions(destinationDir, pathLayout, templateDir);
	}
}
