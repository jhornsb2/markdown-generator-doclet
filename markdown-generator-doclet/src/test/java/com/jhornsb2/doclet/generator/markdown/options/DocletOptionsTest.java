package com.jhornsb2.doclet.generator.markdown.options;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.jhornsb2.doclet.generator.markdown.filepath.FlatOutputFilepathStrategy;
import com.jhornsb2.doclet.generator.markdown.filepath.HierarchicalOutputFilepathStrategy;
import com.jhornsb2.doclet.generator.markdown.logging.DocletLoggingLevel;
import java.util.List;
import org.junit.jupiter.api.Test;

class DocletOptionsTest {

	@Test
	void resolvesDefaultValuesWhenNotOverridden() {
		DocletOptions options = createOptions("build/docs", "hierarchical", "");

		assertEquals("build/docs", options.getDestinationDirectory());
		assertEquals(
			OutputPathLayout.HIERARCHICAL,
			options.getOutputPathLayout()
		);
		assertInstanceOf(
			HierarchicalOutputFilepathStrategy.class,
			options.getOutputFilepathStrategy()
		);
		assertEquals(DocletLoggingLevel.WARN, options.getParsedLogLevel());
		assertFalse(options.hasTemplateDirectory());
	}

	@Test
	void resolvesConfiguredFlatLayoutAndTemplateDirectory() {
		DocletOptions options = createOptions(
			"build/docs",
			"flat",
			"templates"
		);

		assertEquals(OutputPathLayout.FLAT, options.getOutputPathLayout());
		assertInstanceOf(
			FlatOutputFilepathStrategy.class,
			options.getOutputFilepathStrategy()
		);
		assertTrue(options.hasTemplateDirectory());
		assertEquals("templates", options.getTemplateDirectory());
	}

	@Test
	void resolvesConfiguredLogLevel() {
		DocletOptions options = createOptions("build/docs", "hierarchical", "");
		options.getLogLevel().process("-log-level", List.of("debug"));

		assertEquals(DocletLoggingLevel.DEBUG, options.getParsedLogLevel());
	}

	private static DocletOptions createOptions(
		final String destination,
		final String layout,
		final String templateDirectory
	) {
		GenericOption destinationOption = new GenericOption(
			"-d",
			"Destination",
			destination
		);
		GenericOption pathLayoutOption = new GenericOption(
			"-path-layout",
			"Layout",
			"hierarchical"
		);
		pathLayoutOption.process("-path-layout", List.of(layout));

		GenericOption templateDirectoryOption = new GenericOption(
			"-template-dir",
			"Template directory",
			""
		);
		GenericOption logLevelOption = new GenericOption(
			"-log-level",
			"Log level",
			DocletLoggingLevel.WARN.getOptionValue()
		);
		if (!templateDirectory.isBlank()) {
			templateDirectoryOption.process(
				"-template-dir",
				List.of(templateDirectory)
			);
		}

		return new DocletOptions(
			destinationOption,
			pathLayoutOption,
			templateDirectoryOption,
			logLevelOption
		);
	}
}
