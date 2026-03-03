package com.jhornsb2.doclet.generator.markdown.filepath;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.lang.reflect.Proxy;
import javax.lang.model.element.Name;
import javax.lang.model.element.TypeElement;

import org.junit.jupiter.api.Test;

class IOutputFilepathStrategyTest {

	@Test
	void supportsPolymorphicUseAcrossStrategyImplementations() {
		TypeElement typeElement = typeElement("com.example.Sample");

		IOutputFilepathStrategy flat = new FlatOutputFilepathStrategy();
		IOutputFilepathStrategy hierarchical =
			new HierarchicalOutputFilepathStrategy();

		assertEquals("com.example.Sample.md", flat.forClassElement(typeElement));
		assertEquals(
			"com/example/Sample/index.md",
			hierarchical.forClassElement(typeElement)
		);
	}

	@SuppressWarnings("unchecked")
	private static TypeElement typeElement(final String qualifiedName) {
		return (TypeElement) Proxy.newProxyInstance(
			TypeElement.class.getClassLoader(),
			new Class<?>[] { TypeElement.class },
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
				return null;
			}
		);
	}
}
