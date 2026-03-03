package com.documentation.generator.java.fixtures.enums;

/**
 * Lifecycle state enum with per-constant behavior.
 */
public enum LifecycleState {
	/** Draft content not yet published. */
	DRAFT(false),
	/** Publicly visible and active. */
	ACTIVE(true) {
		@Override
		public String displayName() {
			return "Active";
		}
	},
	/** Archived content for historical reference. */
	ARCHIVED(false);

	private final boolean visible;

	LifecycleState(boolean visible) {
		this.visible = visible;
	}

	/**
	 * Indicates if the state is visible to end users.
	 *
	 * @return true when visible
	 */
	public boolean isVisible() {
		return visible;
	}

	/**
	 * Human-readable label.
	 *
	 * @return display text
	 */
	public String displayName() {
		return name();
	}
}
