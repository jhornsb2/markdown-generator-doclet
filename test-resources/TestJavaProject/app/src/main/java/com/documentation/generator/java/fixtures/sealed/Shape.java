package com.documentation.generator.java.fixtures.sealed;

/**
 * Sealed shape contract.
 */
public sealed interface Shape permits Circle, Rectangle {
	/**
	 * Computes the shape area.
	 *
	 * @return area value
	 */
	double area();
}
