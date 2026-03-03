package com.documentation.generator.java.fixtures.contracts;

/**
 * Contract for binary numeric operations.
 */
public interface BinaryOperation {
	/**
	 * Applies the operation.
	 *
	 * @param left left operand
	 * @param right right operand
	 * @return operation result
	 */
	double apply(double left, double right);
}
