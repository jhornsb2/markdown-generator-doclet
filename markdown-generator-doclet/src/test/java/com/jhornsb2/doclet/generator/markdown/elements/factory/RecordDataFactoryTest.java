package com.jhornsb2.doclet.generator.markdown.elements.factory;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

import com.jhornsb2.doclet.generator.markdown.elements.JavaModifier;
import com.jhornsb2.doclet.generator.markdown.elements.RecordData;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeMirror;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class RecordDataFactoryTest {

	@BeforeAll
	static void beforeAll() {
		FactoryTestFixtures.ensureDocletEnvironment();
	}

	@Test
	void createUncachedBuildsRecordDataWithResolvedParents() {
		TypeElement superClassElement = FactoryTestFixtures.minimalTypeElement(
			"Record",
			"java.lang.Record",
			ElementKind.CLASS
		);
		TypeElement interfaceOne = FactoryTestFixtures.minimalTypeElement(
			"Serializable",
			"java.io.Serializable",
			ElementKind.INTERFACE
		);
		TypeElement interfaceTwo = FactoryTestFixtures.minimalTypeElement(
			"Comparable",
			"java.lang.Comparable",
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

		TypeElement recordElement = FactoryTestFixtures.typeElement(
			"UserRecord",
			"example.UserRecord",
			ElementKind.RECORD,
			superClassMirror,
			List.of(
				interfaceOneMirror,
				FactoryTestFixtures.nonDeclaredTypeMirror(),
				interfaceTwoMirror,
				interfaceOneMirror
			),
			Set.of(Modifier.PUBLIC, Modifier.FINAL)
		);

		RecordDataFactory factory = new RecordDataFactory(
			new ElementDataCache(),
			FactoryTestFixtures.docCommentUtil()
		);
		RecordData recordData = factory.createUncached(recordElement);

		assertEquals("UserRecord", recordData.getSimpleName());
		assertEquals("example.UserRecord", recordData.getQualifiedName());
		assertEquals("record", recordData.getKind());
		assertEquals("", recordData.getDocComment());
		assertEquals(
			Optional.of("java.lang.Record"),
			recordData.getSuperClass()
		);
		assertEquals(
			Set.of("java.io.Serializable", "java.lang.Comparable"),
			recordData.getSuperInterfaces()
		);
		assertEquals(
			Set.of(JavaModifier.PUBLIC, JavaModifier.FINAL),
			recordData.getModifiers()
		);
	}

	@Test
	void createUsesCache() {
		ElementDataCache cache = new ElementDataCache();
		RecordDataFactory factory = new RecordDataFactory(
			cache,
			FactoryTestFixtures.docCommentUtil()
		);
		TypeElement recordElement = FactoryTestFixtures.minimalTypeElement(
			"MyRecord",
			"example.MyRecord",
			ElementKind.RECORD
		);

		RecordData first = factory.create(recordElement);
		RecordData second = factory.create(recordElement);

		assertSame(first, second);
	}
}
