package com.documentation.generator.java.classes;

/**
 * A class that extends AbstractTestClass1.
 */
public class ExtendingTestClass1 extends AbstractTestClass1 {

	@Override
	public void abstractMethod1() {
		System.out.println("ExtendingTestClass1.abstractMethod1()");
	}

	@Override
	public String abstractMethod2() {
		return "ExtendingTestClass1.abstractMethod2()";
	}

	@Override
	public void abstractMethod3(String param1, int param2) {
		System.out.println("ExtendingTestClass1.abstractMethod3(" + param1 + ", " + param2 + ")");
	}

}
