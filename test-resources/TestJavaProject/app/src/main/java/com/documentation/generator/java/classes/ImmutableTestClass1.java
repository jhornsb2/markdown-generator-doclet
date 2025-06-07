package com.documentation.generator.java.classes;

/**
 * An immutable class for testing the documentation generator.
 */
public final class ImmutableTestClass1 {

	/**
	 * A private field of type String.
	 */
	private final String foo;

	/**
	 * A private field of type int.
	 */
	private final int bar;

	/**
	 * Default constructor.
	 */
	public ImmutableTestClass1() {
		this("", 0);
	}

	/**
	 * Constructor with all parameters.
	 * 
	 * @param foo The foo parameter.
	 * @param bar The bar parameter.
	 */
	public ImmutableTestClass1(String foo, int bar) {
		this.foo = foo;
		this.bar = bar;
	}

	/**
	 * Getter for the foo field.
	 * 
	 * @return The foo field.
	 */
	public String getFoo() {
		return foo;
	}

	/**
	 * Getter for the bar field.
	 * 
	 * @return The bar field.
	 */
	public int getBar() {
		return bar;
	}

	/**
	 * Returns a new instance of ImmutableTestClass1 with the foo field set to the
	 * specified value.
	 * 
	 * @param foo The new value for the foo field.
	 * @return A new instance of ImmutableTestClass1 with the foo field set to the
	 *         specified value.
	 */
	public ImmutableTestClass1 withFoo(String foo) {
		return new ImmutableTestClass1(foo, bar);
	}

	/**
	 * Returns a new instance of ImmutableTestClass1 with the bar field set to the
	 * specified value.
	 * 
	 * @param bar The new value for the bar field.
	 * @return A new instance of ImmutableTestClass1 with the bar field set to the
	 *         specified value.
	 */
	public ImmutableTestClass1 withBar(int bar) {
		return new ImmutableTestClass1(foo, bar);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null || getClass() != obj.getClass()) {
			return false;
		}
		ImmutableTestClass1 other = (ImmutableTestClass1) obj;
		return foo.equals(other.foo) && bar == other.bar;
	}

	@Override
	public int hashCode() {
		int result = 17;
		result = 31 * result + foo.hashCode();
		result = 31 * result + bar;
		return result;
	}

}
