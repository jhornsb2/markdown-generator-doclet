package com.documentation.generator.java.classes;

/**
 * An abstract class for testing the documentation generator.
 */
public abstract class AbstractTestClass1 {

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
	protected AbstractTestClass1() {
		this("", 0);
	}

	/**
	 * Constructor with all parameters.
	 * 
	 * @param foo The foo parameter.
	 * @param bar The bar parameter.
	 */
	protected AbstractTestClass1(String foo, int bar) {
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

	/**
	 * An abstract method with no parameters and no return value.
	 */
	public abstract void abstractMethod1();

	/**
	 * An abstract method with no parameters and a return value.
	 * 
	 * @return The return value.
	 */
	public abstract String abstractMethod2();

	/**
	 * An abstract method with parameters and no return value.
	 * 
	 * @param param1 The first parameter.
	 * @param param2 The second parameter.
	 */
	public abstract void abstractMethod3(String param1, int param2);

}
