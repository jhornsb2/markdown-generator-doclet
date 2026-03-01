package com.jhornsb2.doclet.generator.markdown.elements.factory;

import com.jhornsb2.doclet.generator.markdown.elements.IElementData;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.function.Supplier;
import javax.lang.model.element.Element;
import javax.lang.model.element.QualifiedNameable;

/**
 * Thread-safe cache for element data instances shared across factories.
 */
public final class ElementDataCache {

	private final ConcurrentMap<String, IElementData> cache =
		new ConcurrentHashMap<>();

	public IElementData getOrCompute(
		final String key,
		final Supplier<IElementData> supplier
	) {
		return this.cache.computeIfAbsent(key, ignored -> supplier.get());
	}

	public String keyFor(final Element element) {
		final String kindPrefix = element.getKind().name() + ":";
		if (element instanceof QualifiedNameable qualifiedNameable) {
			return kindPrefix + qualifiedNameable.getQualifiedName().toString();
		}
		return kindPrefix + element.toString();
	}
}
