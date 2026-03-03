package com.documentation.generator.java.fixtures.models;

import com.documentation.generator.java.fixtures.annotations.DocTag;
import com.documentation.generator.java.fixtures.annotations.Range;
import java.util.ArrayList;
import java.util.List;

/**
 * Utility service with overloaded and generic methods.
 */
@DocTag(value = "calculator", category = "service")
public final class CalculatorService {

	/**
	 * Adds two integers.
	 *
	 * @param left left value
	 * @param right right value
	 * @return sum
	 */
	public int add(int left, int right) {
		return left + right;
	}

	/**
	 * Adds many integers.
	 *
	 * @param values values to add
	 * @return total sum
	 */
	public int addAll(@Range(min = 0, max = 100) int... values) {
		int sum = 0;
		for (int value : values) {
			sum += value;
		}
		return sum;
	}

	/**
	 * Creates a mutable list from the provided items.
	 *
	 * @param <T> item type
	 * @param values source values
	 * @return mutable list
	 */
	@SafeVarargs
	public final <T> List<T> toList(T... values) {
		List<T> result = new ArrayList<>();
		for (T value : values) {
			result.add(value);
		}
		return result;
	}

	/**
	 * Divides a number and throws a checked exception on invalid input.
	 *
	 * @param numerator numerator
	 * @param denominator denominator
	 * @return quotient
	 * @throws OperationException when denominator is zero
	 */
	public double divide(double numerator, double denominator)
		throws OperationException {
		if (denominator == 0) {
			throw new OperationException("Denominator cannot be zero.");
		}
		return numerator / denominator;
	}
}
