package com.jhornsb2.doclet.generator.markdown.filepath;

import com.jhornsb2.doclet.generator.markdown.options.OutputPathLayout;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.NonNull;

/**
 * Resolves output filepath strategy implementations from path layout options.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class OutputFilepathStrategyResolver {

	private static final OutputFilepathStrategy HIERARCHICAL_STRATEGY =
		new HierarchicalOutputFilepathStrategy();
	private static final OutputFilepathStrategy FLAT_STRATEGY =
		new FlatOutputFilepathStrategy();

	public static OutputFilepathStrategy resolve(
		@NonNull final OutputPathLayout outputPathLayout
	) {
		return switch (outputPathLayout) {
			case HIERARCHICAL -> HIERARCHICAL_STRATEGY;
			case FLAT -> FLAT_STRATEGY;
		};
	}
}
