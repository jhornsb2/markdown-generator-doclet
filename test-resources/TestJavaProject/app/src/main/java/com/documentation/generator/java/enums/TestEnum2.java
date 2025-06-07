package com.documentation.generator.java.enums;

import java.util.Collections;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * This is a complex enum that is meant for testing a Java enum's features.
 */
public enum TestEnum2 {

	/**
	 * This is the first value.
	 */
	VALUE1("Value 1"),
	/**
	 * This is the second value.
	 */
	VALUE2("Value 2"),
	/**
	 * This is the third value.
	 */
	VALUE3("Value 3");

	/**
	 * The {@link String} value of the enum.
	 */
	private String value;

	/**
	 * Constructor for the enum.
	 * 
	 * @param value the value of the enum
	 */
	TestEnum2(String value) {
		this.value = value;
	}

	/**
	 * Returns the value of the enum.
	 * 
	 * @return the value of the enum
	 */
	public String getValue() {
		return value;
	}

	/**
	 * Returns whether the enum is {@link #VALUE1}.
	 * 
	 * @return <code>true</code> if the enum is {@link #VALUE1}, <code>false</code>
	 *         otherwise
	 */
	public boolean isValue1() {
		return this == VALUE1;
	}

	/**
	 * Returns whether the enum is {@link #VALUE2}.
	 * 
	 * @return <code>true</code> if the enum is {@link #VALUE2}, <code>false</code>
	 *         otherwise
	 */
	public boolean isValue2() {
		return this == VALUE2;
	}

	/**
	 * Returns whether the enum is {@link #VALUE3}.
	 * 
	 * @return <code>true</code> if the enum is {@link #VALUE3}, <code>false</code>
	 *         otherwise
	 */
	public boolean isValue3() {
		return this == VALUE3;
	}

	/**
	 * A simple map that maps the {@link #value} to the enum value.
	 */
	private static final Map<String, TestEnum2> VALUE_TO_ENUM_MAP = Collections.unmodifiableMap(
			Stream.of(values()).collect(Collectors.toMap(TestEnum2::getValue, Function.identity())));

	/**
	 * Returns the enum value from the given {@link #value}.
	 * 
	 * @param value the {@link #value} to get the enum from
	 * @return the enum value from the given {@link #value}
	 */
	public static TestEnum2 fromValue(String value) {
		return VALUE_TO_ENUM_MAP.get(value);
	}

	/**
	 * Converts the given {@link TestEnum2} to {@link TestEnum1}.
	 * 
	 * @param testEnum2 the {@link TestEnum2} to convert
	 * @return the converted {@link TestEnum1}
	 */
	public static TestEnum1 toTestEnum1(TestEnum2 testEnum2) {
		switch (testEnum2) {
			case VALUE1:
				return TestEnum1.VALUE1;
			case VALUE2:
				return TestEnum1.VALUE2;
			case VALUE3:
				return TestEnum1.VALUE3;
			default:
				throw new IllegalArgumentException("Invalid enum value: " + testEnum2);
		}
	}

	/**
	 * Converts the given {@link TestEnum1} to {@link TestEnum2}.
	 * 
	 * @param testEnum1 the {@link TestEnum1} to convert
	 * @return the converted {@link TestEnum2}
	 */
	public static TestEnum2 fromTestEnum1(TestEnum1 testEnum1) {
		switch (testEnum1) {
			case VALUE1:
				return TestEnum2.VALUE1;
			case VALUE2:
				return TestEnum2.VALUE2;
			case VALUE3:
				return TestEnum2.VALUE3;
			default:
				throw new IllegalArgumentException("Invalid enum value: " + testEnum1);
		}
	}

}
