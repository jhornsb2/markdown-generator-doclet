package com.jhornsb2.doclet.generator.markdown;

import com.jhornsb2.doclet.generator.markdown.logging.DocletLogger;
import com.jhornsb2.doclet.generator.markdown.options.DocletOptions;
import com.jhornsb2.doclet.generator.markdown.options.DocletOptionsResolver;
import com.jhornsb2.doclet.generator.markdown.options.DocletOptionValidationException;
import com.jhornsb2.doclet.generator.markdown.options.Flag;
import com.jhornsb2.doclet.generator.markdown.options.GenericOption;
import com.jhornsb2.doclet.generator.markdown.options.OutputPathLayout;
import com.jhornsb2.doclet.generator.markdown.options.ResolvedDocletOptions;
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

	/**
	 * The option to specify a directory containing custom template files.
	 */
	private final GenericOption templateDir = new GenericOption(
		"-template-dir",
		"Directory containing custom templates (*.md)",
		""
	);

	private final DocletOptionsResolver docletOptionsResolver =
		new DocletOptionsResolver();

	/**
	 * Initializes doclet locale/reporting integration.
	 *
	 * @param locale active locale for doclet execution.
	 * @param reporter reporter instance used for diagnostics.
	 */
	@Override
	public void init(final Locale locale, final Reporter reporter) {
		DocletLogger.setReporter(reporter);
	}

	/**
	 * Returns this doclet implementation name.
	 *
	 * @return simple class name used by tooling output.
	 */
	@Override
	public String getName() {
		return this.getClass().getSimpleName();
	}

	/**
	 * Returns all supported command-line options.
	 *
	 * @return immutable set of supported options.
	 */
	@Override
	public Set<Option> getSupportedOptions() {
		return Set.of(
			this.noTimestamp,
			this.destinationDir,
			this.pathLayout,
			this.templateDir
		);
	}

	/**
	 * Declares the newest supported Java source version.
	 *
	 * @return latest source version from the running JDK.
	 */
	@Override
	public SourceVersion getSupportedSourceVersion() {
		return SourceVersion.latest();
	}

	/**
	 * Executes markdown generation for the provided doclet environment.
	 * <p>
	 * The method validates option values, builds a configured
	 * {@link MarkdownGenerator}, and runs generation.
	 *
	 * @param environment doclet environment containing included language model
	 *                    elements.
	 * @return {@code true} when generation succeeds, otherwise {@code false}.
	 */
	@Override
	public boolean run(final DocletEnvironment environment) {
		final DocletOptions docletOptions = new DocletOptions(
			this.destinationDir,
			this.pathLayout,
			this.templateDir
		);

		// Resolve and validate options, aborting with an error message if any option is invalid
		final ResolvedDocletOptions resolvedDocletOptions;
		try {
			resolvedDocletOptions = this.docletOptionsResolver.resolve(
				docletOptions
			);
		} catch (DocletOptionValidationException ex) {
			log.error(ex.getMessage());
			return false;
		}

		log.info(
			"Starting Markdown generation with destination directory: {}, path layout: {}, template directory: {}",
			this.destinationDir.getValue(),
			resolvedDocletOptions.getOutputPathLayout().getOptionValue(),
			resolvedDocletOptions.hasTemplateDirectory()
				? docletOptions.getTemplateDirectory()
				: "<built-in>"
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
