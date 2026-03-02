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

	@NonNull
	GenericOption templateDir;

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

	public String getTemplateDirectory() {
		return this.templateDir.getValue();
	}

	public boolean hasTemplateDirectory() {
		return !this.getTemplateDirectory().isBlank();
	}
}
