package com.documentation.generator.java.fixtures.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * General-purpose annotation for fixture elements.
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.TYPE, ElementType.METHOD, ElementType.CONSTRUCTOR })
public @interface DocTag {
	/**
	 * A stable identifier for the annotated element.
	 *
	 * @return identifier
	 */
	String value();

	/**
	 * Optional category for grouping related elements.
	 *
	 * @return category label
	 */
	String category() default "general";
}
