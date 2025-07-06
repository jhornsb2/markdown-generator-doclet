package com.jhornsb2.doclet.generator.markdown.util;

import java.util.Optional;

import javax.lang.model.element.Element;

import com.sun.source.doctree.DocCommentTree;

import jdk.javadoc.doclet.DocletEnvironment;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DocCommentUtil {

	private static DocletEnvironment environment;

	public static void setEnvironment(DocletEnvironment env) {
		if (environment != null) {
			throw new IllegalStateException("DocletEnvironment has already been set.");
		}
		environment = env;
	}

	public static Optional<DocCommentTree> getDocCommentTree(@NonNull Element element) {
		return Optional.ofNullable(environment.getDocTrees().getDocCommentTree(element));
	}

}
