package com.jhornsb2.doclet.generator.markdown.filepath;

import javax.lang.model.element.ModuleElement;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;

/**
 * Strategy interface for generating output file paths for different element
 * types.
 */
public interface IOutputFilepathStrategy {
	String forAnnotationElement(TypeElement annotationElement);

	String forInterfaceElement(TypeElement interfaceElement);

	String forClassElement(TypeElement classElement);

	String forEnumElement(TypeElement enumElement);

	String forRecordElement(TypeElement recordElement);

	String forModuleElement(ModuleElement moduleElement);

	String forPackageElement(PackageElement packageElement);
}
