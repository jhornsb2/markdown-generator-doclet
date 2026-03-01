package com.jhornsb2.doclet.generator.markdown.factory;

import com.jhornsb2.doclet.generator.markdown.options.OutputPathLayout;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.NonNull;

/**
 * Resolves output filepath factory implementations from path layout options.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class OutputFilepathFactoryResolver {

	private static final IOutputFilepathFactory HIERARCHICAL_FACTORY =
		new StandardOutputFilepathFactory();
	private static final IOutputFilepathFactory FLAT_FACTORY =
		new GithubOutputFilepathFactory();

	public static IOutputFilepathFactory resolve(
		@NonNull final OutputPathLayout outputPathLayout
	) {
		return switch (outputPathLayout) {
			case HIERARCHICAL -> HIERARCHICAL_FACTORY;
			case FLAT -> FLAT_FACTORY;
		};
	}
}
