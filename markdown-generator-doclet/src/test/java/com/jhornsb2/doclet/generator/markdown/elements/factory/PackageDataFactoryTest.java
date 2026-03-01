package com.jhornsb2.doclet.generator.markdown.elements.factory;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

import com.jhornsb2.doclet.generator.markdown.elements.PackageData;
import java.util.List;
import java.util.Set;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class PackageDataFactoryTest {

	@BeforeAll
	static void beforeAll() {
		FactoryTestFixtures.ensureDocletEnvironment();
	}

	@Test
	void createUncachedCollectsSupportedPackageContents() {
		TypeElement classElement = FactoryTestFixtures.minimalTypeElement(
			"A",
			"example.A",
			ElementKind.CLASS
		);
		TypeElement interfaceElement = FactoryTestFixtures.minimalTypeElement(
			"B",
			"example.B",
			ElementKind.INTERFACE
		);
		Element fieldElement = FactoryTestFixtures.plainElement(
			ElementKind.FIELD,
			"ignored"
		);

		PackageElement packageElement = FactoryTestFixtures.packageElement(
			"example",
			"example",
			List.of(classElement, interfaceElement, fieldElement, classElement)
		);

		PackageDataFactory factory = new PackageDataFactory(new ElementDataCache());
		PackageData packageData = factory.createUncached(packageElement);

		assertEquals("example", packageData.getSimpleName());
		assertEquals("example", packageData.getQualifiedName());
		assertEquals("package", packageData.getKind());
		assertEquals("", packageData.getDocComment());
		assertEquals(Set.of("example.A", "example.B"), packageData.getPackageContents());
	}

	@Test
	void createUsesCache() {
		ElementDataCache cache = new ElementDataCache();
		PackageDataFactory factory = new PackageDataFactory(cache);
		PackageElement packageElement = FactoryTestFixtures.packageElement(
			"example",
			"example",
			List.<Element>of()
		);

		PackageData first = factory.create(packageElement);
		PackageData second = factory.create(packageElement);

		assertSame(first, second);
	}
}
