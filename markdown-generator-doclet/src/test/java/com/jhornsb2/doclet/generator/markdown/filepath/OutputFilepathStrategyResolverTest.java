package com.jhornsb2.doclet.generator.markdown.filepath;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.jhornsb2.doclet.generator.markdown.options.OutputPathLayout;
import org.junit.jupiter.api.Test;

class OutputFilepathStrategyResolverTest {

	@Test
	void resolvesHierarchicalLayoutToHierarchicalStrategySingleton() {
		IOutputFilepathStrategy first = OutputFilepathStrategyResolver.resolve(
			OutputPathLayout.HIERARCHICAL
		);
		IOutputFilepathStrategy second = OutputFilepathStrategyResolver.resolve(
			OutputPathLayout.HIERARCHICAL
		);

		assertInstanceOf(HierarchicalOutputFilepathStrategy.class, first);
		assertSame(first, second);
	}

	@Test
	void resolvesFlatLayoutToFlatStrategySingleton() {
		IOutputFilepathStrategy first = OutputFilepathStrategyResolver.resolve(
			OutputPathLayout.FLAT
		);
		IOutputFilepathStrategy second = OutputFilepathStrategyResolver.resolve(
			OutputPathLayout.FLAT
		);

		assertInstanceOf(FlatOutputFilepathStrategy.class, first);
		assertSame(first, second);
	}

	@Test
	void rejectsNullLayout() {
		assertThrows(NullPointerException.class, () ->
			OutputFilepathStrategyResolver.resolve(null)
		);
	}
}
