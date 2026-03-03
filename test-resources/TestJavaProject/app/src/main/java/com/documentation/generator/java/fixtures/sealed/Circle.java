package com.documentation.generator.java.fixtures.sealed;

/**
 * Circle implementation of {@link Shape}.
 */
public final class Circle implements Shape {

	private final double radius;

	/**
	 * Creates a circle.
	 *
	 * @param radius radius value
	 */
	public Circle(double radius) {
		this.radius = radius;
	}

	/**
	 * Returns the radius.
	 *
	 * @return radius
	 */
	public double getRadius() {
		return radius;
	}

	@Override
	public double area() {
		return Math.PI * radius * radius;
	}
}
