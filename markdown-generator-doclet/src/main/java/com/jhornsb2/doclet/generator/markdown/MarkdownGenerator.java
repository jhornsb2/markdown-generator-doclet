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
import jdk.javadoc.doclet.DocletEnvironment;
import lombok.Value;

@Value
public class MarkdownGenerator {

	/**
	 * Logger for this class.
	 */
	private static final DocletLogger log = DocletLogger.forClass(
		MarkdownGenerator.class
	);

	ElementDataCache elementDataCache = new ElementDataCache();
	InterfaceDataFactory interfaceDataFactory = new InterfaceDataFactory(
		elementDataCache
	);
	AnnotationDataFactory annotationDataFactory = new AnnotationDataFactory(
		elementDataCache
	);
	ClassDataFactory classDataFactory = new ClassDataFactory(elementDataCache);
	EnumDataFactory enumDataFactory = new EnumDataFactory(elementDataCache);
	PackageDataFactory packageDataFactory = new PackageDataFactory(
		elementDataCache
	);
	ModuleDataFactory moduleDataFactory = new ModuleDataFactory(
		elementDataCache
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

	DocletEnvironment environment;

	public void generate() {
		log.info("Running MarkdownGeneratorDoclet...");
		environment
			.getIncludedElements()
			.parallelStream()
			.map(elementDataFactory::create)
			.forEach(e -> log.info("Processed element: {}", e.toString()));
	}
}
