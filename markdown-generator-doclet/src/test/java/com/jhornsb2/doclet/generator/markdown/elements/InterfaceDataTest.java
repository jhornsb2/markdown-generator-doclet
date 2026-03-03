package com.jhornsb2.doclet.generator.markdown.elements;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Set;
import org.junit.jupiter.api.Test;

class InterfaceDataTest {

	@Test
	void builderDefaultsToEmptySuperInterfaces() {
		InterfaceData interfaceData = InterfaceData.builder()
			.simpleName("MyInterface")
			.qualifiedName("example.MyInterface")
			.kind("interface")
			.docComment("")
			.build();

		assertTrue(interfaceData.getSuperInterfaces().isEmpty());
		assertTrue(interfaceData.getModifiers().isEmpty());
	}

	@Test
	void builderSetsSuperInterfaces() {
		InterfaceData interfaceData = InterfaceData.builder()
			.simpleName("MyInterface")
			.qualifiedName("example.MyInterface")
			.kind("interface")
			.docComment("docs")
			.modifiers(Set.of(JavaModifier.PUBLIC, JavaModifier.SEALED))
			.superInterfaces(
				Set.of("java.io.Closeable", "java.io.Serializable")
			)
			.build();

		assertEquals(
			Set.of("java.io.Closeable", "java.io.Serializable"),
			interfaceData.getSuperInterfaces()
		);
		assertEquals(
			Set.of(JavaModifier.PUBLIC, JavaModifier.SEALED),
			interfaceData.getModifiers()
		);
	}
}
