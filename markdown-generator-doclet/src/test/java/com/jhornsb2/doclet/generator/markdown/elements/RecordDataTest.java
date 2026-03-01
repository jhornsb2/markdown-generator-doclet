package com.jhornsb2.doclet.generator.markdown.elements;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;
import java.util.Set;
import org.junit.jupiter.api.Test;

class RecordDataTest {

	@Test
	void builderDefaultsToEmptyHierarchyData() {
		RecordData recordData = RecordData.builder()
			.simpleName("MyRecord")
			.qualifiedName("example.MyRecord")
			.kind("record")
			.docComment("")
			.build();

		assertEquals(Optional.empty(), recordData.getSuperClass());
		assertTrue(recordData.getSuperInterfaces().isEmpty());
	}

	@Test
	void builderSetsHierarchyData() {
		RecordData recordData = RecordData.builder()
			.simpleName("MyRecord")
			.qualifiedName("example.MyRecord")
			.kind("record")
			.docComment("docs")
			.superClass(Optional.of("java.lang.Record"))
			.superInterfaces(Set.of("java.io.Serializable"))
			.build();

		assertEquals(
			Optional.of("java.lang.Record"),
			recordData.getSuperClass()
		);
		assertEquals(
			Set.of("java.io.Serializable"),
			recordData.getSuperInterfaces()
		);
	}
}
