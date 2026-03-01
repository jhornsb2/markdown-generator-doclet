package com.jhornsb2.doclet.generator.markdown.filepath;

import com.jhornsb2.doclet.generator.markdown.constants.StandardFileNames;
import javax.lang.model.element.ModuleElement;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.QualifiedNameable;
import javax.lang.model.element.TypeElement;

/**
 * Hierarchical strategy that generates output file paths with directories
 * based on qualified names.
 */
public class HierarchicalOutputFilepathStrategy
	implements OutputFilepathStrategy
{

	@Override
	public String forAnnotationElement(final TypeElement annotationElement) {
		return this.containerElementFilepath(annotationElement);
	}

	@Override
	public String forInterfaceElement(final TypeElement interfaceElement) {
		return this.containerElementFilepath(interfaceElement);
	}

	@Override
	public String forClassElement(final TypeElement classElement) {
		return this.containerElementFilepath(classElement);
	}

	@Override
	public String forEnumElement(final TypeElement enumElement) {
		return this.containerElementFilepath(enumElement);
	}

	@Override
	public String forRecordElement(final TypeElement recordElement) {
		return this.containerElementFilepath(recordElement);
	}

	@Override
	public String forModuleElement(final ModuleElement moduleElement) {
		return this.containerElementFilepath(moduleElement);
	}

	@Override
	public String forPackageElement(final PackageElement packageElement) {
		return this.containerElementFilepath(packageElement);
	}

	private String containerElementFilepath(final QualifiedNameable element) {
		final String qualifiedNamePath = element
			.getQualifiedName()
			.toString()
			.replace('.', '/');

		return String.format(
			"%s/%s",
			qualifiedNamePath,
			StandardFileNames.INDEX_FILE_NAME
		);
	}
}
