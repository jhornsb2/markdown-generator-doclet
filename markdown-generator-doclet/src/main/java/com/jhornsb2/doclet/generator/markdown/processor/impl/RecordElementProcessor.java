package com.jhornsb2.doclet.generator.markdown.processor.impl;

import java.util.List;

import javax.lang.model.element.TypeElement;

import com.jhornsb2.doclet.generator.markdown.logging.DocletLogger;
import com.jhornsb2.doclet.generator.markdown.processor.IDocletElementProcessor;
import com.jhornsb2.doclet.generator.markdown.util.DocCommentUtil;
import com.sun.source.doctree.DocCommentTree;

import lombok.Value;

@Value
public class RecordElementProcessor implements IDocletElementProcessor {

	private static final DocletLogger log = DocletLogger.forClass(RecordElementProcessor.class);

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

	TypeElement recordElement;

	public String getOutputFilepath() {
		log.debug("Generating output file path for record: {}", this.recordElement.getQualifiedName());
		return this.recordElement.getQualifiedName().toString().replace('.', '/') + "/index.md";
	}

	public String toMarkdownString() {
		log.debug("Generating markdown for record: {}", this.recordElement.getQualifiedName());

		return RecordElementProcessor.TEMPLATE.replace("${simpleName}", this.recordElement.getSimpleName().toString())
			.replace("${kind}", this.recordElement.getKind().toString())
			.replace("${packageName}", this.recordElement.getEnclosingElement().toString())
			.replace("${superclassName}", this.recordElement.getSuperclass().toString())
			.replace("${interfaces}", this.recordElement.getInterfaces().toString())
			.replace(
				"${docComment}",
				DocCommentUtil.getDocCommentTree(this.recordElement)
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
