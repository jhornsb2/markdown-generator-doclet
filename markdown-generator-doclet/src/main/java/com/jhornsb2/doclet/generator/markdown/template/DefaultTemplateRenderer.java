package com.jhornsb2.doclet.generator.markdown.template;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import lombok.NonNull;
import lombok.Value;

/**
 * Template renderer that replaces ${bookmark} tokens with resolved values.
 */
@Value
public class DefaultTemplateRenderer implements TemplateRenderer {

	private static final Pattern BOOKMARK_PATTERN = Pattern.compile(
		"\\$\\{([^}]+)}"
	);

	@NonNull
	TemplateRegistry templateRegistry;

	@NonNull
	List<BookmarkResolver> bookmarkResolvers;

	@Override
	public String render(
		@NonNull final TemplateKind templateKind,
		@NonNull final TemplateRenderContext context
	) {
		final String template = this.templateRegistry.getTemplate(
			templateKind,
			context
		);
		final Map<String, String> resolvedBookmarks = new LinkedHashMap<>();

		this.bookmarkResolvers.stream()
			.filter(resolver -> resolver.supports(context))
			.forEach(resolver ->
				resolvedBookmarks.putAll(resolver.resolve(context))
			);

		final Matcher matcher = BOOKMARK_PATTERN.matcher(template);
		final StringBuffer rendered = new StringBuffer();
		while (matcher.find()) {
			final String bookmark = matcher.group(1);
			final String value = resolvedBookmarks.getOrDefault(bookmark, "");
			matcher.appendReplacement(
				rendered,
				Matcher.quoteReplacement(value)
			);
		}
		matcher.appendTail(rendered);
		return rendered.toString();
	}
}
