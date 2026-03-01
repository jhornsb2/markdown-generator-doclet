package com.jhornsb2.doclet.generator.markdown.processor.impl;

import com.jhornsb2.doclet.generator.markdown.logging.DocletLogger;
import com.jhornsb2.doclet.generator.markdown.options.DocletOptions;
import com.jhornsb2.doclet.generator.markdown.processor.IDocletElementProcessor;
import com.jhornsb2.doclet.generator.markdown.util.DocCommentUtil;
import com.sun.source.doctree.DocCommentTree;
import java.util.List;
import javax.lang.model.element.TypeElement;
import lombok.Value;

@Value
public class AnnotationElementProcessor implements IDocletElementProcessor {

	private static final DocletLogger log = DocletLogger.forClass(
		AnnotationElementProcessor.class
	);

	private static final String TEMPLATE = """
		# ${simpleName}

		${kind} in ${packageName}

		## Description

		${docComment}

		""";

	TypeElement annotationElement;
	DocCommentUtil docCommentUtil;
	DocletOptions docletOptions;

	public String getOutputFilepath() {
		log.debug(
			"Generating output file path for annotation: {}",
			this.annotationElement.getQualifiedName()
		);
		return this.docletOptions.getOutputFilepathStrategy().forAnnotationElement(
			this.annotationElement
		);
	}

	public String toMarkdownString() {
		log.debug(
			"Generating markdown for annotation: {}",
			this.annotationElement.getQualifiedName()
		);

		return AnnotationElementProcessor.TEMPLATE.replace(
			"${simpleName}",
			this.annotationElement.getSimpleName().toString()
		)
			.replace("${kind}", this.annotationElement.getKind().toString())
			.replace(
				"${packageName}",
				this.annotationElement.getEnclosingElement().toString()
			)
			.replace(
				"${docComment}",
				this.docCommentUtil.getDocCommentTree(this.annotationElement)
					.map(DocCommentTree::getFullBody)
					.map(List::toString)
					.orElse("")
			);
	}
}
