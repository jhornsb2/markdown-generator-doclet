package com.github.jhornsb2.doclet.generator.markdown.processor;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import com.github.jhornsb2.doclet.generator.markdown.util.OptionUtil;
import com.google.common.io.Files;

public interface IDocletElementProcessor {

	String getOutputFilepath();

	String toMarkdownString();

	default void processElement() throws IOException {
		String outputFilepath = this.getOutputFilepath();
		File outputFile = new File(OptionUtil.getInstance().getDestinationDirectory(), outputFilepath);
		Files.createParentDirs(outputFile);
		Files.asCharSink(outputFile, StandardCharsets.UTF_8).write(this.toMarkdownString());
	}

}
