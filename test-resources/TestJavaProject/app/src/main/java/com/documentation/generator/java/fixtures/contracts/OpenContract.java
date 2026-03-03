package com.documentation.generator.java.fixtures.contracts;

/**
 * Non-sealed interface featuring all legal interface member categories.
 */
public non-sealed interface OpenContract extends SealedContract {
	/**
	 * Public constant field on an interface.
	 */
	String DEFAULT_PREFIX = "contract";

	/**
	 * Computes a display label.
	 *
	 * @return label
	 */
	default String displayLabel() {
		return join(DEFAULT_PREFIX, sanitize(contractName()));
	}

	/**
	 * Joins two text values.
	 *
	 * @param left left text
	 * @param right right text
	 * @return joined text
	 */
	static String join(String left, String right) {
		return left + ":" + right;
	}

	private static String sanitize(String value) {
		return value == null ? "unknown" : value.trim().toLowerCase();
	}

	private String normalizedName() {
		return sanitize(contractName());
	}

	/**
	 * Checks if the normalized name is blank.
	 *
	 * @return true when blank
	 */
	default boolean isBlankName() {
		return normalizedName().isBlank();
	}

	/**
	 * Nested interface type.
	 */
	interface Auditor {
		/**
		 * Audits the current contract.
		 *
		 * @param contract contract value
		 */
		void audit(OpenContract contract);
	}

	/**
	 * Nested helper class.
	 */
	class Helper {

		/**
		 * Creates a summary string.
		 *
		 * @param contract contract value
		 * @return summary
		 */
		public String summary(OpenContract contract) {
			return contract.displayLabel();
		}
	}

	/**
	 * Nested enum indicating audit level.
	 */
	enum AuditLevel {
		LOW,
		MEDIUM,
		HIGH
	}

	/**
	 * Nested record describing an audit entry.
	 *
	 * @param actor actor id
	 * @param level audit level
	 */
	record AuditEntry(String actor, AuditLevel level) {}
}
