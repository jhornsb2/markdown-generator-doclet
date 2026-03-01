package com.jhornsb2.doclet.generator.markdown.processor.impl;

import com.jhornsb2.doclet.generator.markdown.logging.DocletLogger;
import com.jhornsb2.doclet.generator.markdown.naming.QualifiedNameResolver;
import com.jhornsb2.doclet.generator.markdown.options.DocletOptions;
import com.jhornsb2.doclet.generator.markdown.processor.IDocletElementProcessor;
import com.jhornsb2.doclet.generator.markdown.util.DocCommentUtil;
import com.sun.source.doctree.DocCommentTree;
import java.util.List;
import java.util.Optional;
import javax.lang.model.element.PackageElement;
import lombok.Value;

@Value
public class PackageElementProcessor implements IDocletElementProcessor {

	private static final DocletLogger log = DocletLogger.forClass(
		PackageElementProcessor.class
	);

	private static final String TEMPLATE = """
		# ${qualifiedName}

		${docComment}

		## Contents

		${packageContents}

		""";

	/**
	 * The package element to process.
	 */
	PackageElement packageElement;
	DocCommentUtil docCommentUtil;
	DocletOptions docletOptions;

	public String getOutputFilepath() {
		log.debug(
			"Generating output file path for package: {}",
			QualifiedNameResolver.qualifiedNameOf(this.packageElement)
		);
		return this.docletOptions
			.getOutputFilepathStrategy()
			.forPackageElement(this.packageElement);
	}

	public String toMarkdownString() {
		log.debug(
			"Generating markdown for package: {}",
			QualifiedNameResolver.qualifiedNameOf(this.packageElement)
		);
		final Optional<DocCommentTree> docCommentTree =
			this.docCommentUtil.getDocCommentTree(this.packageElement);
		return PackageElementProcessor.TEMPLATE.replace(
			"${qualifiedName}",
			QualifiedNameResolver.qualifiedNameOf(this.packageElement)
		)
			.replace(
				"${docComment}",
				docCommentTree
					.map(DocCommentTree::getFullBody)
					.map(List::toString)
					.orElse("")
			)
			.replace(
				"${packageContents}",
				this.packageElement.getEnclosedElements()
					.stream()
					.filter(element -> element.getKind().isClass())
					.map(element -> "- " + element.getSimpleName())
					.reduce((a, b) -> a + "\n" + b)
					.orElse("")
			);
	}
}
