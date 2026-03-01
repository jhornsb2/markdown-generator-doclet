package com.jhornsb2.doclet.generator.markdown.elements.factory;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

import com.jhornsb2.doclet.generator.markdown.elements.ModuleData;
import java.util.List;
import java.util.Set;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.PackageElement;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class ModuleDataFactoryTest {

	@BeforeAll
	static void beforeAll() {
		FactoryTestFixtures.ensureDocletEnvironment();
	}

	@Test
	void createUncachedCollectsPackageNamesFromModuleContents() {
		PackageElement firstPackage = FactoryTestFixtures.packageElement(
			"alpha",
			"example.alpha",
			List.of()
		);
		PackageElement secondPackage = FactoryTestFixtures.packageElement(
			"beta",
			"example.beta",
			List.of()
		);
		Element nonPackageElement = FactoryTestFixtures.plainElement(
			ElementKind.METHOD,
			"ignored()"
		);

		Element moduleElement = FactoryTestFixtures.moduleElement(
			"example.module",
			"example.module",
			List.of(
				firstPackage,
				nonPackageElement,
				secondPackage,
				firstPackage
			)
		);

		ModuleDataFactory factory = new ModuleDataFactory(
			new ElementDataCache(),
			FactoryTestFixtures.docCommentUtil()
		);
		ModuleData moduleData = factory.createUncached(moduleElement);

		assertEquals("example.module", moduleData.getSimpleName());
		assertEquals("example.module", moduleData.getQualifiedName());
		assertEquals("module", moduleData.getKind());
		assertEquals("", moduleData.getDocComment());
		assertEquals(
			Set.of("example.alpha", "example.beta"),
			moduleData.getModuleContents()
		);
	}

	@Test
	void createUsesCache() {
		ElementDataCache cache = new ElementDataCache();
		ModuleDataFactory factory = new ModuleDataFactory(
			cache,
			FactoryTestFixtures.docCommentUtil()
		);
		Element moduleElement = FactoryTestFixtures.moduleElement(
			"example.module",
			"example.module",
			List.of()
		);

		ModuleData first = factory.create(moduleElement);
		ModuleData second = factory.create(moduleElement);

		assertSame(first, second);
	}
}
