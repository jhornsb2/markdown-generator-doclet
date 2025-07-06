package com.jhornsb2.doclet.generator.markdown;

import java.io.IOException;
import java.util.Locale;
import java.util.Set;

import javax.lang.model.SourceVersion;
import javax.lang.model.element.ModuleElement;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;

import com.jhornsb2.doclet.generator.markdown.logging.DocletLogger;
import com.jhornsb2.doclet.generator.markdown.options.Flag;
import com.jhornsb2.doclet.generator.markdown.options.GenericOption;
import com.jhornsb2.doclet.generator.markdown.processor.IDocletElementProcessor;
import com.jhornsb2.doclet.generator.markdown.processor.impl.AnnotationElementProcessor;
import com.jhornsb2.doclet.generator.markdown.processor.impl.ClassElementProcessor;
import com.jhornsb2.doclet.generator.markdown.processor.impl.EnumElementProcessor;
import com.jhornsb2.doclet.generator.markdown.processor.impl.InterfaceElementProcessor;
import com.jhornsb2.doclet.generator.markdown.processor.impl.PackageElementProcessor;
import com.jhornsb2.doclet.generator.markdown.processor.impl.RecordElementProcessor;
import com.jhornsb2.doclet.generator.markdown.util.DocCommentUtil;
import com.jhornsb2.doclet.generator.markdown.util.OptionUtil;

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
	private static final DocletLogger log = DocletLogger.forClass(MarkdownGeneratorDoclet.class);

	/**
	 * The flag to control whether to include a timestamp in the output.
	 * <p>
	 * This is ignored, but is supported in order to be compatible with running in
	 * gradle.
	 */
	private final Flag noTimestamp = new Flag("-notimestamp", "Do not include hidden time stamp");
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

		log.info("Running MarkdownGeneratorDoclet...");
		environment.getIncludedElements().forEach(element -> {
			IDocletElementProcessor elementProcessor = switch (element.getKind()) {
				case MODULE -> {
					ModuleElement moduleElement = (ModuleElement) element;
					log.info("Processing module: {}", moduleElement.getQualifiedName());
					yield null; // No specific processing for modules yet
				}
				case PACKAGE -> new PackageElementProcessor((PackageElement) element);
				case INTERFACE -> new InterfaceElementProcessor((TypeElement) element);
				case CLASS -> new ClassElementProcessor((TypeElement) element);
				case ENUM -> new EnumElementProcessor((TypeElement) element);
				case RECORD -> new RecordElementProcessor((TypeElement) element);
				case ANNOTATION_TYPE -> new AnnotationElementProcessor((TypeElement) element);
				default -> {
					log.warn("Unhandled element kind: {}", element.getKind());
					yield null;
				}
			};

			if (elementProcessor == null) {
				log.warn("No processor found for element: {}", element.getKind());
				return;
			}

			try {
				elementProcessor.processElement();
			}
			catch (IOException e) {
				log.error("Error processing element: {}", element.getSimpleName(), e);
			}
		});
		return true;
	}

}
