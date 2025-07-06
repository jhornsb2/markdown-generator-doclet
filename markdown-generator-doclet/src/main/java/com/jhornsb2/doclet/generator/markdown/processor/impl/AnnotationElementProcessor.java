package com.jhornsb2.doclet.generator.markdown.processor.impl;

import java.util.List;

import javax.lang.model.element.TypeElement;

import com.jhornsb2.doclet.generator.markdown.logging.DocletLogger;
import com.jhornsb2.doclet.generator.markdown.processor.IDocletElementProcessor;
import com.jhornsb2.doclet.generator.markdown.util.DocCommentUtil;
import com.sun.source.doctree.DocCommentTree;

import lombok.Value;

@Value
public class AnnotationElementProcessor implements IDocletElementProcessor {

	private static final DocletLogger log = DocletLogger.forClass(AnnotationElementProcessor.class);

	private static final String TEMPLATE = """
		# ${simpleName}

		${kind} in ${packageName}

		## Description

		${docComment}

		""";

	TypeElement annotationElement;

	public String getOutputFilepath() {
		log.debug("Generating output file path for annotation: {}", this.annotationElement.getQualifiedName());
		return this.annotationElement.getQualifiedName().toString().replace('.', '/') + "/index.md";
	}

	public String toMarkdownString() {
		log.debug("Generating markdown for annotation: {}", this.annotationElement.getQualifiedName());

		return AnnotationElementProcessor.TEMPLATE.replace("${simpleName}", this.annotationElement.getSimpleName().toString())
			.replace("${kind}", this.annotationElement.getKind().toString())
			.replace("${packageName}", this.annotationElement.getEnclosingElement().toString())
			.replace(
				"${docComment}",
				DocCommentUtil.getDocCommentTree(this.annotationElement)
					.map(DocCommentTree::getFullBody)
					.map(List::toString)
					.orElse("")
			);
	}

}
