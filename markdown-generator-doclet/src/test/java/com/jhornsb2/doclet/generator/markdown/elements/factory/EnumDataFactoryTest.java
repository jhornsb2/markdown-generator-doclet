package com.jhornsb2.doclet.generator.markdown.elements.factory;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

import com.jhornsb2.doclet.generator.markdown.elements.EnumData;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeMirror;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class EnumDataFactoryTest {

	@BeforeAll
	static void beforeAll() {
		FactoryTestFixtures.ensureDocletEnvironment();
	}

	@Test
	void createUncachedBuildsEnumDataWithHierarchy() {
		TypeElement superClassElement = FactoryTestFixtures.minimalTypeElement(
			"Enum",
			"java.lang.Enum",
			ElementKind.CLASS
		);
		TypeElement interfaceElement = FactoryTestFixtures.minimalTypeElement(
			"Serializable",
			"java.io.Serializable",
			ElementKind.INTERFACE
		);

		TypeMirror superClassMirror = FactoryTestFixtures.declaredType(
			superClassElement
		);
		TypeMirror interfaceMirror = FactoryTestFixtures.declaredType(
			interfaceElement
		);

		TypeElement enumElement = FactoryTestFixtures.typeElement(
			"Status",
			"example.Status",
			ElementKind.ENUM,
			superClassMirror,
			List.of(interfaceMirror)
		);

		EnumDataFactory factory = new EnumDataFactory(
			new ElementDataCache(),
			FactoryTestFixtures.docCommentUtil()
		);
		EnumData enumData = factory.createUncached(enumElement);

		assertEquals("Status", enumData.getSimpleName());
		assertEquals("example.Status", enumData.getQualifiedName());
		assertEquals("enum", enumData.getKind());
		assertEquals("", enumData.getDocComment());
		assertEquals(Optional.of("java.lang.Enum"), enumData.getSuperClass());
		assertEquals(
			Set.of("java.io.Serializable"),
			enumData.getSuperInterfaces()
		);
	}

	@Test
	void createUsesCache() {
		ElementDataCache cache = new ElementDataCache();
		EnumDataFactory factory = new EnumDataFactory(
			cache,
			FactoryTestFixtures.docCommentUtil()
		);
		TypeElement enumElement = FactoryTestFixtures.minimalTypeElement(
			"Status",
			"example.Status",
			ElementKind.ENUM
		);

		EnumData first = factory.create(enumElement);
		EnumData second = factory.create(enumElement);

		assertSame(first, second);
	}
}
