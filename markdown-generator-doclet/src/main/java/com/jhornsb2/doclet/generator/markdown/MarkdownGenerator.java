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
import com.jhornsb2.doclet.generator.markdown.util.DocCommentUtil;
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
		@NonNull final DocletEnvironment environment
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
		EnumDataFactory enumDataFactory = new EnumDataFactory(
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
			enumDataFactory
		);

		return MarkdownGenerator.builder()
			.environment(environment)
			.docCommentUtil(docCommentUtil)
			.elementDataCache(elementDataCache)
			.interfaceDataFactory(interfaceDataFactory)
			.annotationDataFactory(annotationDataFactory)
			.classDataFactory(classDataFactory)
			.enumDataFactory(enumDataFactory)
			.packageDataFactory(packageDataFactory)
			.moduleDataFactory(moduleDataFactory)
			.elementDataFactory(elementDataFactory)
			.build();
	}

	/**
	 * The environment provided by the doclet, which contains information about
	 * the elements to be processed and other relevant data for generating
	 * Markdown.
	 */
	DocletEnvironment environment;
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

	public void generate() {
		log.info("Running MarkdownGeneratorDoclet...");
		environment
			.getIncludedElements()
			.parallelStream()
			.map(elementDataFactory::create)
			.forEach(e -> log.info("Processed element: {}", e.toString()));
	}
}
