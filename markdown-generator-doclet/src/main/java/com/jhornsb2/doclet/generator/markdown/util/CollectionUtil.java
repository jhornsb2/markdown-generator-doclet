package com.jhornsb2.doclet.generator.markdown.util;

import java.util.Collection;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * Utility class for collection-related operations.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CollectionUtil {

	/**
	 * Checks if a collection is null or empty.
	 *
	 * @param collection The collection to check.
	 * @return {@code true} if the collection is null or empty, {@code false}
	 *         otherwise.
	 */
	public static boolean isEmpty(final Collection<?> collection) {
		return null == collection || collection.isEmpty();
	}
}
