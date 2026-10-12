package com.jhornsb2.doclet.generator.markdown.options;

import lombok.Builder;
import lombok.Value;

/**
 * Class that contains the runtime option details that should be used when
 * running the markdown generation command.
 */
@Value
@Builder(toBuilder = true)
public class MarkdownGeneratorRuntimeOptions {}
