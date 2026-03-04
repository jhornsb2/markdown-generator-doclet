# V1 Output Contract

Defines expected markdown output structure and naming for v1 rewrite parity.

## Root Output Directory

- Root is configured by existing doclet output option.
- All relative paths below are rooted at that configured output directory.

## Project Page

- Path: `index.md`
- Source template kind: `PROJECT`
- Required sections:
  - project title
  - project description
  - project contents hierarchy

## Package Pages

- Path pattern: `<fully-qualified-name>/index.md`
- Example: `com/example/index.md`
- Source template kind: `PACKAGE`
- Required sections:
  - package title
  - description
  - contents list (or equivalent grouped rendering)

## Type Pages

- Path pattern: `<fully-qualified-name>/index.md`
- Source template kinds:
  - `CLASS`
  - `INTERFACE`
- Required sections are template-driven and conditional by content presence.
- Nested/Inner Types should be handled the same way.

## Method Group Pages

- Path pattern: `<fully-qualified-type-name>/<method-name>.md` (proposed v1 default)
- Source: method-group template
- Required behavior:
  - one file per method name
  - include all overloads of that name
  - each overload rendered with identical sub-block structure

## Property Pages

- Path pattern: `<fully-qualified-type-name>/<property-name>.md` (proposed v1 default)
- Source: method-group template
- Required behavior:
  - one file per property name

## Relative Link Rules

- All generated links must be relative within output tree.
- No absolute filesystem paths.
- No `file://` URIs.

## Filename Rules

- Markdown extension only (`.md`).
- Stable deterministic names for repeatable generation.
- Method-group names normalized to avoid illegal characters.

## Collision Handling

- If a name of a file collides with another, add `#<template-kind>` as a suffix to the name. If that resolves the issue, log a warning, otherwise log an error and exit since that should be a compile error.
- If the directory already exists, just write to it. It should be assumed that we are updating existing documentation.

## Reserved Filename Handling

- Reserved filename handling (`README`, platform-sensitive names). For this scenario, just continue as normal. We cannot guarantee what platform the docs are being generated for.
