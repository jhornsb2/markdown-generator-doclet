package com.jhornsb2.doclet.generator.markdown.processor.impl;

import java.util.List;

import javax.lang.model.element.TypeElement;

import com.jhornsb2.doclet.generator.markdown.logging.DocletLogger;
import com.jhornsb2.doclet.generator.markdown.processor.IDocletElementProcessor;
import com.jhornsb2.doclet.generator.markdown.util.DocCommentUtil;
import com.sun.source.doctree.DocCommentTree;

import lombok.Value;

@Value
public class ClassElementProcessor implements IDocletElementProcessor {

	private static final DocletLogger log = DocletLogger.forClass(ClassElementProcessor.class);

	private static final String TEMPLATE = """
		# ${simpleName}

		${kind} in ${packageName}

		Inherits from ${superclassName}

		Implements ${interfaces}

		## Description

		${docComment}

		## Properties

		${properties}

		## Public Methods

		${publicMethods}

		## Static Methods

		${staticMethods}

		## Inherited Members

		${inheritedMembers}

		""";

	TypeElement classElement;

	public String getOutputFilepath() {
		log.debug("Generating output file path for class: {}", this.classElement.getQualifiedName());
		return this.classElement.getQualifiedName().toString().replace('.', '/') + "/index.md";
	}

	public String toMarkdownString() {
		log.debug("Generating markdown for class: {}", this.classElement.getQualifiedName());

		return ClassElementProcessor.TEMPLATE.replace("${simpleName}", this.classElement.getSimpleName().toString())
			.replace("${kind}", this.classElement.getKind().toString())
			.replace("${packageName}", this.classElement.getEnclosingElement().toString())
			.replace("${superclassName}", this.classElement.getSuperclass().toString())
			.replace("${interfaces}", this.classElement.getInterfaces().toString())
			.replace(
				"${docComment}",
				DocCommentUtil.getDocCommentTree(this.classElement)
					.map(DocCommentTree::getFullBody)
					.map(List::toString)
					.orElse("")
			)
			.replace("${properties}", "TODO: Implement properties")
			.replace("${publicMethods}", "TODO: Implement public methods")
			.replace("${staticMethods}", "TODO: Implement static methods")
			.replace("${inheritedMembers}", "TODO: Implement inherited members");
	}

}
