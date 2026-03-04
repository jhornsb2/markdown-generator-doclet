package com.jhornsb2.doclet.generator.markdown.template.resolver;

import com.jhornsb2.doclet.generator.markdown.elements.PackageData;
import com.jhornsb2.doclet.generator.markdown.template.IBookmarkResolver;
import com.jhornsb2.doclet.generator.markdown.template.TemplateKind;
import com.jhornsb2.doclet.generator.markdown.template.TemplateRenderContext;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;
import lombok.NonNull;

/**
 * Resolves package-specific bookmarks.
 */
public class PackageBookmarkResolver implements IBookmarkResolver {

	@Override
	public boolean supports(@NonNull final TemplateRenderContext context) {
		return (
			context.getTemplateKind() == TemplateKind.PACKAGE &&
			context.getElementData() instanceof PackageData
		);
	}

	@Override
	public Map<String, String> resolve(
		@NonNull final TemplateRenderContext context
	) {
		final PackageData packageData = (PackageData) context.getElementData();
		final String packageName = packageData.getQualifiedName();
		final List<String> sortedContents = packageData
			.getPackageContents()
			.stream()
			.sorted()
			.toList();
		final String packageContents = packageData
			.getPackageContents()
			.stream()
			.sorted()
			.map(content -> "- " + content)
			.collect(Collectors.joining("\n"));
		final String packageContentsTree = renderTree(
			packageName,
			sortedContents
		);
		return Map.of(
			"package.contents",
			packageContents,
			"package.contents.flat",
			packageContents,
			"package.contents.tree",
			packageContentsTree
		);
	}

	private static String renderTree(
		@NonNull final String packageName,
		@NonNull final List<String> sortedContents
	) {
		final TreeNode root = new TreeNode();
		for (String content : sortedContents) {
			final String relativeName = toRelativeName(packageName, content);
			if (relativeName.isEmpty()) {
				continue;
			}

			TreeNode currentNode = root;
			for (String token : relativeName.split("\\.")) {
				currentNode = currentNode.children.computeIfAbsent(token, key ->
					new TreeNode()
				);
			}
		}

		final List<String> lines = new ArrayList<>();
		renderTreeNode(root, 0, lines);
		return String.join("\n", lines);
	}

	private static void renderTreeNode(
		@NonNull final TreeNode node,
		final int depth,
		@NonNull final List<String> lines
	) {
		for (Map.Entry<String, TreeNode> entry : node.children.entrySet()) {
			final String indent = "  ".repeat(depth);
			lines.add(indent + "- " + entry.getKey());
			renderTreeNode(entry.getValue(), depth + 1, lines);
		}
	}

	private static String toRelativeName(
		@NonNull final String packageName,
		@NonNull final String content
	) {
		final String packagePrefix = packageName + ".";
		if (content.startsWith(packagePrefix)) {
			return content.substring(packagePrefix.length());
		}
		return content;
	}

	private static final class TreeNode {

		private final Map<String, TreeNode> children = new TreeMap<>();
	}
}
