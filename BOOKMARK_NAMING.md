# Bookmark Naming Specification

This document defines the canonical naming convention for template bookmarks.

## Goals

- Keep existing bookmarks stable and backward compatible.
- Make bookmark names predictable and parseable.
- Support filters and alternate render views without inventing one-off names.

## Canonical Pattern

Use dot-separated lowercase tokens:

`domain.subject[.facet...][.view]`

- `domain`: high-level area (`common`, `project`, `package`, `class`, `interface`, etc.)
- `subject`: data target (`contents`, `methods`, `fields`, etc.)
- `facet`: zero or more filters/grouping keys
- `view`: optional rendering style, always the last token

## Ordering Rules

When facets are present, keep this order:

1. Visibility (`public`, `protected`, `package`, `private`)
2. Scope (`instance`, `static`)
3. Additional modifiers (`abstract`, `final`, `sealed`, `nonSealed`)
4. Grouping key (only for grouped output, e.g. `byKind`, `byVisibility`)
5. View (`flat`, `tree`, `grouped`, `table`, `summary`, `detailed`)

Examples:

- `class.methods`
- `class.methods.public`
- `class.methods.public.static`
- `class.methods.public.static.table`
- `package.contents`
- `package.contents.tree`
- `package.contents.byKind.grouped`

## Views

- `flat`: single-level list (default if view token is omitted)
- `tree`: hierarchical output
- `grouped`: grouped output (use with a `by...` grouping facet)
- `table`: table-formatted output
- `summary`: compact, high-level output
- `detailed`: verbose output

## Naming Guidelines

- Keep `domain.subject` as the legacy/default bookmark whenever possible.
- Reserve `qualified` for identity/name semantics (for example `qualifiedName`), not render style.
- Use `kindWithModifiers` for display strings that combine modifiers + type kind
  (for example `abstract class`, `sealed interface`).
- Avoid synonyms for the same concept (`tree` vs `hierarchical`); choose one canonical token.
- Do not use uppercase, spaces, or separators other than `.`.

## Recommended Canonical Tokens

### Visibility

- `public`
- `protected`
- `package`
- `private`

### Scope

- `instance`
- `static`

### Grouping Keys

- `byVisibility`
- `byKind`
- `byPackage`
- `byModule`

### Views

- `flat`
- `tree`
- `grouped`
- `table`
- `summary`
- `detailed`
