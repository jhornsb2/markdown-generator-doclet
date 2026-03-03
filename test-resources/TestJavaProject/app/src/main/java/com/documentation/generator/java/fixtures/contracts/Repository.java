package com.documentation.generator.java.fixtures.contracts;

import java.util.List;
import java.util.Optional;

/**
 * Generic repository API fixture to test interface methods and type parameters.
 *
 * @param <K> key type
 * @param <V> value type
 */
public interface Repository<K, V extends Identifiable<K>> {
	/**
	 * Finds an element by key.
	 *
	 * @param key lookup key
	 * @return optional value
	 */
	Optional<V> findById(K key);

	/**
	 * Returns all elements.
	 *
	 * @return list of values
	 */
	List<V> findAll();

	/**
	 * Returns whether the repository is read only.
	 *
	 * @return true when immutable
	 */
	default boolean isReadOnly() {
		return false;
	}

	/**
	 * Returns an empty repository marker.
	 *
	 * @return marker text
	 */
	static String marker() {
		return "repository";
	}
}
