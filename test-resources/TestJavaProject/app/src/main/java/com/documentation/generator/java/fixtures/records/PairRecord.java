package com.documentation.generator.java.fixtures.records;

import java.util.Objects;

/**
 * Generic record with an explicit canonical constructor.
 *
 * @param <L> left type
 * @param <R> right type
 * @param left left value
 * @param right right value
 */
public record PairRecord<L, R>(L left, R right) {
	/**
	 * Explicit canonical constructor.
	 *
	 * @param left left value
	 * @param right right value
	 */
	public PairRecord(L left, R right) {
		this.left = Objects.requireNonNull(left, "left must not be null");
		this.right = right;
	}

	/**
	 * Factory method for pair creation.
	 *
	 * @param <L> left type
	 * @param <R> right type
	 * @param left left value
	 * @param right right value
	 * @return pair record
	 */
	public static <L, R> PairRecord<L, R> of(L left, R right) {
		return new PairRecord<>(left, right);
	}
}
