package com.github.jhornsb2.doclet.generator.markdown.processor.impl;

import javax.lang.model.element.TypeElement;

import com.github.jhornsb2.doclet.generator.markdown.logging.DocletLogger;
import com.github.jhornsb2.doclet.generator.markdown.processor.IDocletElementProcessor;

import lombok.Value;

@Value
public class ClassElementProcessor implements IDocletElementProcessor {

    private static final DocletLogger log = DocletLogger.forClass(ClassElementProcessor.class);

    TypeElement classElement;

    public String toMarkdownString() {
        log.debug("Generating markdown for class: {}", classElement.getQualifiedName());
        StringBuilder sb = new StringBuilder();
        
        sb.append("# ").append(classElement.getSimpleName()).append("\n\n");

        return sb.toString();
    }

}
