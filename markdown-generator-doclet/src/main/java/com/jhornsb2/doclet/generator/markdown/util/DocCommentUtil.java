package com.jhornsb2.doclet.generator.markdown.util;

import com.sun.source.doctree.DocCommentTree;
import java.util.Optional;
import javax.lang.model.element.Element;
import jdk.javadoc.doclet.DocletEnvironment;
import lombok.NonNull;
import lombok.Value;

@Value
public class DocCommentUtil {

	DocletEnvironment environment;

	public Optional<DocCommentTree> getDocCommentTree(
		@NonNull final Element element
	) {
		return Optional.ofNullable(
			this.environment.getDocTrees().getDocCommentTree(element)
		);
	}
}
