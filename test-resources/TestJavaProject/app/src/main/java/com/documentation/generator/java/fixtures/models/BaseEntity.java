package com.documentation.generator.java.fixtures.models;

import com.documentation.generator.java.fixtures.annotations.DocTag;
import com.documentation.generator.java.fixtures.contracts.Identifiable;
import java.time.Instant;

/**
 * Base class for identifiable entities.
 *
 * @param <K> identifier type
 */
@DocTag(value = "base-entity", category = "model")
public abstract class BaseEntity<K> implements Identifiable<K> {

	private final K id;
	private final Instant createdAt;

	/**
	 * Constructs a base entity.
	 *
	 * @param id identifier
	 */
	protected BaseEntity(K id) {
		this.id = id;
		this.createdAt = Instant.now();
	}

	@Override
	public K id() {
		return id;
	}

	/**
	 * Returns the creation timestamp.
	 *
	 * @return timestamp
	 */
	public Instant getCreatedAt() {
		return createdAt;
	}

	/**
	 * Returns a type label.
	 *
	 * @return entity label
	 */
	public abstract String entityType();
}
