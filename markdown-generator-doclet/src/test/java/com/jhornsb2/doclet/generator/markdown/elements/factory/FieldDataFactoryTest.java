package com.jhornsb2.doclet.generator.markdown.elements.factory;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

import com.jhornsb2.doclet.generator.markdown.elements.FieldData;
import com.jhornsb2.doclet.generator.markdown.elements.JavaModifier;
import java.util.Set;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class FieldDataFactoryTest {

	@BeforeAll
	static void beforeAll() {
		FactoryTestFixtures.ensureDocletEnvironment();
	}

	@Test
	void createUncachedBuildsFieldDataWithDeclaringTypeAndReturnType() {
		TypeElement ownerType = FactoryTestFixtures.minimalTypeElement(
			"MyClass",
			"example.MyClass",
			ElementKind.CLASS
		);
		TypeMirror fieldType = FactoryTestFixtures.typeMirror(
			"java.lang.String",
			TypeKind.DECLARED
		);
		VariableElement fieldElement = FactoryTestFixtures.variableElement(
			"value",
			ElementKind.FIELD,
			fieldType,
			ownerType,
			Set.of(Modifier.PRIVATE, Modifier.FINAL)
		);

		FieldDataFactory factory = new FieldDataFactory(
			new ElementDataCache(),
			FactoryTestFixtures.docCommentUtil()
		);
		FieldData fieldData = factory.createUncached(fieldElement);

		assertEquals("value", fieldData.getSimpleName());
		assertEquals("example.MyClass#value", fieldData.getQualifiedName());
		assertEquals("field", fieldData.getKind());
		assertEquals("", fieldData.getDocComment());
		assertEquals("java.lang.String", fieldData.getReturnType());
		assertEquals("example.MyClass", fieldData.getDeclaringType());
		assertEquals(
			Set.of(JavaModifier.PRIVATE, JavaModifier.FINAL),
			fieldData.getModifiers()
		);
	}

	@Test
	void createUsesCache() {
		ElementDataCache cache = new ElementDataCache();
		FieldDataFactory factory = new FieldDataFactory(
			cache,
			FactoryTestFixtures.docCommentUtil()
		);
		TypeElement ownerType = FactoryTestFixtures.minimalTypeElement(
			"MyClass",
			"example.MyClass",
			ElementKind.CLASS
		);
		VariableElement fieldElement = FactoryTestFixtures.variableElement(
			"value",
			ElementKind.FIELD,
			FactoryTestFixtures.typeMirror("int", TypeKind.INT),
			ownerType,
			Set.of(Modifier.PRIVATE)
		);

		FieldData first = factory.create(fieldElement);
		FieldData second = factory.create(fieldElement);

		assertSame(first, second);
	}
}
