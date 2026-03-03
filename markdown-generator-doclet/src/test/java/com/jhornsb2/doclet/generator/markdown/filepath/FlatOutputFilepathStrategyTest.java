package com.jhornsb2.doclet.generator.markdown.filepath;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.lang.reflect.Proxy;
import javax.lang.model.element.ModuleElement;
import javax.lang.model.element.Name;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import org.junit.jupiter.api.Test;

class FlatOutputFilepathStrategyTest {

	@Test
	void appendsMdToQualifiedTypeNames() {
		FlatOutputFilepathStrategy strategy = new FlatOutputFilepathStrategy();

		assertEquals(
			"com.example.Service.md",
			strategy.forClassElement(typeElement("com.example.Service"))
		);
		assertEquals(
			"com.example.Api.md",
			strategy.forInterfaceElement(typeElement("com.example.Api"))
		);
	}

	@Test
	void appendsMdToModuleAndPackageNames() {
		FlatOutputFilepathStrategy strategy = new FlatOutputFilepathStrategy();

		assertEquals(
			"com.example.module.md",
			strategy.forModuleElement(moduleElement("com.example.module"))
		);
		assertEquals(
			"com.example.pkg.md",
			strategy.forPackageElement(packageElement("com.example.pkg"))
		);
	}

	private static TypeElement typeElement(final String qualifiedName) {
		return proxyQualifiedName(TypeElement.class, qualifiedName);
	}

	private static ModuleElement moduleElement(final String qualifiedName) {
		return proxyQualifiedName(ModuleElement.class, qualifiedName);
	}

	private static PackageElement packageElement(final String qualifiedName) {
		return proxyQualifiedName(PackageElement.class, qualifiedName);
	}

	@SuppressWarnings("unchecked")
	private static <T> T proxyQualifiedName(
		final Class<T> type,
		final String qualifiedName
	) {
		return (T) Proxy.newProxyInstance(
			type.getClassLoader(),
			new Class<?>[] { type },
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
