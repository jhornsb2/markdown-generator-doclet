package com.documentation.generator.java.fixtures.sealed;

/**
 * Rectangle record implementation of {@link Shape}.
 *
 * @param width width value
 * @param height height value
 */
public record Rectangle(double width, double height) implements Shape {
	@Override
	public double area() {
		return width * height;
	}
}
