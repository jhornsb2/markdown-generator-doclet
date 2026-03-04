package com.jhornsb2.doclet.generator.markdown.elements;

import static org.junit.jupiter.api.Assertions.assertSame;

import java.lang.reflect.Proxy;
import org.junit.jupiter.api.Test;

class IModuleElementTest {

	@Test
	void canBeHandledAsElementDataPolymorphically() {
		IModuleElement moduleElement = (IModuleElement) Proxy.newProxyInstance(
			IModuleElement.class.getClassLoader(),
			new Class<?>[] { IModuleElement.class },
			(proxy, method, args) -> null
		);

		assertSame(moduleElement, asElementData(moduleElement));
	}

	private static IElementData asElementData(final IElementData elementData) {
		return elementData;
	}
}
