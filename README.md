# markdown-generator-doclet

A java doclet to allow people to generate their javadoc in markdown format.

## Doclet Flags

The doclet currently supports the following command-line flags:

| Flag            | Type   | Default        | Allowed values                   | Description                                                          |
| --------------- | ------ | -------------- | -------------------------------- | -------------------------------------------------------------------- |
| `-d`            | option | `/tmp`         | any directory path               | Destination directory for generated markdown files.                  |
| `-path-layout`  | option | `hierarchical` | `hierarchical`, `h`, `flat`, `f` | Controls output filepath layout strategy.                            |
| `-template-dir` | option | _(empty)_      | existing directory path          | Directory containing custom templates (`*.md`).                      |
| `-log-level`    | option | `warn`         | `debug`, `info`, `warn`, `error` | Minimum logging level to emit.                                       |
| `-notimestamp`  | flag   | off            | present/absent                   | Supported for Gradle compatibility; currently ignored by the doclet. |

### Example

```bash
javadoc \
	-doclet com.jhornsb2.doclet.generator.markdown.MarkdownGeneratorDoclet \
	-docletpath path/to/markdown-generator-doclet.jar \
	-d build/docs \
	-path-layout hierarchical \
	-template-dir templates \
	-log-level warn \
	-notimestamp \
	<sources>
```
