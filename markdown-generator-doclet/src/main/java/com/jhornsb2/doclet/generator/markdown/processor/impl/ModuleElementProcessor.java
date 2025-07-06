package com.jhornsb2.doclet.generator.markdown.processor.impl;

import javax.lang.model.element.ModuleElement;
import javax.lang.model.element.QualifiedNameable;

import com.jhornsb2.doclet.generator.markdown.processor.IDocletElementProcessor;

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

	public String getOutputFilepath() {
		return this.moduleElement.getQualifiedName().toString().replace('.', '/') + "/index.md";
	}

	public String toMarkdownString() {
		return TEMPLATE.replace("${simpleName}", this.moduleElement.getSimpleName().toString())
			.replace("${kind}", "Module")
			.replace("${docComment}", "Module description not available")
			.replace(
				"${contents}",
				moduleElement.getEnclosedElements()
					.stream()
					.filter(QualifiedNameable.class::isInstance)
					.map(QualifiedNameable.class::cast)
					.map(e -> "- " + e.getQualifiedName())
					.reduce((a, b) -> a + "\n" + b)
					.orElse("")
			);
	}

}
