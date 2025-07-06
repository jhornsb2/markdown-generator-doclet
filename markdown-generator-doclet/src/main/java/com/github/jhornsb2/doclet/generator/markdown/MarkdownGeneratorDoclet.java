package com.github.jhornsb2.doclet.generator.markdown;

import java.io.File;
import java.io.IOException;
import java.util.Locale;
import java.util.Set;

import javax.lang.model.SourceVersion;
import javax.lang.model.element.ModuleElement;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;

import com.github.jhornsb2.doclet.generator.markdown.logging.DocletLogger;
import com.github.jhornsb2.doclet.generator.markdown.options.Flag;
import com.github.jhornsb2.doclet.generator.markdown.options.GenericOption;
import com.github.jhornsb2.doclet.generator.markdown.processor.impl.ClassElementProcessor;
import com.github.jhornsb2.doclet.generator.markdown.processor.impl.InterfaceElementProcessor;
import com.github.jhornsb2.doclet.generator.markdown.processor.impl.PackageElementProcessor;
import com.github.jhornsb2.doclet.generator.markdown.util.DocCommentUtil;
import com.google.common.io.Files;

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
			String outputFilepath;
			File outputFile;
			switch (element.getKind()) {
			case MODULE:
				ModuleElement moduleElement = (ModuleElement) element;
				log.info("Processing module: {}", moduleElement.getQualifiedName());
				break;
			case PACKAGE:
				final PackageElement packageElement = (PackageElement) element;
				final PackageElementProcessor packageProcessor = new PackageElementProcessor(packageElement);
				outputFilepath = packageProcessor.getOutputFilepath();
				outputFile = new File(this.destinationDir.getValue(), outputFilepath);
				try {
					Files.createParentDirs(outputFile);
					Files.asCharSink(outputFile, java.nio.charset.StandardCharsets.UTF_8)
						.write(packageProcessor.toMarkdownString());
				}
				catch (IOException e) {
					log.error("Error writing file: {}", outputFilepath, e);
				}
				break;
			case INTERFACE:
				final TypeElement interfaceElement = (TypeElement) element;
				final InterfaceElementProcessor interfaceProcessor = new InterfaceElementProcessor(interfaceElement);
				outputFilepath = interfaceProcessor.getOutputFilepath();
				outputFile = new File(this.destinationDir.getValue(), outputFilepath);
				try {
					Files.createParentDirs(outputFile);
					Files.asCharSink(outputFile, java.nio.charset.StandardCharsets.UTF_8)
						.write(interfaceProcessor.toMarkdownString());
				}
				catch (IOException e) {
					log.error("Error writing file: {}", outputFilepath, e);
				}
				break;
			case CLASS:
				final TypeElement classElement = (TypeElement) element;
				final ClassElementProcessor classProcessor = new ClassElementProcessor(classElement);
				outputFilepath = classProcessor.getOutputFilepath();
				outputFile = new File(this.destinationDir.getValue(), outputFilepath);
				try {
					Files.createParentDirs(outputFile);
					Files.asCharSink(outputFile, java.nio.charset.StandardCharsets.UTF_8)
						.write(classProcessor.toMarkdownString());
				}
				catch (IOException e) {
					log.error("Error writing file: {}", outputFilepath, e);
				}
				break;
			case ENUM:
				final TypeElement enumElement = (TypeElement) element;
				log.info("Processing enum: {}", enumElement.getQualifiedName());
				break;
			case RECORD:
				final TypeElement recordElement = (TypeElement) element;
				log.info("Processing record: {}", recordElement.getQualifiedName());
				break;
			case ANNOTATION_TYPE:
				final TypeElement annotationElement = (TypeElement) element;
				log.info("Processing annotation type: {}", annotationElement.getQualifiedName());
				break;
			default:
				log.warn("Unhandled element kind: {}", element.getKind());
				break;
			}
		});
		return true;
	}

}
