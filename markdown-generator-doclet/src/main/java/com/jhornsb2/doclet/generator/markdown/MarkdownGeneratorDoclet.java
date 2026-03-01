package com.jhornsb2.doclet.generator.markdown;

import com.jhornsb2.doclet.generator.markdown.logging.DocletLogger;
import com.jhornsb2.doclet.generator.markdown.options.DocletOptions;
import com.jhornsb2.doclet.generator.markdown.options.Flag;
import com.jhornsb2.doclet.generator.markdown.options.GenericOption;
import com.jhornsb2.doclet.generator.markdown.options.OutputPathLayout;
import java.util.Locale;
import java.util.Set;
import javax.lang.model.SourceVersion;
import jdk.javadoc.doclet.Doclet;
import jdk.javadoc.doclet.DocletEnvironment;
import jdk.javadoc.doclet.Reporter;

/**
 * A Doclet that generates Markdown documentation from Javadoc comments.
 * It processes package and class elements, extracting their documentation
 * and printing it to the specified destination directory.
 */
public class MarkdownGeneratorDoclet implements Doclet {

	/**
	 * Logger for this doclet.
	 */
	private static final DocletLogger log = DocletLogger.forClass(
		MarkdownGeneratorDoclet.class
	);

	/**
	 * The flag to control whether to include a timestamp in the output.
	 * <p>
	 * This is ignored, but is supported in order to be compatible with running
	 * in Gradle.
	 */
	private final Flag noTimestamp = new Flag(
		"-notimestamp",
		"Do not include hidden time stamp"
	);
	/**
	 * The option to specify the destination directory for output files.
	 */
	private final GenericOption destinationDir = new GenericOption(
		"-d",
		"Destination directory for output files",
		"/tmp"
	);

	/**
	 * The option to specify output path layout for generated docs.
	 * <p>
	 * Supported values are: hierarchical, flat.
	 */
	private final GenericOption pathLayout = new GenericOption(
		"-path-layout",
		"Output path layout for generated docs: hierarchical|h|flat|f",
		OutputPathLayout.HIERARCHICAL.getOptionValue()
	);

	@Override
	public void init(final Locale locale, final Reporter reporter) {
		DocletLogger.setReporter(reporter);
	}

	@Override
	public String getName() {
		return this.getClass().getSimpleName();
	}

	@Override
	public Set<Option> getSupportedOptions() {
		return Set.of(this.noTimestamp, this.destinationDir, this.pathLayout);
	}

	@Override
	public SourceVersion getSupportedSourceVersion() {
		return SourceVersion.latest();
	}

	@Override
	public boolean run(final DocletEnvironment environment) {
		final DocletOptions docletOptions = new DocletOptions(
			this.destinationDir,
			this.pathLayout
		);
		final OutputPathLayout resolvedPathLayout;
		try {
			resolvedPathLayout = docletOptions.getOutputPathLayout();
		} catch (IllegalArgumentException ex) {
			log.error(
				"Invalid -path-layout option: {}",
				this.pathLayout.getValue()
			);
			log.error(
				"Supported values are: {}",
				OutputPathLayout.supportedOptionValues()
			);
			return false;
		}

		log.info(
			"Starting Markdown generation with destination directory: {}, path layout: {}",
			this.destinationDir.getValue(),
			resolvedPathLayout.getOptionValue()
		);

		// Create and run the generator
		final MarkdownGenerator generator = MarkdownGenerator.createFor(
			environment,
			docletOptions
		);
		generator.generate();

		return true;
	}
}
