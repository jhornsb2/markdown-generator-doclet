package com.jhornsb2.doclet.generator.markdown.elements.factory;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

import com.jhornsb2.doclet.generator.markdown.elements.JavaModifier;
import com.jhornsb2.doclet.generator.markdown.elements.MethodData;
import java.util.List;
import java.util.Set;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class MethodDataFactoryTest {

	@BeforeAll
	static void beforeAll() {
		FactoryTestFixtures.ensureDocletEnvironment();
	}

	@Test
	void createUncachedBuildsMethodDataWithReturnTypeDeclaringTypeAndParameters() {
		TypeElement ownerType = FactoryTestFixtures.minimalTypeElement(
			"MyClass",
			"example.MyClass",
			ElementKind.CLASS
		);
		TypeMirror returnType = FactoryTestFixtures.typeMirror(
			"java.lang.String",
			TypeKind.DECLARED
		);
		VariableElement parameter = FactoryTestFixtures.variableElement(
			"count",
			ElementKind.PARAMETER,
			FactoryTestFixtures.typeMirror("int", TypeKind.INT),
			null,
			Set.of()
		);
		ExecutableElement methodElement = FactoryTestFixtures.executableElement(
			"value",
			returnType,
			ownerType,
			List.of(parameter),
			Set.of(Modifier.PUBLIC, Modifier.STATIC)
		);

		MethodDataFactory factory = new MethodDataFactory(
			new ElementDataCache(),
			FactoryTestFixtures.docCommentUtil()
		);
		MethodData methodData = factory.createUncached(methodElement);

		assertEquals("value", methodData.getSimpleName());
		assertEquals(
			"example.MyClass#value(int)",
			methodData.getQualifiedName()
		);
		assertEquals("method", methodData.getKind());
		assertEquals("", methodData.getDocComment());
		assertEquals("java.lang.String", methodData.getReturnType());
		assertEquals("example.MyClass", methodData.getDeclaringType());
		assertEquals(1, methodData.getParameters().size());
		assertEquals(
			Set.of(JavaModifier.PUBLIC, JavaModifier.STATIC),
			methodData.getModifiers()
		);
	}

	@Test
	void createUsesCache() {
		ElementDataCache cache = new ElementDataCache();
		MethodDataFactory factory = new MethodDataFactory(
			cache,
			FactoryTestFixtures.docCommentUtil()
		);
		TypeElement ownerType = FactoryTestFixtures.minimalTypeElement(
			"MyClass",
			"example.MyClass",
			ElementKind.CLASS
		);
		ExecutableElement methodElement = FactoryTestFixtures.executableElement(
			"value",
			FactoryTestFixtures.typeMirror("int", TypeKind.INT),
			ownerType,
			List.of(),
			Set.of(Modifier.PRIVATE)
		);

		MethodData first = factory.create(methodElement);
		MethodData second = factory.create(methodElement);

		assertSame(first, second);
	}
}
