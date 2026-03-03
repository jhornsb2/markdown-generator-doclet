package com.documentation.generator.java.fixtures.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Marks a numeric element with a lower and upper bound.
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(
	{ ElementType.FIELD, ElementType.PARAMETER, ElementType.RECORD_COMPONENT }
)
public @interface Range {
	/**
	 * Lower inclusive bound.
	 *
	 * @return lower bound
	 */
	long min();

	/**
	 * Upper inclusive bound.
	 *
	 * @return upper bound
	 */
	long max();
}
