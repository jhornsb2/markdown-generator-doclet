package com.jhornsb2.doclet.generator.markdown.elements;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Set;
import org.junit.jupiter.api.Test;

class ProjectDataTest {

	@Test
	void builderSetsAllFields() {
		Set<String> modules = Set.of("example.alpha", "example.beta");
		Set<String> packages = Set.of("com.example", "com.example.sub");

		ProjectData projectData = ProjectData.builder()
			.simpleName("example-project")
			.kind("project")
			.description("project docs")
			.modules(modules)
			.packages(packages)
			.build();

		assertEquals("example-project", projectData.getSimpleName());
		assertEquals("project", projectData.getKind());
		assertEquals("project docs", projectData.getDescription());
		assertEquals(modules, projectData.getModules());
		assertEquals(packages, projectData.getPackages());
	}
}
