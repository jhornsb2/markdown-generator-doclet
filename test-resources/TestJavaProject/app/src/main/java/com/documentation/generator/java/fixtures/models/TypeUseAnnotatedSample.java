package com.documentation.generator.java.fixtures.models;

import com.documentation.generator.java.fixtures.annotations.Label;
import java.util.ArrayList;
import java.util.List;

/**
 * Type-use annotation fixture.
 */
@Label("sample")
@Label("type-use")
public class TypeUseAnnotatedSample {

	private @Label("id-type") String identifier;
	private List<@Label("list-number") Integer> values;

	/**
	 * Creates a type-use annotation sample.
	 *
	 * @param identifier initial identifier
	 */
	public TypeUseAnnotatedSample(@Label("input") String identifier) {
		this.identifier = identifier;
		this.values = new ArrayList<>();
	}

	/**
	 * Returns values.
	 *
	 * @return values list
	 */
	public List<@Label("return") Integer> getValues() {
		return values;
	}

	/**
	 * Sets values.
	 *
	 * @param values values to set
	 */
	public void setValues(List<@Label("param") Integer> values) {
		this.values = values;
	}
}
