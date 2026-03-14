package com.jhornsb2.doclet.generator.markdown.lookup;

import com.jhornsb2.doclet.generator.markdown.elements.IElementData;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import javax.lang.model.element.Element;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.Value;

/**
 * An index that maps qualified names to various pieces of data that are
 * associated with the element with that qualified name.
 */
@Value
@AllArgsConstructor(access = lombok.AccessLevel.PACKAGE)
public class QualifiedNameIndex {

	private static final int DEFAULT_INITIAL_CAPACITY = 16;

	/**
	 * Maps qualified names to the path of the documentation file.
	 */
	Map<String, String> qualifiedNameToFilePath;

	/**
	 * Maps qualified names to the {@link Element}.
	 */
	Map<String, Element> qualifiedNameToElement;

	/**
	 * Maps qualified names to the {@link IElementData}.
	 */
	Map<String, IElementData> qualifiedNameToElementData;

	public QualifiedNameIndex() {
		this(DEFAULT_INITIAL_CAPACITY);
	}

	public QualifiedNameIndex(int initialCapacity) {
		this(
			new ConcurrentHashMap<>(initialCapacity),
			new ConcurrentHashMap<>(initialCapacity),
			new ConcurrentHashMap<>(initialCapacity)
		);
	}

	/**
	 * Adds an entry to the index.
	 *
	 * @param entry The {@link QualifiedNameIndexEntry} to add.
	 */
	public void addEntry(@NonNull final QualifiedNameIndexEntry entry) {
		this.qualifiedNameToFilePath.put(
			entry.getQualifiedName(),
			entry.getFilePath()
		);
		this.qualifiedNameToElement.put(
			entry.getQualifiedName(),
			entry.getElement()
		);
		this.qualifiedNameToElementData.put(
			entry.getQualifiedName(),
			entry.getElementData()
		);
	}

	/**
	 * Adds multiple entries to the index.
	 *
	 * @param entries The {@link QualifiedNameIndexEntry}s to add.
	 */
	public void addEntries(
		@NonNull final Collection<QualifiedNameIndexEntry> entries
	) {
		entries.forEach(this::addEntry);
	}

	/**
	 * Gets the file path for the documentation file associated with the element
	 * with the given qualified name.
	 *
	 * @param qualifiedName The qualified name of the element.
	 * @return The file path of the documentation file.
	 */
	public String getFilePath(@NonNull final String qualifiedName) {
		return this.qualifiedNameToFilePath.get(qualifiedName);
	}

	/**
	 * Gets the {@link Element} associated with the element with the given
	 * qualified name.
	 *
	 * @param qualifiedName The qualified name of the element.
	 * @return The {@link Element}.
	 */
	public Element getElement(@NonNull final String qualifiedName) {
		return this.qualifiedNameToElement.get(qualifiedName);
	}

	/**
	 * Gets the {@link IElementData} associated with the element with the given
	 * qualified name.
	 *
	 * @param qualifiedName The qualified name of the element.
	 * @return The {@link IElementData}.
	 */
	public IElementData getElementData(@NonNull final String qualifiedName) {
		return this.qualifiedNameToElementData.get(qualifiedName);
	}

	/**
	 * Gets a {@link QualifiedNameIndexEntry} for the element with the given
	 * qualified name.
	 *
	 * @param qualifiedName The qualified name of the element.
	 * @return The {@link QualifiedNameIndexEntry} containing the data for the
	 *         element.
	 */
	public QualifiedNameIndexEntry getEntry(
		@NonNull final String qualifiedName
	) {
		return QualifiedNameIndexEntry.builder()
			.qualifiedName(qualifiedName)
			.filePath(this.getFilePath(qualifiedName))
			.element(this.getElement(qualifiedName))
			.elementData(this.getElementData(qualifiedName))
			.build();
	}
}
