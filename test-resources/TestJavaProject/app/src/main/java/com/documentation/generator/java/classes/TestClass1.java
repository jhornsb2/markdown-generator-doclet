package com.documentation.generator.java.classes;

/**
 * A simple class to test the documentation generator.
 */
public class TestClass1 {

	/**
	 * A private field of type String.
	 */
	private String foo;

	/**
	 * A private field of type int.
	 */
	private int bar;

	/**
	 * Default constructor.
	 */
	public TestClass1() {
		this("", 0);
	}

	/**
	 * Constructor with all parameters.
	 * 
	 * @param foo The foo parameter.
	 * @param bar The bar parameter.
	 */
	public TestClass1(String foo, int bar) {
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
	 * Setter for the foo field.
	 * 
	 * @param foo The new value for the foo field.
	 */
	public void setFoo(String foo) {
		this.foo = foo;
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
	 * Setter for the bar field.
	 * 
	 * @param bar The new value for the bar field.
	 */
	public void setBar(int bar) {
		this.bar = bar;
	}

}
