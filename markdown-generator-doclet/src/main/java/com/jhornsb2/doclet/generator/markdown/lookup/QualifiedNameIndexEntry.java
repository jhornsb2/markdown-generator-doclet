package com.jhornsb2.doclet.generator.markdown.lookup;

import com.jhornsb2.doclet.generator.markdown.elements.IElementData;
import javax.lang.model.element.Element;
import lombok.Builder;
import lombok.Value;

/**
 * An entry in the {@link QualifiedNameIndex} that contains the qualified name,
 * file path, element, and element data associated with an element.
 */
@Value
@Builder
public class QualifiedNameIndexEntry {

	/**
	 * The qualified name of the element. This is the key that is used to look
	 * up the file path, element, and element data in the
	 * {@link QualifiedNameIndex}.
	 */
	String qualifiedName;

	/**
	 * The path of the documentation file associated with the element with the
	 * qualified name.
	 */
	String filePath;

	/**
	 * The {@link Element} associated with the element with the given qualified
	 * name.
	 */
	Element element;

	/**
	 * The {@link IElementData} associated with the element with the given
	 * qualified name.
	 */
	IElementData elementData;
}
