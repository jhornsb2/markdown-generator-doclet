package com.jhornsb2.doclet.generator.markdown;

import com.google.common.io.Files;
import com.jhornsb2.doclet.generator.markdown.elements.IElementData;
import com.jhornsb2.doclet.generator.markdown.elements.PackageData;
import com.jhornsb2.doclet.generator.markdown.elements.factory.AnnotationDataFactory;
import com.jhornsb2.doclet.generator.markdown.elements.factory.ClassDataFactory;
import com.jhornsb2.doclet.generator.markdown.elements.factory.ElementDataCache;
import com.jhornsb2.doclet.generator.markdown.elements.factory.EnumDataFactory;
import com.jhornsb2.doclet.generator.markdown.elements.factory.IElementDataFactory;
import com.jhornsb2.doclet.generator.markdown.elements.factory.InterfaceDataFactory;
import com.jhornsb2.doclet.generator.markdown.elements.factory.ModuleDataFactory;
import com.jhornsb2.doclet.generator.markdown.elements.factory.PackageDataFactory;
import com.jhornsb2.doclet.generator.markdown.elements.factory.RecordDataFactory;
import com.jhornsb2.doclet.generator.markdown.logging.DocletLogger;
import com.jhornsb2.doclet.generator.markdown.naming.QualifiedNameResolver;
import com.jhornsb2.doclet.generator.markdown.options.DocletOptions;
import com.jhornsb2.doclet.generator.markdown.template.BuiltInTemplateRegistry;
import com.jhornsb2.doclet.generator.markdown.template.DefaultTemplateRenderer;
import com.jhornsb2.doclet.generator.markdown.template.FileSystemTemplateRegistry;
import com.jhornsb2.doclet.generator.markdown.template.TemplateKind;
import com.jhornsb2.doclet.generator.markdown.template.TemplateRenderContext;
import com.jhornsb2.doclet.generator.markdown.template.TemplateRenderer;
import com.jhornsb2.doclet.generator.markdown.template.TemplateRegistry;
import com.jhornsb2.doclet.generator.markdown.template.resolver.CommonBookmarkResolver;
import com.jhornsb2.doclet.generator.markdown.template.resolver.PackageBookmarkResolver;
import com.jhornsb2.doclet.generator.markdown.util.DocCommentUtil;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ModuleElement;
import javax.lang.model.element.PackageElement;
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
			enumDataFactory
		);
		TemplateRegistry templateRegistry = createTemplateRegistry(docletOptions);
		TemplateRenderer templateRenderer = new DefaultTemplateRenderer(
			templateRegistry,
			List.of(new CommonBookmarkResolver(), new PackageBookmarkResolver())
		);

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
			.build();
	}

	private static TemplateRegistry createTemplateRegistry(
		@NonNull final DocletOptions docletOptions
	) {
		final TemplateRegistry builtInTemplateRegistry =
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
	TemplateRenderer templateRenderer;

	public void generate() {
		log.debug(String.format("%s.generate()", this.getClass().getName()));
		final Set<PackageElement> packageElements =
			this.collectPackageElements();
		final Map<String, PackageData> packageDataByQualifiedName =
			this.createPackageDataByQualifiedName(packageElements);
		final List<IElementData> allElements = packageDataByQualifiedName
			.values()
			.stream()
			.map(IElementData.class::cast)
			.toList();

		this.writePackageDocumentation(
			packageElements,
			packageDataByQualifiedName
		);
		log.info(
			"Generated project and {} package markdown files",
			packageDataByQualifiedName.size()
		);
	}

	private Set<PackageElement> collectPackageElements() {
		final Set<PackageElement> packageElements = new LinkedHashSet<>();
		for (Element includedElement : this.environment.getIncludedElements()) {
			if (
				includedElement.getKind() == ElementKind.PACKAGE &&
				includedElement instanceof PackageElement packageElement
			) {
				packageElements.add(packageElement);
				continue;
			}

			final PackageElement packageElement =
				this.environment.getElementUtils().getPackageOf(
					includedElement
				);
			if (packageElement != null && !packageElement.isUnnamed()) {
				packageElements.add(packageElement);
			}
		}
		return packageElements;
	}

	private Set<String> collectModuleNames() {
		final Set<String> moduleNames = new LinkedHashSet<>();
		for (Element includedElement : this.environment.getIncludedElements()) {
			final ModuleElement moduleElement =
				this.environment.getElementUtils().getModuleOf(includedElement);
			if (
				moduleElement != null &&
				!moduleElement.isUnnamed() &&
				!moduleElement.getQualifiedName().toString().isBlank()
			) {
				moduleNames.add(moduleElement.getQualifiedName().toString());
			}
		}
		return moduleNames;
	}

	private Map<String, PackageData> createPackageDataByQualifiedName(
		final Set<PackageElement> packageElements
	) {
		final Map<String, PackageData> packageDataByQualifiedName =
			new LinkedHashMap<>();
		for (PackageElement packageElement : packageElements) {
			final PackageData packageData =
				(PackageData) this.elementDataFactory.create(packageElement);
			packageDataByQualifiedName.put(
				packageData.getQualifiedName(),
				packageData
			);
		}
		return packageDataByQualifiedName;
	}

	private void writePackageDocumentation(
		final Set<PackageElement> packageElements,
		final Map<String, PackageData> packageDataByQualifiedName
	) {
		for (PackageElement packageElement : packageElements) {
			final String qualifiedName = packageElement
				.getQualifiedName()
				.toString();
			final PackageData packageData = packageDataByQualifiedName.get(
				qualifiedName
			);
			if (packageData == null) {
				continue;
			}

			final String outputFilepath =
				this.docletOptions.getOutputFilepathStrategy().forPackageElement(
					packageElement
				);

			final String markdown = this.templateRenderer.render(
				TemplateKind.PACKAGE,
				TemplateRenderContext.builder()
					.templateKind(TemplateKind.PACKAGE)
					.elementData(packageData)
					.outputRelativeFilepath(outputFilepath)
					.build()
			);
			this.writeMarkdownFile(outputFilepath, markdown);
		}
	}

	private void writeMarkdownFile(
		final String relativeOutputFilepath,
		final String markdownContent
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

	void extractElementData(@NonNull final Element element) {
		log.debug("Extracting data for element: {}", element.toString());

		final String qualifiedName = QualifiedNameResolver.qualifiedNameOf(
			element
		);

		// TODO update this so it uses the filepath resolver final String documentationFilePath;

		final IElementData elementData = elementDataFactory.create(element);
	}
}
