package com.jhornsb2.doclet.generator.markdown.naming;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.lang.reflect.Proxy;
import javax.lang.model.element.Name;
import javax.lang.model.element.PackageElement;
import org.junit.jupiter.api.Test;

class QualifiedNameResolverTest {

	@Test
	void toPathReplacesDotsWithForwardSlashes() {
		assertEquals(
			"com/example/api",
			QualifiedNameResolver.toPath("com.example.api")
		);
	}

	@Test
	void pathOfUsesQualifiedPackageName() {
		PackageElement packageElement = packageElement("com.example.pkg");

		assertEquals(
			"com/example/pkg",
			QualifiedNameResolver.pathOf(packageElement)
		);
	}

	private static PackageElement packageElement(final String qualifiedName) {
		return (PackageElement) Proxy.newProxyInstance(
			PackageElement.class.getClassLoader(),
			new Class<?>[] { PackageElement.class },
			(proxy, method, args) -> {
				if ("getQualifiedName".equals(method.getName())) {
					return new Name() {
						@Override
						public boolean contentEquals(final CharSequence cs) {
							return qualifiedName.contentEquals(cs);
						}

						@Override
						public int length() {
							return qualifiedName.length();
						}

						@Override
						public char charAt(final int index) {
							return qualifiedName.charAt(index);
						}

						@Override
						public CharSequence subSequence(
							final int start,
							final int end
						) {
							return qualifiedName.subSequence(start, end);
						}

						@Override
						public String toString() {
							return qualifiedName;
						}
					};
				}
				if ("toString".equals(method.getName())) {
					return qualifiedName;
				}
				return null;
			}
		);
	}
}
