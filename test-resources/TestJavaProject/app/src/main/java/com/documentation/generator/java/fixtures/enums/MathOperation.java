package com.documentation.generator.java.fixtures.enums;

import com.documentation.generator.java.fixtures.contracts.BinaryOperation;

/**
 * Enum implementing an interface with abstract members implemented per constant.
 */
public enum MathOperation implements BinaryOperation {
	/** Addition operation. */
	ADD {
		@Override
		public double apply(double left, double right) {
			return left + right;
		}

		@Override
		public String symbol() {
			return "+";
		}
	},
	/** Subtraction operation. */
	SUBTRACT {
		@Override
		public double apply(double left, double right) {
			return left - right;
		}

		@Override
		public String symbol() {
			return "-";
		}
	};

	/**
	 * Symbol for the operation.
	 *
	 * @return operation symbol
	 */
	public abstract String symbol();
}
