package com.jhornsb2.doclet.generator.markdown.processor.impl;

import java.util.List;
import java.util.Optional;

import javax.lang.model.element.PackageElement;

import com.jhornsb2.doclet.generator.markdown.logging.DocletLogger;
import com.jhornsb2.doclet.generator.markdown.processor.IDocletElementProcessor;
import com.jhornsb2.doclet.generator.markdown.util.DocCommentUtil;
import com.sun.source.doctree.DocCommentTree;

import lombok.Value;

@Value
public class PackageElementProcessor implements IDocletElementProcessor {

	private static final DocletLogger log = DocletLogger.forClass(PackageElementProcessor.class);

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

	public String getOutputFilepath() {
		log.debug("Generating output file path for package: {}", this.packageElement.getQualifiedName());
		return this.packageElement.getQualifiedName().toString().replace('.', '/') + "/index.md";
	}

	public String toMarkdownString() {
		log.debug("Generating markdown for package: {}", this.packageElement.getQualifiedName());
		final Optional<DocCommentTree> docCommentTree = DocCommentUtil.getDocCommentTree(this.packageElement);
		return PackageElementProcessor.TEMPLATE.replace("${qualifiedName}", this.packageElement.getQualifiedName())
			.replace("${docComment}", docCommentTree.map(DocCommentTree::getFullBody).map(List::toString).orElse(""))
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
