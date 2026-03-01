package com.jhornsb2.doclet.generator.markdown.elements.factory;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

import com.jhornsb2.doclet.generator.markdown.elements.InterfaceData;
import java.util.List;
import java.util.Set;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeMirror;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class InterfaceDataFactoryTest {

	@BeforeAll
	static void beforeAll() {
		FactoryTestFixtures.ensureDocletEnvironment();
	}

	@Test
	void createUncachedBuildsInterfaceDataWithSuperInterfaces() {
		TypeElement firstInterface = FactoryTestFixtures.minimalTypeElement(
			"Closeable",
			"java.io.Closeable",
			ElementKind.INTERFACE
		);
		TypeElement secondInterface = FactoryTestFixtures.minimalTypeElement(
			"Serializable",
			"java.io.Serializable",
			ElementKind.INTERFACE
		);

		TypeMirror firstInterfaceMirror = FactoryTestFixtures.declaredType(
			firstInterface
		);
		TypeMirror secondInterfaceMirror = FactoryTestFixtures.declaredType(
			secondInterface
		);

		TypeElement interfaceElement = FactoryTestFixtures.typeElement(
			"MyInterface",
			"example.MyInterface",
			ElementKind.INTERFACE,
			FactoryTestFixtures.noneTypeMirror(),
			List.of(
				firstInterfaceMirror,
				FactoryTestFixtures.nonDeclaredTypeMirror(),
				secondInterfaceMirror
			)
		);

		InterfaceDataFactory factory = new InterfaceDataFactory(
			new ElementDataCache()
		);
		InterfaceData interfaceData = factory.createUncached(interfaceElement);

		assertEquals("MyInterface", interfaceData.getSimpleName());
		assertEquals("example.MyInterface", interfaceData.getQualifiedName());
		assertEquals("interface", interfaceData.getKind());
		assertEquals("", interfaceData.getDocComment());
		assertEquals(
			Set.of("java.io.Closeable", "java.io.Serializable"),
			interfaceData.getSuperInterfaces()
		);
	}

	@Test
	void createUsesCache() {
		ElementDataCache cache = new ElementDataCache();
		InterfaceDataFactory factory = new InterfaceDataFactory(cache);
		TypeElement interfaceElement = FactoryTestFixtures.minimalTypeElement(
			"MyInterface",
			"example.MyInterface",
			ElementKind.INTERFACE
		);

		InterfaceData first = factory.create(interfaceElement);
		InterfaceData second = factory.create(interfaceElement);

		assertSame(first, second);
	}
}
