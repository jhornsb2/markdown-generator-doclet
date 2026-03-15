package com.jhornsb2.doclet.generator.markdown.elements.factory;

import com.jhornsb2.doclet.generator.markdown.elements.IElementData;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import lombok.NonNull;
import lombok.Value;

/**
 * Factory class for creating instances of {@link IElementData} based on the
 * provided elements.
 */
@Value
public class IElementDataFactory {

	ElementDataCache elementDataCache;

	ModuleDataFactory moduleDataFactory;
	PackageDataFactory packageDataFactory;
	InterfaceDataFactory interfaceDataFactory;
	AnnotationDataFactory annotationDataFactory;
	ClassDataFactory classDataFactory;
	RecordDataFactory recordDataFactory;
	EnumDataFactory enumDataFactory;
	FieldDataFactory fieldDataFactory;
	MethodDataFactory methodDataFactory;

	/**
	 * Creates an instance of {@link IElementData} based on the provided
	 * element.
	 * <p>
	 * The method determines the kind of the element and delegates to the
	 * appropriate creation method for that specific kind (e.g., class,
	 * interface, enum, etc.).
	 *
	 * @param element The element for which to create the element data. This
	 *                can be any kind of element (e.g., class, interface, enum,
	 *                package, module). This parameter must not be null.
	 * @return An instance of {@link IElementData} representing the provided
	 *         element.
	 * @throws IllegalArgumentException If the element kind is not supported, an
	 *                                  IllegalArgumentException is thrown with a
	 *                                  message indicating the unsupported kind.
	 */
	public IElementData create(@NonNull final Element element) {
		final String key = this.elementDataCache.keyFor(element);
		return this.elementDataCache.getOrCompute(key, () ->
			this.createUncached(element)
		);
	}

	/**
	 * Creates an instance of {@link IElementData} based on the provided element
	 * without checking the cache. This method is intended to be called
	 * internally by the caching mechanism when a cache miss occurs.
	 *
	 * @param element The element for which to create the element data. This can
	 *                be any kind of element (e.g., class, interface, enum,
	 *                package, module). This parameter must not be null.
	 * @return An instance of {@link IElementData} representing the provided
	 *         element.
	 * @throws IllegalArgumentException If the element kind is not supported, an
	 *                                  IllegalArgumentException is thrown with a
	 *                                  message indicating the unsupported kind.
	 */
	IElementData createUncached(@NonNull final Element element) {
		final ElementKind kind = element.getKind();
		return switch (kind) {
			case CLASS -> this.classDataFactory.createUncached(element);
			case RECORD -> this.recordDataFactory.createUncached(element);
			case ENUM -> this.enumDataFactory.createUncached(element);
			case FIELD -> this.fieldDataFactory.createUncached(element);
			case METHOD -> this.methodDataFactory.createUncached(element);
			case INTERFACE -> this.interfaceDataFactory.createUncached(element);
			case ANNOTATION_TYPE -> this.annotationDataFactory.createUncached(
				element
			);
			case PACKAGE -> this.packageDataFactory.createUncached(element);
			case MODULE -> this.moduleDataFactory.createUncached(element);
			default -> throw new IllegalArgumentException(
				"Unsupported element kind: " + kind
			);
		};
	}
}
