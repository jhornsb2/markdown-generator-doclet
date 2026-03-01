package com.jhornsb2.doclet.generator.markdown.util;

import com.sun.source.doctree.DocCommentTree;
import java.util.Optional;
import javax.lang.model.element.Element;
import jdk.javadoc.doclet.DocletEnvironment;
import lombok.NonNull;
import lombok.Value;

/**
 * Utility class for working with Javadoc comments in the context of a
 * {@link DocletEnvironment}. Provides methods to retrieve and process doc
 * comments associated with Java elements.
 */
@Value
public class DocCommentUtil {

	/**
	 * The {@link DocletEnvironment} provides access to the elements being
	 * processed and their associated doc comments. This environment is
	 * typically provided by the Javadoc tool when the doclet is executed.
	 */
	DocletEnvironment environment;

	/**
	 * Retrieves the {@link DocCommentTree} for a given {@link Element}, if it
	 * exists. The {@link DocCommentTree} represents the structured content of
	 * the Javadoc comment associated with the element, including its
	 * description and any tags.
	 *
	 * @param element The {@link Element} for which to retrieve the doc comment.
	 *                This is typically a class, method, field, or package
	 *                {@link Element}.
	 * @return An {@link Optional} containing the {@link DocCommentTree} if a
	 *         doc comment is present for the element, or an empty
	 *         {@link Optional} if no doc comment is associated with the
	 *         element.
	 */
	public Optional<DocCommentTree> getDocCommentTree(
		@NonNull final Element element
	) {
		return Optional.ofNullable(
			this.environment.getDocTrees().getDocCommentTree(element)
		);
	}
}
