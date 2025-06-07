package com.github.jhornsb2.doclet.generator.markdown;

import jdk.javadoc.doclet.Doclet;
import jdk.javadoc.doclet.Reporter;
import jdk.javadoc.doclet.DocletEnvironment;

import javax.lang.model.SourceVersion;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic.Kind;

import com.github.jhornsb2.doclet.generator.markdown.options.Flag;
import com.github.jhornsb2.doclet.generator.markdown.options.GenericOption;

import java.util.Set;
import java.util.Locale;

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
        environment.getIncludedElements().stream()
            .filter(element -> element.getKind() == ElementKind.CLASS)
            .map(element -> (TypeElement) element)
            .forEach(typeElement -> {
                String className = typeElement.getQualifiedName().toString();
                reporter.print(Kind.NOTE, "Found class: " + className);
            });
        return true;
    }
}