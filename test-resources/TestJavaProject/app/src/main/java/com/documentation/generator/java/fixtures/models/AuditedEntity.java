package com.documentation.generator.java.fixtures.models;

import com.documentation.generator.java.fixtures.enums.LifecycleState;

/**
 * Concrete base implementation with lifecycle support.
 */
public class AuditedEntity extends BaseEntity<String> {

	private LifecycleState state;

	/**
	 * Creates an audited entity.
	 *
	 * @param id identifier
	 */
	public AuditedEntity(String id) {
		super(id);
		this.state = LifecycleState.DRAFT;
	}

	@Override
	public String entityType() {
		return "audited";
	}

	/**
	 * Returns current state.
	 *
	 * @return lifecycle state
	 */
	public LifecycleState getState() {
		return state;
	}

	/**
	 * Sets current state.
	 *
	 * @param state next state
	 */
	public void setState(LifecycleState state) {
		this.state = state;
	}
}
