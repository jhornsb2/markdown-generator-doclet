package com.jhornsb2.doclet.generator.markdown.template;

import java.util.EnumMap;
import java.util.Map;
import lombok.NonNull;

/**
 * Default template registry backed by predefined template strings.
 */
public class BuiltInTemplateRegistry implements ITemplateRegistry {

	private static final String PROJECT_TEMPLATE = """
		# ${project.name}

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

		${common.description}

		## Contents

		${package.contents}
		""";

	private static final String INTERFACE_TEMPLATE = """
		# ${common.simpleName}

		${common.kindWithModifiers}

		## Description

		${common.description}

		## Public Methods

		${interface.methods}
		""";

	private static final String CLASS_TEMPLATE = """
		# ${common.simpleName}

		${common.kindWithModifiers}

		## Description

		${common.description}

		## Public Constants

		${class.constants.public}

		## Static Methods

		${class.methods.static}

		## Properties

		${class.properties.public}

		## Public Methods

		${class.methods.public}

		## Inherited Members

		### Static Methods

		${class.inheritedMethods.static}

		### Properties

		${class.inheritedProperties.public}

		### Public Methods

		${class.inheritedMethods.public}

		""";

	private static final String METHOD_TEMPLATE = """
		#  ${method.className}.${method.simpleName}

		${method.overloads}
		""";

	private static final String METHOD_OVERLOAD_BLOCK_TEMPLATE = """
		## Declaration

		${method.signature}

		## Parameters

		${method.parameters.table}

		## Returns

		${method.returns}

		## Description

		${method.description}
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
