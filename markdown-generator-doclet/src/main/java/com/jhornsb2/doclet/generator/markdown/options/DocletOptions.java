package com.jhornsb2.doclet.generator.markdown.options;

import com.jhornsb2.doclet.generator.markdown.filepath.OutputFilepathStrategy;
import com.jhornsb2.doclet.generator.markdown.filepath.OutputFilepathStrategyResolver;
import lombok.NonNull;
import lombok.Value;

@Value
public class DocletOptions {

	@NonNull
	GenericOption destinationDir;
	@NonNull
	GenericOption pathLayout;

	public String getDestinationDirectory() {
		return this.destinationDir.getValue();
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
