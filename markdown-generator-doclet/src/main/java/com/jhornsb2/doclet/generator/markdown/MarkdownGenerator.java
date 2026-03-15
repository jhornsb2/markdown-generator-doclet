package com.jhornsb2.doclet.generator.markdown;

import com.google.common.io.Files;
import com.jhornsb2.doclet.generator.markdown.elements.IElementData;
import com.jhornsb2.doclet.generator.markdown.elements.factory.AnnotationDataFactory;
import com.jhornsb2.doclet.generator.markdown.elements.factory.ClassDataFactory;
import com.jhornsb2.doclet.generator.markdown.elements.factory.ElementDataCache;
import com.jhornsb2.doclet.generator.markdown.elements.factory.EnumDataFactory;
import com.jhornsb2.doclet.generator.markdown.elements.factory.FieldDataFactory;
import com.jhornsb2.doclet.generator.markdown.elements.factory.IElementDataFactory;
import com.jhornsb2.doclet.generator.markdown.elements.factory.InterfaceDataFactory;
import com.jhornsb2.doclet.generator.markdown.elements.factory.ModuleDataFactory;
import com.jhornsb2.doclet.generator.markdown.elements.factory.PackageDataFactory;
import com.jhornsb2.doclet.generator.markdown.elements.factory.RecordDataFactory;
import com.jhornsb2.doclet.generator.markdown.logging.DocletLogger;
import com.jhornsb2.doclet.generator.markdown.lookup.QualifiedNameIndex;
import com.jhornsb2.doclet.generator.markdown.lookup.QualifiedNameIndexEntry;
import com.jhornsb2.doclet.generator.markdown.naming.QualifiedNameResolver;
import com.jhornsb2.doclet.generator.markdown.options.DocletOptions;
import com.jhornsb2.doclet.generator.markdown.template.BuiltInTemplateRegistry;
import com.jhornsb2.doclet.generator.markdown.template.DefaultTemplateRenderer;
import com.jhornsb2.doclet.generator.markdown.template.FileSystemTemplateRegistry;
import com.jhornsb2.doclet.generator.markdown.template.ITemplateRegistry;
import com.jhornsb2.doclet.generator.markdown.template.ITemplateRenderer;
import com.jhornsb2.doclet.generator.markdown.template.resolver.CommonBookmarkResolver;
import com.jhornsb2.doclet.generator.markdown.template.resolver.PackageBookmarkResolver;
import com.jhornsb2.doclet.generator.markdown.template.resolver.ProjectBookmarkResolver;
import com.jhornsb2.doclet.generator.markdown.util.DocCommentUtil;
import com.jhornsb2.doclet.generator.markdown.util.StringUtil;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Stream;
import javax.lang.model.element.Element;
import jdk.javadoc.doclet.DocletEnvironment;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder(access = AccessLevel.PRIVATE)
public class MarkdownGenerator {

	/**
	 * Logger for this class.
	 */
	private static final DocletLogger log = DocletLogger.forClass(
		MarkdownGenerator.class
	);

	/**
	 * Static constructor to create a {@link MarkdownGenerator} instance from a
	 * {@link DocletEnvironment}.
	 *
	 * @param environment The {@link DocletEnvironment} to use for generating
	 *                    Markdown.
	 * @return A new instance of {@link MarkdownGenerator}.
	 */
	public static MarkdownGenerator createFor(
		@NonNull final DocletEnvironment environment,
		@NonNull final DocletOptions docletOptions
	) {
		DocCommentUtil docCommentUtil = new DocCommentUtil(environment);
		ElementDataCache elementDataCache = new ElementDataCache();
		InterfaceDataFactory interfaceDataFactory = new InterfaceDataFactory(
			elementDataCache,
			docCommentUtil
		);
		AnnotationDataFactory annotationDataFactory = new AnnotationDataFactory(
			elementDataCache,
			docCommentUtil
		);
		ClassDataFactory classDataFactory = new ClassDataFactory(
			elementDataCache,
			docCommentUtil
		);
		RecordDataFactory recordDataFactory = new RecordDataFactory(
			elementDataCache,
			docCommentUtil
		);
		EnumDataFactory enumDataFactory = new EnumDataFactory(
			elementDataCache,
			docCommentUtil
		);
		FieldDataFactory fieldDataFactory = new FieldDataFactory(
			elementDataCache,
			docCommentUtil
		);
		PackageDataFactory packageDataFactory = new PackageDataFactory(
			elementDataCache,
			docCommentUtil
		);
		ModuleDataFactory moduleDataFactory = new ModuleDataFactory(
			elementDataCache,
			docCommentUtil
		);
		IElementDataFactory elementDataFactory = new IElementDataFactory(
			elementDataCache,
			moduleDataFactory,
			packageDataFactory,
			interfaceDataFactory,
			annotationDataFactory,
			classDataFactory,
			recordDataFactory,
			enumDataFactory,
			fieldDataFactory
		);
		ITemplateRegistry templateRegistry = createTemplateRegistry(
			docletOptions
		);
		ITemplateRenderer templateRenderer = new DefaultTemplateRenderer(
			templateRegistry,
			List.of(
				new CommonBookmarkResolver(),
				new ProjectBookmarkResolver(),
				new PackageBookmarkResolver()
			)
		);
		QualifiedNameIndex qualifiedNameIndex = new QualifiedNameIndex();

		return MarkdownGenerator.builder()
			.environment(environment)
			.docletOptions(docletOptions)
			.docCommentUtil(docCommentUtil)
			.elementDataCache(elementDataCache)
			.interfaceDataFactory(interfaceDataFactory)
			.annotationDataFactory(annotationDataFactory)
			.classDataFactory(classDataFactory)
			.recordDataFactory(recordDataFactory)
			.enumDataFactory(enumDataFactory)
			.packageDataFactory(packageDataFactory)
			.moduleDataFactory(moduleDataFactory)
			.elementDataFactory(elementDataFactory)
			.templateRenderer(templateRenderer)
			.qualifiedNameIndex(qualifiedNameIndex)
			.build();
	}

	private static ITemplateRegistry createTemplateRegistry(
		@NonNull final DocletOptions docletOptions
	) {
		final ITemplateRegistry builtInTemplateRegistry =
			new BuiltInTemplateRegistry();
		if (!docletOptions.hasTemplateDirectory()) {
			return builtInTemplateRegistry;
		}

		return new FileSystemTemplateRegistry(
			Path.of(docletOptions.getTemplateDirectory()),
			builtInTemplateRegistry
		);
	}

	/**
	 * The environment provided by the doclet, which contains information about
	 * the elements to be processed and other relevant data for generating
	 * Markdown.
	 */
	DocletEnvironment environment;
	/**
	 * Options provided to the doclet, which may include various settings and
	 * configurations for how the Markdown should be generated.
	 */
	DocletOptions docletOptions;
	/**
	 * Utility for working with Javadoc comments, providing methods to extract
	 * and process documentation from elements.
	 */
	DocCommentUtil docCommentUtil;

	/**
	 * Cache for storing and retrieving element data, optimizing the generation
	 * process by avoiding redundant computations.
	 */
	ElementDataCache elementDataCache;

	/**
	 * Index for looking up elements and their associated data by qualified name,
	 * facilitating cross-referencing and linking within the generated
	 * documentation.
	 */
	QualifiedNameIndex qualifiedNameIndex;

	/**
	 * Factory for creating data representations of interface elements.
	 */
	InterfaceDataFactory interfaceDataFactory;
	/**
	 * Factory for creating data representations of annotation type elements.
	 */
	AnnotationDataFactory annotationDataFactory;
	/**
	 * Factory for creating data representations of class elements.
	 */
	ClassDataFactory classDataFactory;
	/**
	 * Factory for creating data representations of record elements.
	 */
	RecordDataFactory recordDataFactory;
	/**
	 * Factory for creating data representations of enum elements.
	 */
	EnumDataFactory enumDataFactory;
	/**
	 * Factory for creating data representations of package elements.
	 */
	PackageDataFactory packageDataFactory;
	/**
	 * Factory for creating data representations of module elements.
	 */
	ModuleDataFactory moduleDataFactory;
	/**
	 * Factory for creating data representations of generic elements.
	 */
	IElementDataFactory elementDataFactory;

	/**
	 * Renders markdown content from templates and bookmark resolvers.
	 */
	ITemplateRenderer templateRenderer;

	/**
	 * Executes markdown generation for all included package elements.
	 * <p>
	 * The method collects package elements, builds package metadata, renders
	 * markdown pages, and writes those pages to the configured destination.
	 */
	public void generate() {
		log.debug(String.format("%s.generate()", this.getClass().getName()));

		this.environment.getIncludedElements()
			.parallelStream()
			/*
			 * Expand elements to include enclosed members for type elements,
			 * since these members should also be processed for markdown
			 * generation.
			 */
			.flatMap(this::expandToProcessableElements)
			/*
			 * Build qualified name index entries for each element, which will
			 * be used for cross-referencing and linking within the generated
			 * documentation.
			 */
			.map(this::buildQualifiedNameIndexEntry)
			.forEach(this.qualifiedNameIndex::addEntry);

		log.debug(
			StringUtil.formatLombokToString(this.qualifiedNameIndex.toString())
		);
	}

	/**
	 * Expands an element into a stream of elements that should be processed for
	 * markdown generation. For most element types, this will just return a
	 * stream containing the element itself. Type elements (classes, interfaces,
	 * enums, records) will be expanded to include their enclosed method, field,
	 * constructor, and enum constant elements.
	 *
	 * @param element the element to expand
	 * @return a stream of elements to be processed for markdown generation
	 */
	private Stream<Element> expandToProcessableElements(
		@NonNull final Element element
	) {
		return switch (element.getKind()) {
			case CLASS, INTERFACE, ENUM, RECORD -> Stream.of(
				Stream.of(element),
				element
					.getEnclosedElements()
					.stream()
					.map(Element.class::cast)
					.filter(e ->
						switch (e.getKind()) {
							case METHOD, FIELD -> true;
							default -> false;
						}
					)
			).flatMap(Function.identity());
			default -> Stream.of(element);
		};
	}

	private QualifiedNameIndexEntry buildQualifiedNameIndexEntry(
		@NonNull final Element element
	) {
		String qualifiedName = QualifiedNameResolver.qualifiedNameOf(element);
		String filePath =
			this.docletOptions.getOutputFilepathStrategy().forElement(element);
		IElementData elementData = this.elementDataFactory.create(element);
		return QualifiedNameIndexEntry.builder()
			.qualifiedName(qualifiedName)
			.filePath(filePath)
			.element(element)
			.elementData(elementData)
			.build();
	}

	/**
	 * Writes rendered markdown content to a destination file.
	 *
	 * @param relativeOutputFilepath output path relative to destination root.
	 * @param markdownContent rendered markdown content.
	 */
	private void writeMarkdownFile(
		@NonNull final String relativeOutputFilepath,
		@NonNull final String markdownContent
	) {
		final File outputFile = new File(
			this.docletOptions.getDestinationDirectory(),
			relativeOutputFilepath
		);
		try {
			Files.createParentDirs(outputFile);
			Files.asCharSink(outputFile, StandardCharsets.UTF_8).write(
				markdownContent
			);
		} catch (IOException exception) {
			throw new IllegalStateException(
				"Failed to write markdown file: " + outputFile,
				exception
			);
		}
	}

	/**
	 * Resolves a display name for project-level documentation.
	 *
	 * @return project name derived from the current working directory.
	 */
	private String resolveProjectName() {
		final String userDirectory = System.getProperty("user.dir", "");
		if (userDirectory.isBlank()) {
			return "Project";
		}

		final Path directoryPath = Path.of(userDirectory).normalize();
		final Path fileName = directoryPath.getFileName();
		if (fileName == null) {
			return "Project";
		}

		final String projectName = fileName.toString().trim();
		return projectName.isBlank() ? "Project" : projectName;
	}
}
