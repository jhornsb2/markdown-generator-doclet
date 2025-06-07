package com.documentation.generator.java.classes;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.With;

/**
 * A class for testing the documentation generator with Lombok annotations. The
 * annotation @Value generates an immutable class with a constructor, getters,
 * equals, hashCode, and toString methods. The Builder annotation creates a
 * builder class for the annotated class.
 */
@With
@Value
@Builder(toBuilder = true)
public class LombokValueBuilder {

	/**
	 * A private field of type String.
	 */
	@NonNull
	@Builder.Default
	String foo = "";

	/**
	 * A private field of type int.
	 */
	@Builder.Default
	int bar = 0;

}
