package com.github.jhornsb2.doclet.generator.markdown.util;

import com.github.jhornsb2.doclet.generator.markdown.options.GenericOption;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class OptionUtil {

	private static OptionUtil instance;
	private final GenericOption destinationDir;

	public static void initialize(GenericOption destinationDir) {
		if (instance != null) {
			throw new IllegalStateException("OptionUtil has already been initialized.");
		}
		instance = new OptionUtil(destinationDir);
	}

	public static OptionUtil getInstance() {
		if (instance == null) {
			throw new IllegalStateException("OptionUtil has not been initialized. Call initialize() first.");
		}
		return instance;
	}

	public String getDestinationDirectory()	{
		return destinationDir.getValue();
	}

}
