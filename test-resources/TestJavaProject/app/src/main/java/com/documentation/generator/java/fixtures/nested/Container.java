package com.documentation.generator.java.fixtures.nested;

/**
 * Container class exposing inner, nested, enum, and record members.
 */
public class Container {

	private final String name;

	/**
	 * Constructs a new container.
	 *
	 * @param name container name
	 */
	public Container(String name) {
		this.name = name;
	}

	/**
	 * Returns the container name.
	 *
	 * @return name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Static nested helper.
	 */
	public static class StaticNested {

		/**
		 * Returns a normalized value.
		 *
		 * @param value source value
		 * @return normalized value
		 */
		public String normalize(String value) {
			return value == null ? "" : value.trim().toLowerCase();
		}
	}

	/**
	 * Non-static inner type.
	 */
	public class Inner {

		/**
		 * Builds a label using outer and inner context.
		 *
		 * @param suffix suffix text
		 * @return composed label
		 */
		public String label(String suffix) {
			return name + ":" + suffix;
		}
	}

	/**
	 * Nested enum for mode flags.
	 */
	public enum Mode {
		BASIC,
		ADVANCED
	}

	/**
	 * Nested record fixture.
	 *
	 * @param key entry key
	 * @param value entry value
	 */
	public record Entry(String key, String value) {}
}
