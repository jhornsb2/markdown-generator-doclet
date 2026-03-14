package com.jhornsb2.doclet.generator.markdown.filepath;

import javax.lang.model.element.Element;
import javax.lang.model.element.ModuleElement;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;

/**
 * Strategy interface for generating output file paths for different element
 * types.
 */
public interface IOutputFilepathStrategy {
	default String forElement(Element element) {
		return switch (element.getKind()) {
			case ANNOTATION_TYPE -> forAnnotationElement((TypeElement) element);
			case INTERFACE -> forInterfaceElement((TypeElement) element);
			case CLASS -> forClassElement((TypeElement) element);
			case ENUM -> forEnumElement((TypeElement) element);
			case RECORD -> forRecordElement((TypeElement) element);
			case MODULE -> forModuleElement((ModuleElement) element);
			case PACKAGE -> forPackageElement((PackageElement) element);
			default -> throw new IllegalArgumentException("Unsupported element type: " + element.getKind());
		};
	}

	String forAnnotationElement(TypeElement annotationElement);

	String forInterfaceElement(TypeElement interfaceElement);

	String forClassElement(TypeElement classElement);

	String forEnumElement(TypeElement enumElement);

	String forRecordElement(TypeElement recordElement);

	String forModuleElement(ModuleElement moduleElement);

	String forPackageElement(PackageElement packageElement);
}
