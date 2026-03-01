package com.jhornsb2.doclet.generator.markdown.util;

import com.jhornsb2.doclet.generator.markdown.filepath.OutputFilepathStrategy;
import com.jhornsb2.doclet.generator.markdown.filepath.OutputFilepathStrategyResolver;
import com.jhornsb2.doclet.generator.markdown.options.GenericOption;
import com.jhornsb2.doclet.generator.markdown.options.OutputPathLayout;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class OptionUtil {

	private static OptionUtil instance;
	private final GenericOption destinationDir;
	private final GenericOption pathLayout;

	public static void initialize(
		GenericOption destinationDir,
		GenericOption pathLayout
	) {
		if (instance != null) {
			throw new IllegalStateException(
				"OptionUtil has already been initialized."
			);
		}
		instance = new OptionUtil(destinationDir, pathLayout);
	}

	public static OptionUtil getInstance() {
		if (instance == null) {
			throw new IllegalStateException(
				"OptionUtil has not been initialized. Call initialize() first."
			);
		}
		return instance;
	}

	public String getDestinationDirectory() {
		return destinationDir.getValue();
	}

	public OutputPathLayout getOutputPathLayout() {
		return OutputPathLayout.fromOptionValue(this.pathLayout.getValue());
	}

	public OutputFilepathStrategy getOutputFilepathStrategy() {
		return OutputFilepathStrategyResolver.resolve(
			this.getOutputPathLayout()
		);
	}
}
