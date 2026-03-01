package com.jhornsb2.doclet.generator.markdown.elements.factory;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.jhornsb2.doclet.generator.markdown.elements.AnnotationData;
import com.jhornsb2.doclet.generator.markdown.elements.ClassData;
import com.jhornsb2.doclet.generator.markdown.elements.EnumData;
import com.jhornsb2.doclet.generator.markdown.elements.IElementData;
import com.jhornsb2.doclet.generator.markdown.elements.InterfaceData;
import com.jhornsb2.doclet.generator.markdown.elements.ModuleData;
import com.jhornsb2.doclet.generator.markdown.elements.PackageData;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.TypeElement;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class IElementDataFactoryTest {

	@BeforeAll
	static void beforeAll() {
		FactoryTestFixtures.ensureDocletEnvironment();
	}

	@Test
	void createRoutesByElementKind() {
		IElementDataFactory factory = createFactory();

		TypeElement classElement = FactoryTestFixtures.minimalTypeElement(
			"MyClass",
			"example.MyClass",
			ElementKind.CLASS
		);
		TypeElement enumElement = FactoryTestFixtures.minimalTypeElement(
			"Status",
			"example.Status",
			ElementKind.ENUM
		);
		TypeElement interfaceElement = FactoryTestFixtures.minimalTypeElement(
			"Api",
			"example.Api",
			ElementKind.INTERFACE
		);
		TypeElement annotationElement = FactoryTestFixtures.minimalTypeElement(
			"MyAnnotation",
			"example.MyAnnotation",
			ElementKind.ANNOTATION_TYPE
		);
		Element packageElement = FactoryTestFixtures.packageElement(
			"example",
			"example",
			java.util.List.of()
		);
		Element moduleElement = FactoryTestFixtures.moduleElement(
			"example.module",
			"example.module",
			java.util.List.of()
		);

		IElementData classData = factory.create(classElement);
		IElementData enumData = factory.create(enumElement);
		IElementData interfaceData = factory.create(interfaceElement);
		IElementData annotationData = factory.create(annotationElement);
		IElementData packageData = factory.create(packageElement);
		IElementData moduleData = factory.create(moduleElement);

		assertInstanceOf(ClassData.class, classData);
		assertInstanceOf(EnumData.class, enumData);
		assertInstanceOf(InterfaceData.class, interfaceData);
		assertInstanceOf(AnnotationData.class, annotationData);
		assertInstanceOf(PackageData.class, packageData);
		assertInstanceOf(ModuleData.class, moduleData);
	}

	@Test
	void createReturnsCachedInstanceForSameElement() {
		IElementDataFactory factory = createFactory();
		TypeElement classElement = FactoryTestFixtures.minimalTypeElement(
			"MyClass",
			"example.MyClass",
			ElementKind.CLASS
		);

		IElementData first = factory.create(classElement);
		IElementData second = factory.create(classElement);

		assertSame(first, second);
	}

	@Test
	void createThrowsForUnsupportedElementKind() {
		IElementDataFactory factory = createFactory();
		Element unsupported = FactoryTestFixtures.plainElement(
			ElementKind.METHOD,
			"run()"
		);

		IllegalArgumentException exception = assertThrows(
			IllegalArgumentException.class,
			() -> factory.create(unsupported)
		);

		assertEquals(
			"Unsupported element kind: METHOD",
			exception.getMessage()
		);
	}

	private static IElementDataFactory createFactory() {
		ElementDataCache cache = new ElementDataCache();
		var docCommentUtil = FactoryTestFixtures.docCommentUtil();
		ModuleDataFactory moduleDataFactory = new ModuleDataFactory(
			cache,
			docCommentUtil
		);
		PackageDataFactory packageDataFactory = new PackageDataFactory(
			cache,
			docCommentUtil
		);
		InterfaceDataFactory interfaceDataFactory = new InterfaceDataFactory(
			cache,
			docCommentUtil
		);
		AnnotationDataFactory annotationDataFactory = new AnnotationDataFactory(
			cache,
			docCommentUtil
		);
		ClassDataFactory classDataFactory = new ClassDataFactory(
			cache,
			docCommentUtil
		);
		EnumDataFactory enumDataFactory = new EnumDataFactory(
			cache,
			docCommentUtil
		);

		return new IElementDataFactory(
			cache,
			moduleDataFactory,
			packageDataFactory,
			interfaceDataFactory,
			annotationDataFactory,
			classDataFactory,
			enumDataFactory
		);
	}
}
