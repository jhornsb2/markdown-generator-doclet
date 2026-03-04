package com.jhornsb2.doclet.generator.markdown.elements;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class IModifiersTest {

	@Test
	void defaultsToEmptyModifierSetWhenNotSpecified() {
		IModifiers data = InterfaceData.builder()
			.simpleName("Api")
			.qualifiedName("com.example.Api")
			.kind("interface")
			.docComment("")
			.build();

		assertTrue(data.getModifiers().isEmpty());
	}

	@Test
	void returnsConfiguredModifiers() {
		IModifiers data = InterfaceData.builder()
			.simpleName("Api")
			.qualifiedName("com.example.Api")
			.kind("interface")
			.docComment("")
			.modifiers(
				java.util.Set.of(JavaModifier.PUBLIC, JavaModifier.STATIC)
			)
			.build();

		assertEquals(
			java.util.Set.of(JavaModifier.PUBLIC, JavaModifier.STATIC),
			data.getModifiers()
		);
	}
}
