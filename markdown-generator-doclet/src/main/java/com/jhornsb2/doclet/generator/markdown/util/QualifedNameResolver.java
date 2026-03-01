package com.jhornsb2.doclet.generator.markdown.util;

import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.QualifiedNameable;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.TypeMirror;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.NonNull;

/**
 * Resolves simple and qualified names for {@link Element} instances.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class QualifedNameResolver {

	/**
	 * Resolves a fully-qualified-like name for the supplied element.
	 * <p>
	 * For elements implementing {@link QualifiedNameable}, this returns
	 * {@link QualifiedNameable#getQualifiedName()}. For executable and variable
	 * elements, this composes a member-style name from the enclosing element.
	 *
	 * @param element Element to resolve.
	 * @return Resolved qualified name string.
	 */
	public static String qualifiedNameOf(@NonNull final Element element) {
		if (element instanceof QualifiedNameable qualifiedNameable) {
			return qualifiedNameable.getQualifiedName().toString();
		}

		if (element instanceof ExecutableElement executableElement) {
			return qualifiedMemberNameOf(executableElement);
		}

		if (element instanceof VariableElement variableElement) {
			return qualifiedMemberNameOf(variableElement);
		}

		return element.toString();
	}

	/**
	 * Resolves the simple name for the supplied element.
	 *
	 * @param element Element to resolve.
	 * @return Simple name string.
	 */
	public static String simpleNameOf(@NonNull final Element element) {
		return element.getSimpleName().toString();
	}

	/**
	 * Creates a member-style qualified name for an executable element.
	 *
	 * @param executableElement The executable element.
	 * @return Qualified member name like
	 *         {@code com.example.Type#method(java.lang.String)}.
	 */
	public static String qualifiedMemberNameOf(
		@NonNull final ExecutableElement executableElement
	) {
		final String enclosingName = qualifiedNameOf(
			executableElement.getEnclosingElement()
		);
		final String parameterTypes = executableElement
			.getParameters()
			.stream()
			.map(VariableElement::asType)
			.map(TypeMirror::toString)
			.reduce((left, right) -> left + "," + right)
			.orElse("");

		return String.format(
			"%s#%s(%s)",
			enclosingName,
			executableElement.getSimpleName(),
			parameterTypes
		);
	}

	/**
	 * Creates a member-style qualified name for a variable element.
	 *
	 * @param variableElement The variable element.
	 * @return Qualified member name like {@code com.example.Type#field}.
	 */
	public static String qualifiedMemberNameOf(
		@NonNull final VariableElement variableElement
	) {
		return String.format(
			"%s#%s",
			qualifiedNameOf(variableElement.getEnclosingElement()),
			variableElement.getSimpleName()
		);
	}

	/**
	 * Converts a qualified name into a slash-delimited path.
	 *
	 * @param qualifiedName Fully qualified name.
	 * @return Slash-delimited path.
	 */
	public static String toPath(@NonNull final String qualifiedName) {
		return qualifiedName.replace('.', '/');
	}

	/**
	 * Resolves a slash-delimited path for the supplied element.
	 *
	 * @param element Element to resolve.
	 * @return Slash-delimited path.
	 */
	public static String pathOf(@NonNull final Element element) {
		if (element instanceof PackageElement packageElement) {
			return toPath(packageElement.getQualifiedName().toString());
		}

		return toPath(qualifiedNameOf(element));
	}
}
