package com.jhornsb2.doclet.generator.markdown.elements.factory;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.jhornsb2.doclet.generator.markdown.elements.JavaModifier;
import java.util.LinkedHashSet;
import java.util.Set;
import javax.lang.model.element.Modifier;
import org.junit.jupiter.api.Test;

class ModifierMapperTest {

	@Test
	void returnsEmptySetForNullAndEmptyInput() {
		assertTrue(ModifierMapper.toJavaModifiers(null).isEmpty());
		assertTrue(ModifierMapper.toJavaModifiers(Set.of()).isEmpty());
	}

	@Test
	void mapsSupportedModifiersAndReturnsUnmodifiableSet() {
		Set<Modifier> modifiers = new LinkedHashSet<>();
		modifiers.add(Modifier.PUBLIC);
		modifiers.add(Modifier.STATIC);

		Set<JavaModifier> mapped = ModifierMapper.toJavaModifiers(modifiers);

		assertEquals(Set.of(JavaModifier.PUBLIC, JavaModifier.STATIC), mapped);
		assertThrows(UnsupportedOperationException.class, () ->
			mapped.add(JavaModifier.FINAL)
		);
	}
}
