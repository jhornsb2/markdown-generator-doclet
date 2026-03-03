package com.documentation.generator.java.fixtures.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Container annotation for repeatable {@link Label} usage.
 */
@Documented
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target(
	{
		ElementType.TYPE,
		ElementType.TYPE_USE,
		ElementType.FIELD,
		ElementType.METHOD,
		ElementType.PARAMETER,
		ElementType.RECORD_COMPONENT
	}
)
public @interface Labels {
	/**
	 * Repeated labels.
	 *
	 * @return label values
	 */
	Label[] value();
}
