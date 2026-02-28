package com.jhornsb2.doclet.generator.markdown.factory;

import javax.lang.model.element.ModuleElement;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;

/**
 * Factory interface for generating output file paths for different types of elements.
 */
public interface IOutputFilepathFactory {
	/**
	 * Generates the output file path for an annotation element.
	 *
	 * @param annotationElement the annotation element to generate the output file path for
	 * @return the output file path for the annotation element
	 */
	String forAnnotationElement(TypeElement annotationElement);

	/**
	 * Generates the output file path for an interface element.
	 *
	 * @param interfaceElement the interface element to generate the output file path for
	 * @return the output file path for the interface element
	 */
	String forInterfaceElement(TypeElement interfaceElement);

	/**
	 * Generates the output file path for a class element.
	 *
	 * @param classElement the class element to generate the output file path for
	 * @return the output file path for the class element
	 */
	String forClassElement(TypeElement classElement);

	/**
	 * Generates the output file path for an enum element.
	 *
	 * @param enumElement the enum element to generate the output file path for
	 * @return the output file path for the enum element
	 */
	String forEnumElement(TypeElement enumElement);

	/**
	 * Generates the output file path for a record element.
	 *
	 * @param recordElement the record element to generate the output file path for
	 * @return the output file path for the record element
	 */
	String forRecordElement(TypeElement recordElement);

	/**
	 * Generates the output file path for a module element.
	 *
	 * @param moduleElement the module element to generate the output file path for
	 * @return the output file path for the module element
	 */
	String forModuleElement(ModuleElement moduleElement);

	/**
	 * Generates the output file path for a package element.
	 *
	 * @param packageElement the package element to generate the output file path for
	 * @return the output file path for the package element
	 */
	String forPackageElement(PackageElement packageElement);
}
