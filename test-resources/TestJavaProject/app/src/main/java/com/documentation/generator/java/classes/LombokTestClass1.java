package com.documentation.generator.java.classes;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * A class for testing the documentation generator with Lombok annotations.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LombokTestClass1 {

	/**
	 * A private field of type String.
	 */
	private String foo = "";

	/**
	 * A private field of type int.
	 */
	private int bar = 0;

}
