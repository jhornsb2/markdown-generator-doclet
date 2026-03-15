package com.jhornsb2.doclet.generator.markdown.elements.factory;

import com.jhornsb2.doclet.generator.markdown.elements.VariableData;
import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.VariableElement;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.NonNull;

/**
 * Factory for creating parameter {@link VariableData} values.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ParameterDataFactory {

	/**
	 * Creates parameter data entries for all parameters of an executable.
	 *
	 * @param executableElement Method or constructor element.
	 * @return Immutable-like list of parameter data values.
	 */
	public static List<VariableData> createParameters(
		@NonNull final ExecutableElement executableElement
	) {
		final List<? extends VariableElement> parameterElements =
			executableElement.getParameters();
		if (parameterElements == null || parameterElements.isEmpty()) {
			return Collections.emptyList();
		}

		final int lastIndex = parameterElements.size() - 1;
		final boolean executableIsVarArgs = executableElement.isVarArgs();
		return IntStream.range(0, parameterElements.size())
			.mapToObj(index -> {
				final VariableElement parameterElement = parameterElements.get(
					index
				);
				return createParameter(
					parameterElement,
					index,
					executableIsVarArgs && index == lastIndex
				);
			})
			.toList();
	}

	private static VariableData createParameter(
		@NonNull final VariableElement parameterElement,
		final int position,
		final boolean isVarArgsParameter
	) {
		final String type = parameterElement.asType().toString();
		final List<? extends AnnotationMirror> annotationMirrors =
			parameterElement.getAnnotationMirrors();
		final List<String> annotations =
			annotationMirrors == null
				? Collections.emptyList()
				: annotationMirrors
						.stream()
						.map(AnnotationMirror::getAnnotationType)
						.map(Object::toString)
						.toList();

		return VariableData.builder()
			.simpleName(parameterElement.getSimpleName().toString())
			.type(type)
			.returnType(type)
			.position(position)
			.varArgs(isVarArgsParameter)
			.annotations(annotations)
			.build();
	}
}
