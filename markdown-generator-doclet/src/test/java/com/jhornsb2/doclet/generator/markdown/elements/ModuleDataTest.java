package com.jhornsb2.doclet.generator.markdown.elements;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Set;
import org.junit.jupiter.api.Test;

class ModuleDataTest {

	@Test
	void builderSetsAllFields() {
		Set<String> moduleContents = Set.of("example.alpha", "example.beta");

		ModuleData moduleData = ModuleData.builder()
			.simpleName("example.module")
			.qualifiedName("example.module")
			.kind("module")
			.docComment("module docs")
			.moduleContents(moduleContents)
			.build();

		assertEquals("example.module", moduleData.getSimpleName());
		assertEquals("example.module", moduleData.getQualifiedName());
		assertEquals("module", moduleData.getKind());
		assertEquals("module docs", moduleData.getDocComment());
		assertEquals(moduleContents, moduleData.getModuleContents());
	}
}
