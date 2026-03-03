package com.documentation.generator.java.fixtures.models;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Advanced generic examples including wildcards and intersection bounds.
 */
public class GenericPlayground {

	private final Map<String, List<? super Integer>> groupedValues =
		new HashMap<>();

	/**
	 * Sums values using an upper-bounded wildcard.
	 *
	 * @param values numeric values
	 * @return sum
	 */
	public double sum(List<? extends Number> values) {
		double result = 0;
		for (Number value : values) {
			result += value.doubleValue();
		}
		return result;
	}

	/**
	 * Adds defaults using a lower-bounded wildcard.
	 *
	 * @param values sink values
	 */
	public void addDefaults(List<? super Integer> values) {
		values.add(1);
		values.add(2);
	}

	/**
	 * Returns max value using an intersection type bound.
	 *
	 * @param <T> value type
	 * @param left first value
	 * @param right second value
	 * @return max value
	 */
	public <T extends Number & Comparable<T>> T max(T left, T right) {
		return left.compareTo(right) >= 0 ? left : right;
	}

	/**
	 * Returns max value using multiple generic parameters and a comparator.
	 *
	 * @param <T> value type
	 * @param left first value
	 * @param right second value
	 * @param comparator comparator instance
	 * @return max value
	 */
	public <T> T max(T left, T right, Comparator<? super T> comparator) {
		return comparator.compare(left, right) >= 0 ? left : right;
	}

	/**
	 * Stores grouped values.
	 *
	 * @param key group key
	 * @param values group values
	 */
	public void putGroup(String key, List<? super Integer> values) {
		groupedValues.put(key, values);
	}

	/**
	 * Returns stored grouped values.
	 *
	 * @return grouped values
	 */
	public Map<String, List<? super Integer>> groupedValues() {
		return groupedValues;
	}
}
