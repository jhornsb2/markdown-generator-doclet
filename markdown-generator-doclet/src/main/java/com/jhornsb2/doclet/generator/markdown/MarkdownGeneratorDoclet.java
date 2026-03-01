package com.jhornsb2.doclet.generator.markdown;

import com.jhornsb2.doclet.generator.markdown.elements.factory.AnnotationDataFactory;
import com.jhornsb2.doclet.generator.markdown.elements.factory.ClassDataFactory;
import com.jhornsb2.doclet.generator.markdown.elements.factory.ElementDataCache;
import com.jhornsb2.doclet.generator.markdown.elements.factory.EnumDataFactory;
import com.jhornsb2.doclet.generator.markdown.elements.factory.IElementDataFactory;
import com.jhornsb2.doclet.generator.markdown.elements.factory.InterfaceDataFactory;
import com.jhornsb2.doclet.generator.markdown.elements.factory.ModuleDataFactory;
import com.jhornsb2.doclet.generator.markdown.elements.factory.PackageDataFactory;
import com.jhornsb2.doclet.generator.markdown.logging.DocletLogger;
import com.jhornsb2.doclet.generator.markdown.options.Flag;
import com.jhornsb2.doclet.generator.markdown.options.GenericOption;
import com.jhornsb2.doclet.generator.markdown.util.DocCommentUtil;
import com.jhornsb2.doclet.generator.markdown.util.OptionUtil;
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
		OptionUtil.initialize(this.destinationDir);
		return Set.of(this.noTimestamp, this.destinationDir);
	}

	@Override
	public SourceVersion getSupportedSourceVersion() {
		return SourceVersion.latest();
	}

	@Override
	public boolean run(final DocletEnvironment environment) {
		// Initialize the run
		DocCommentUtil.setEnvironment(environment);

		final ElementDataCache elementDataCache = new ElementDataCache();
		final InterfaceDataFactory interfaceDataFactory =
			new InterfaceDataFactory(elementDataCache);
		final AnnotationDataFactory annotationDataFactory =
			new AnnotationDataFactory(elementDataCache);
		final ClassDataFactory classDataFactory = new ClassDataFactory(
			elementDataCache
		);
		final EnumDataFactory enumDataFactory = new EnumDataFactory(
			elementDataCache
		);
		final PackageDataFactory packageDataFactory = new PackageDataFactory(
			elementDataCache
		);
		final ModuleDataFactory moduleDataFactory = new ModuleDataFactory(
			elementDataCache
		);
		final IElementDataFactory elementDataFactory = new IElementDataFactory(
			elementDataCache,
			moduleDataFactory,
			packageDataFactory,
			interfaceDataFactory,
			annotationDataFactory,
			classDataFactory,
			enumDataFactory
		);

		log.info("Running MarkdownGeneratorDoclet...");
		environment
			.getIncludedElements()
			.parallelStream()
			.map(elementDataFactory::create)
			.forEach(e -> log.info("Processed element: {}", e.toString()));
		return true;
	}
}
