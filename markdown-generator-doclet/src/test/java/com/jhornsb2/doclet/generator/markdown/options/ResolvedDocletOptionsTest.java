package com.jhornsb2.doclet.generator.markdown.options;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.jhornsb2.doclet.generator.markdown.logging.DocletLoggingLevel;
import java.nio.file.Path;
import java.util.Optional;
import org.junit.jupiter.api.Test;

class ResolvedDocletOptionsTest {

	@Test
	void reportsTemplateDirectoryWhenPresent() {
		ResolvedDocletOptions options = new ResolvedDocletOptions(
			OutputPathLayout.HIERARCHICAL,
			Optional.of(Path.of("templates")),
			DocletLoggingLevel.WARN
		);

		assertTrue(options.hasTemplateDirectory());
	}

	@Test
	void reportsNoTemplateDirectoryWhenAbsent() {
		ResolvedDocletOptions options = new ResolvedDocletOptions(
			OutputPathLayout.HIERARCHICAL,
			Optional.empty(),
			DocletLoggingLevel.WARN
		);

		assertFalse(options.hasTemplateDirectory());
	}
}
