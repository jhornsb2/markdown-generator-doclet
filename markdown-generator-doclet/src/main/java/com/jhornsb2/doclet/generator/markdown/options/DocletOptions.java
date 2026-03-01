package com.jhornsb2.doclet.generator.markdown.options;

import com.jhornsb2.doclet.generator.markdown.filepath.OutputFilepathStrategy;
import com.jhornsb2.doclet.generator.markdown.filepath.OutputFilepathStrategyResolver;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class DocletOptions {

	private static DocletOptions instance;
	private final GenericOption destinationDir;
	private final GenericOption pathLayout;

	public static void initialize(
		GenericOption destinationDir,
		GenericOption pathLayout
	) {
		if (instance != null) {
			throw new IllegalStateException(
				"DocletOptions has already been initialized."
			);
		}
		instance = new DocletOptions(destinationDir, pathLayout);
	}

	public static DocletOptions getInstance() {
		if (instance == null) {
			throw new IllegalStateException(
				"DocletOptions has not been initialized. Call initialize() first."
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
