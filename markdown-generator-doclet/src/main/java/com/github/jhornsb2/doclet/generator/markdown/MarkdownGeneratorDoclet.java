package com.github.jhornsb2.doclet.generator.markdown;

import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.Set;

import javax.lang.model.SourceVersion;
import javax.tools.Diagnostic.Kind;

import com.github.jhornsb2.doclet.generator.markdown.options.Flag;
import com.github.jhornsb2.doclet.generator.markdown.options.GenericOption;

import jdk.javadoc.doclet.Doclet;
import jdk.javadoc.doclet.DocletEnvironment;
import jdk.javadoc.doclet.Reporter;

public class MarkdownGeneratorDoclet implements Doclet {

	private Flag noTimestamp = new Flag("-notimestamp", "Do not include hidden time stamp");
	private GenericOption destinationDir = new GenericOption("-d", "Destination directory for output files", "/tmp");
	private Reporter reporter;

	@Override
	public void init(Locale locale, Reporter reporter) {
		this.reporter = reporter;
	}

	@Override
	public String getName() {
		return getClass().getSimpleName();
	}

	@Override
	public Set<Option> getSupportedOptions() {
		return Set.of(noTimestamp, destinationDir);
	}

	@Override
	public SourceVersion getSupportedSourceVersion() {
		return SourceVersion.latest();
	}

	@Override
	public boolean run(DocletEnvironment environment) {
		environment.getIncludedElements().forEach(element -> {
			reporter.print(
				Kind.NOTE,
				String.format(
					"Processing element %s of kind %s element class %s",
					element.getSimpleName(),
					element.getKind(),
					element.getClass().getSimpleName()
				)
			);
			reporter.print(
				Kind.NOTE,
				Optional.ofNullable(environment.getDocTrees().getDocCommentTree(element))
					.map(docTree -> docTree.getFullBody())
					.map(List::toString)
					.orElse("")
			);
		});
		return true;
	}
}