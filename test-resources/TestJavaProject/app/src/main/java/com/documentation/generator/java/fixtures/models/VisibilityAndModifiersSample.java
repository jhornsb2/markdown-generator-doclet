package com.documentation.generator.java.fixtures.models;

/**
 * Class that exercises rarely used fields, methods, and blocks.
 */
public class VisibilityAndModifiersSample {

	/** Public field fixture. */
	public String publicField;
	/** Protected field fixture. */
	protected int protectedField;
	/** Package-private field fixture. */
	long packageField;
	/** Private transient field fixture. */
	private transient String cachedDisplay;
	/** Private volatile field fixture. */
	private volatile boolean active;

	static {
		System.setProperty("fixtures.visibility.loaded", "true");
	}

	{
		this.active = true;
	}

	/**
	 * Creates a sample instance.
	 *
	 * @param publicField public field value
	 */
	public VisibilityAndModifiersSample(String publicField) {
		this.publicField = publicField;
	}

	/**
	 * Package-private method fixture.
	 *
	 * @return package field value
	 */
	long packageMethod() {
		return packageField;
	}

	/**
	 * Protected method fixture.
	 *
	 * @return protected value
	 */
	protected int protectedMethod() {
		return protectedField;
	}

	/**
	 * Private method fixture.
	 *
	 * @return private value
	 */
	private String privateMethod() {
		return cachedDisplay;
	}

	/**
	 * Synchronized method fixture.
	 *
	 * @return active state
	 */
	public synchronized boolean isActive() {
		return active;
	}

	/**
	 * Strict floating point method fixture.
	 *
	 * @param left first value
	 * @param right second value
	 * @return computed ratio
	 */
	public strictfp double ratio(double left, double right) {
		return right == 0 ? 0 : left / right;
	}

	/**
	 * Native method fixture declaration.
	 *
	 * @return platform value
	 */
	public native long nativeClock();

	/**
	 * Uses private method to avoid dead-code behavior.
	 *
	 * @return formatted text
	 */
	public String describe() {
		return privateMethod() == null ? "n/a" : privateMethod();
	}
}
