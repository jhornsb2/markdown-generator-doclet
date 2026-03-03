package com.documentation.generator.java.fixtures.records;

import com.documentation.generator.java.fixtures.annotations.Range;
import com.documentation.generator.java.fixtures.contracts.Identifiable;

/**
 * Immutable person record fixture.
 *
 * @param id stable identifier
 * @param name display name
 * @param age age value
 */
public record PersonRecord(
	String id,
	String name,
	@Range(min = 0, max = 140) int age
) implements Identifiable<String> {
	/**
	 * Validates canonical constructor arguments.
	 */
	public PersonRecord {
		if (id == null || id.isBlank()) {
			throw new IllegalArgumentException("id must not be blank");
		}
		if (name == null || name.isBlank()) {
			throw new IllegalArgumentException("name must not be blank");
		}
	}
}
