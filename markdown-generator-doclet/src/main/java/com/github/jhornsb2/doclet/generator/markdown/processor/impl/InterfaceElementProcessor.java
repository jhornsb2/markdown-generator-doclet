package com.github.jhornsb2.doclet.generator.markdown.processor.impl;

import java.util.List;

import javax.lang.model.element.TypeElement;

import com.github.jhornsb2.doclet.generator.markdown.logging.DocletLogger;
import com.github.jhornsb2.doclet.generator.markdown.processor.IDocletElementProcessor;
import com.github.jhornsb2.doclet.generator.markdown.util.DocCommentUtil;
import com.sun.source.doctree.DocCommentTree;

import lombok.Value;

@Value
public class InterfaceElementProcessor implements IDocletElementProcessor {

	private static final DocletLogger log = DocletLogger.forClass(InterfaceElementProcessor.class);

	private static final String TEMPLATE = """
		# ${simpleName}

		${kind} in ${packageName}

		Extends ${interfaces}

		## Description

		${docComment}

		## Public Methods

		${publicMethods}

		## Static Methods

		${staticMethods}

		## Inherited Members

		${inheritedMembers}

		""";

	TypeElement interfaceElement;

	public String getOutputFilepath() {
		log.debug("Generating output file path for interface: {}", this.interfaceElement.getQualifiedName());
		return this.interfaceElement.getQualifiedName().toString().replace('.', '/') + "/index.md";
	}

	public String toMarkdownString() {
		log.debug("Generating markdown for interface: {}", this.interfaceElement.getQualifiedName());

		return InterfaceElementProcessor.TEMPLATE
			.replace("${simpleName}", this.interfaceElement.getSimpleName().toString())
			.replace("${kind}", this.interfaceElement.getKind().toString())
			.replace("${packageName}", this.interfaceElement.getEnclosingElement().toString())
			.replace("${interfaces}", this.interfaceElement.getInterfaces().toString())
			.replace(
				"${docComment}",
				DocCommentUtil.getDocCommentTree(this.interfaceElement)
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
