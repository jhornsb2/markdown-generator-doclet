package com.jhornsb2.doclet.generator.markdown.processor.impl;

import com.jhornsb2.doclet.generator.markdown.logging.DocletLogger;
import com.jhornsb2.doclet.generator.markdown.processor.IDocletElementProcessor;
import com.jhornsb2.doclet.generator.markdown.util.DocCommentUtil;
import com.jhornsb2.doclet.generator.markdown.util.OptionUtil;
import com.sun.source.doctree.DocCommentTree;
import java.util.List;
import javax.lang.model.element.TypeElement;
import lombok.Value;

@Value
public class EnumElementProcessor implements IDocletElementProcessor {

	private static final DocletLogger log = DocletLogger.forClass(
		EnumElementProcessor.class
	);

	private static final String TEMPLATE = """
		# ${simpleName}

		${kind} in ${packageName}

		Implements ${interfaces}

		## Description

		${docComment}

		## Values

		${enumValues}

		## Properties

		${properties}

		## Public Methods

		${publicMethods}

		## Static Methods

		${staticMethods}

		""";

	TypeElement enumElement;
	DocCommentUtil docCommentUtil;

	public String getOutputFilepath() {
		log.debug(
			"Generating output file path for enum: {}",
			this.enumElement.getQualifiedName()
		);
		return OptionUtil.getInstance()
			.getOutputFilepathFactory()
			.forEnumElement(this.enumElement);
	}

	public String toMarkdownString() {
		log.debug(
			"Generating markdown for enum: {}",
			this.enumElement.getQualifiedName()
		);

		return EnumElementProcessor.TEMPLATE.replace(
			"${simpleName}",
			this.enumElement.getSimpleName().toString()
		)
			.replace("${kind}", this.enumElement.getKind().toString())
			.replace(
				"${packageName}",
				this.enumElement.getEnclosingElement().toString()
			)
			.replace(
				"${interfaces}",
				this.enumElement.getInterfaces().toString()
			)
			.replace(
				"${docComment}",
				this.docCommentUtil.getDocCommentTree(this.enumElement)
					.map(DocCommentTree::getFullBody)
					.map(List::toString)
					.orElse("")
			)
			.replace(
				"${enumValues}",
				this.enumElement.getEnclosedElements()
					.stream()
					.filter(
						element ->
							element.getKind() ==
							javax.lang.model.element.ElementKind.ENUM_CONSTANT
					)
					.map(element -> "- " + element.getSimpleName())
					.reduce((a, b) -> a + "\n" + b)
					.orElse("NO VALUES FOUND")
			)
			.replace("${properties}", "TODO: Implement properties")
			.replace("${publicMethods}", "TODO: Implement public methods")
			.replace("${staticMethods}", "TODO: Implement static methods");
	}
}
