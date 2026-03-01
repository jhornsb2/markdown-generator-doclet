package com.jhornsb2.doclet.generator.markdown.elements;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Set;
import org.junit.jupiter.api.Test;

class PackageDataTest {

	@Test
	void builderSetsAllFields() {
		Set<String> packageContents = Set.of("example.A", "example.B");

		PackageData packageData = PackageData.builder()
			.simpleName("example")
			.qualifiedName("example")
			.kind("package")
			.docComment("package docs")
			.packageContents(packageContents)
			.build();

		assertEquals("example", packageData.getSimpleName());
		assertEquals("example", packageData.getQualifiedName());
		assertEquals("package", packageData.getKind());
		assertEquals("package docs", packageData.getDocComment());
		assertEquals(packageContents, packageData.getPackageContents());
	}
}
