package com.jhornsb2.doclet.generator.markdown.template;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.jhornsb2.doclet.generator.markdown.elements.PackageData;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.junit.jupiter.api.Test;

class ITemplateRendererTest {

	@Test
	void rendersUsingRegistryAndBookmarkResolvers() {
		ITemplateRegistry registry = (templateKind, context) ->
			"Hello ${name} (${kind})";
		IBookmarkResolver resolver = new IBookmarkResolver() {
			@Override
			public boolean supports(final TemplateRenderContext context) {
				return true;
			}

			@Override
			public Map<String, String> resolve(final TemplateRenderContext context) {
				return Map.of("name", "World", "kind", context.getTemplateKind().name());
			}
		};
		ITemplateRenderer renderer = new DefaultTemplateRenderer(
			registry,
			List.of(resolver)
		);

		String rendered = renderer.render(
			TemplateKind.PACKAGE,
			TemplateRenderContext.builder()
				.templateKind(TemplateKind.PACKAGE)
				.elementData(
					PackageData.builder()
						.simpleName("pkg")
						.qualifiedName("com.example")
						.kind("package")
						.docComment("")
						.packageContents(Set.of())
						.build()
				)
				.build()
		);

		assertEquals("Hello World (PACKAGE)", rendered);
	}
}
