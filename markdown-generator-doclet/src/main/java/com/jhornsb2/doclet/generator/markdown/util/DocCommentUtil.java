package com.jhornsb2.doclet.generator.markdown.util;

import com.sun.source.doctree.DocCommentTree;
import java.util.Optional;
import javax.lang.model.element.Element;
import jdk.javadoc.doclet.DocletEnvironment;
import lombok.NonNull;

public class DocCommentUtil {

	private final DocletEnvironment environment;

	public DocCommentUtil(@NonNull final DocletEnvironment environment) {
		this.environment = environment;
	}

	public Optional<DocCommentTree> getDocCommentTree(
		@NonNull final Element element
	) {
		return Optional.ofNullable(
			this.environment.getDocTrees().getDocCommentTree(element)
		);
	}
}
