package com.jhornsb2.doclet.generator.markdown.processor.impl;

import com.jhornsb2.doclet.generator.markdown.options.DocletOptions;
import com.jhornsb2.doclet.generator.markdown.processor.IDocletElementProcessor;
import javax.lang.model.element.ModuleElement;
import javax.lang.model.element.QualifiedNameable;
import lombok.Value;

@Value
public class ModuleElementProcessor implements IDocletElementProcessor {

	private static final String TEMPLATE = """
		# ${simpleName}

		${kind} in ${packageName}

		## Description

		${docComment}

		## Contents

		${contents}

		""";

	ModuleElement moduleElement;
	DocletOptions docletOptions;

	public String getOutputFilepath() {
		return this.docletOptions.getOutputFilepathStrategy().forModuleElement(
			this.moduleElement
		);
	}

	public String toMarkdownString() {
		return TEMPLATE.replace(
			"${simpleName}",
			this.moduleElement.getSimpleName().toString()
		)
			.replace("${kind}", "Module")
			.replace("${docComment}", "Module description not available")
			.replace(
				"${contents}",
				moduleElement
					.getEnclosedElements()
					.stream()
					.filter(QualifiedNameable.class::isInstance)
					.map(QualifiedNameable.class::cast)
					.map(e -> "- " + e.getQualifiedName())
					.reduce((a, b) -> a + "\n" + b)
					.orElse("")
			);
	}
}
