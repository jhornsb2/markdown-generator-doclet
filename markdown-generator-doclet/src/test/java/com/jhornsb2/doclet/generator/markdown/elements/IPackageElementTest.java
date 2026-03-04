package com.jhornsb2.doclet.generator.markdown.elements;

import static org.junit.jupiter.api.Assertions.assertSame;

import java.lang.reflect.Proxy;
import org.junit.jupiter.api.Test;

class IPackageElementTest {

	@Test
	void canBeHandledAsElementDataPolymorphically() {
		IPackageElement packageElement =
			(IPackageElement) Proxy.newProxyInstance(
				IPackageElement.class.getClassLoader(),
				new Class<?>[] { IPackageElement.class },
				(proxy, method, args) -> null
			);

		assertSame(packageElement, asElementData(packageElement));
	}

	private static IElementData asElementData(final IElementData elementData) {
		return elementData;
	}
}
