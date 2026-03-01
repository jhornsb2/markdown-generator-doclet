package com.jhornsb2.doclet.generator.markdown.elements.factory;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

import com.jhornsb2.doclet.generator.markdown.elements.ClassData;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeMirror;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class ClassDataFactoryTest {

	@BeforeAll
	static void beforeAll() {
		FactoryTestFixtures.ensureDocletEnvironment();
	}

	@Test
	void createUncachedBuildsClassDataWithResolvedParents() {
		TypeElement superClassElement = FactoryTestFixtures.minimalTypeElement(
			"Base",
			"example.Base",
			ElementKind.CLASS
		);
		TypeElement interfaceOne = FactoryTestFixtures.minimalTypeElement(
			"Runnable",
			"java.lang.Runnable",
			ElementKind.INTERFACE
		);
		TypeElement interfaceTwo = FactoryTestFixtures.minimalTypeElement(
			"Serializable",
			"java.io.Serializable",
			ElementKind.INTERFACE
		);

		TypeMirror superClassMirror = FactoryTestFixtures.declaredType(
			superClassElement
		);
		TypeMirror interfaceOneMirror = FactoryTestFixtures.declaredType(
			interfaceOne
		);
		TypeMirror interfaceTwoMirror = FactoryTestFixtures.declaredType(
			interfaceTwo
		);

		TypeElement classElement = FactoryTestFixtures.typeElement(
			"Child",
			"example.Child",
			ElementKind.CLASS,
			superClassMirror,
			List.of(
				interfaceOneMirror,
				FactoryTestFixtures.nonDeclaredTypeMirror(),
				interfaceTwoMirror,
				interfaceOneMirror
			)
		);

		ClassDataFactory factory = new ClassDataFactory(
			new ElementDataCache(),
			FactoryTestFixtures.docCommentUtil()
		);
		ClassData classData = factory.createUncached(classElement);

		assertEquals("Child", classData.getSimpleName());
		assertEquals("example.Child", classData.getQualifiedName());
		assertEquals("class", classData.getKind());
		assertEquals("", classData.getDocComment());
		assertEquals(Optional.of("example.Base"), classData.getSuperClass());
		assertEquals(
			Set.of("java.lang.Runnable", "java.io.Serializable"),
			classData.getSuperInterfaces()
		);
	}

	@Test
	void createUsesCache() {
		ElementDataCache cache = new ElementDataCache();
		ClassDataFactory factory = new ClassDataFactory(
			cache,
			FactoryTestFixtures.docCommentUtil()
		);
		TypeElement classElement = FactoryTestFixtures.minimalTypeElement(
			"MyClass",
			"example.MyClass",
			ElementKind.CLASS
		);

		ClassData first = factory.create(classElement);
		ClassData second = factory.create(classElement);

		assertSame(first, second);
	}
}
