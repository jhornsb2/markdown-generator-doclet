package com.documentation.generator.java.fixtures.contracts;

/**
 * Sealed contract root to test non-sealed interface descendants.
 */
public sealed interface SealedContract permits OpenContract {
	/**
	 * Returns the contract name.
	 *
	 * @return contract name
	 */
	String contractName();
}
