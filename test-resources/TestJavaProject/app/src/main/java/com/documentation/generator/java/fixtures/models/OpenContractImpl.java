package com.documentation.generator.java.fixtures.models;

import com.documentation.generator.java.fixtures.contracts.OpenContract;

/**
 * Concrete implementation of {@link OpenContract}.
 */
public class OpenContractImpl implements OpenContract {

	private final String name;

	/**
	 * Creates a contract implementation.
	 *
	 * @param name contract name
	 */
	public OpenContractImpl(String name) {
		this.name = name;
	}

	@Override
	public String contractName() {
		return name;
	}
}
