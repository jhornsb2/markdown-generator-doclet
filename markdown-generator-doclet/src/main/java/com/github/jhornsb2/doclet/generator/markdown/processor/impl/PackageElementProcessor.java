package com.github.jhornsb2.doclet.generator.markdown.processor.impl;

import java.util.Optional;

import javax.lang.model.element.PackageElement;

import com.github.jhornsb2.doclet.generator.markdown.logging.DocletLogger;
import com.github.jhornsb2.doclet.generator.markdown.processor.IDocletElementProcessor;
import com.github.jhornsb2.doclet.generator.markdown.util.DocCommentUtil;
import com.sun.source.doctree.DocCommentTree;

import lombok.Value;

@Value
public class PackageElementProcessor implements IDocletElementProcessor {

	private static final DocletLogger log = DocletLogger.forClass(PackageElementProcessor.class);

	/**
	 * The package element to process.
	 */
	PackageElement packageElement;

	public String toMarkdownString() {
		log.debug("Generating markdown for package: {}", packageElement.getQualifiedName());
		Optional<DocCommentTree> docCommentTree = DocCommentUtil.getDocCommentTree(packageElement);
		StringBuilder sb = new StringBuilder();
		sb.append("# ").append(packageElement.getQualifiedName()).append("\n\n");
		docCommentTree.ifPresent(tree -> sb.append(tree.getFullBody()).append("\n\n"));
		sb.append("## Contents\n\n");

		packageElement.getEnclosedElements().stream().filter(element -> element.getKind().isClass()).forEach(
			element -> sb.append("- ").append(element.getSimpleName()).append("\n")
		);

		return sb.toString();
	}

}
