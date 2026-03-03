package com.jhornsb2.doclet.generator.markdown.elements.factory;

import com.jhornsb2.doclet.generator.markdown.elements.JavaModifier;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;
import javax.lang.model.element.Modifier;

final class ModifierMapper {

	private ModifierMapper() {}

	static Set<JavaModifier> toJavaModifiers(final Set<Modifier> modifiers) {
		if (modifiers == null || modifiers.isEmpty()) {
			return Collections.emptySet();
		}
		final Set<JavaModifier> resolvedModifiers = new LinkedHashSet<>();
		for (Modifier modifier : modifiers) {
			JavaModifier javaModifier = map(modifier);
			if (javaModifier != null) {
				resolvedModifiers.add(javaModifier);
			}
		}
		if (resolvedModifiers.isEmpty()) {
			return Collections.emptySet();
		}
		return Collections.unmodifiableSet(resolvedModifiers);
	}

	private static JavaModifier map(final Modifier modifier) {
		return switch (modifier) {
			case PUBLIC -> JavaModifier.PUBLIC;
			case PROTECTED -> JavaModifier.PROTECTED;
			case PRIVATE -> JavaModifier.PRIVATE;
			case ABSTRACT -> JavaModifier.ABSTRACT;
			case DEFAULT -> JavaModifier.DEFAULT;
			case STATIC -> JavaModifier.STATIC;
			case SEALED -> JavaModifier.SEALED;
			case NON_SEALED -> JavaModifier.NON_SEALED;
			case FINAL -> JavaModifier.FINAL;
			case TRANSIENT -> JavaModifier.TRANSIENT;
			case VOLATILE -> JavaModifier.VOLATILE;
			case SYNCHRONIZED -> JavaModifier.SYNCHRONIZED;
			case NATIVE -> JavaModifier.NATIVE;
			case STRICTFP -> JavaModifier.STRICTFP;
		};
	}
}
