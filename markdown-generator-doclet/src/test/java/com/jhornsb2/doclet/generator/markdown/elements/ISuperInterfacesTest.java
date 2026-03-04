package com.jhornsb2.doclet.generator.markdown.elements;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class ISuperInterfacesTest {

	@Test
	void defaultsToNoSuperInterfaces() {
		ISuperInterfaces data = InterfaceData.builder()
			.simpleName("Api")
			.qualifiedName("com.example.Api")
			.kind("interface")
			.docComment("")
			.build();

		assertTrue(data.getSuperInterfaces().isEmpty());
	}

	@Test
	void returnsConfiguredSuperInterfaces() {
		ISuperInterfaces data = InterfaceData.builder()
			.simpleName("Api")
			.qualifiedName("com.example.Api")
			.kind("interface")
			.docComment("")
			.superInterfaces(java.util.Set.of("java.io.Serializable"))
			.build();

		assertEquals(
			java.util.Set.of("java.io.Serializable"),
			data.getSuperInterfaces()
		);
	}
}
