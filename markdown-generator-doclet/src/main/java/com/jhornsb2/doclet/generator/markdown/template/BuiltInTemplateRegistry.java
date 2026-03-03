package com.jhornsb2.doclet.generator.markdown.template;

import java.util.EnumMap;
import java.util.Map;
import lombok.NonNull;

/**
 * Default template registry backed by predefined template strings.
 */
public class BuiltInTemplateRegistry implements ITemplateRegistry {

	private static final String PROJECT_TEMPLATE = """
		# ${common.simpleName}

		${project.description}

		## Modules (${project.moduleCount})

		${project.modules}

		## Packages (${project.packageCount})

		${project.packages}
		""";

	private static final String PACKAGE_TEMPLATE = """
		# ${common.qualifiedName}

		${common.kind}

		## Description

		${common.docComment}

		## Contents

		${package.contents}
		""";

	private static final String INTERFACE_TEMPLATE = """
		# ${common.simpleName}

		${common.kindWithModifiers}

		## Description

		${common.docComment}

		## Public Methods

		${interface.methods}
		""";

	private static final String CLASS_TEMPLATE = """
		# ${common.simpleName}

		${common.kindWithModifiers}

		## Description

		${common.docComment}

		## Public Methods

		${class.methods}
		""";

	private final Map<TemplateKind, String> templates;

	public BuiltInTemplateRegistry() {
		Map<TemplateKind, String> configuredTemplates = new EnumMap<>(
			TemplateKind.class
		);
		configuredTemplates.put(TemplateKind.PROJECT, PROJECT_TEMPLATE);
		configuredTemplates.put(TemplateKind.PACKAGE, PACKAGE_TEMPLATE);
		configuredTemplates.put(TemplateKind.INTERFACE, INTERFACE_TEMPLATE);
		configuredTemplates.put(TemplateKind.CLASS, CLASS_TEMPLATE);
		this.templates = Map.copyOf(configuredTemplates);
	}

	@Override
	public String getTemplate(
		@NonNull final TemplateKind templateKind,
		@NonNull final TemplateRenderContext context
	) {
		return this.templates.getOrDefault(templateKind, "");
	}
}
