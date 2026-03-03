package com.documentation.generator.java.fixtures.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Repeatable label annotation used for type and member tagging.
 */
@Documented
@Inherited
@Repeatable(Labels.class)
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
public @interface Label {
	/**
	 * Label text.
	 *
	 * @return label text
	 */
	String value();
}
