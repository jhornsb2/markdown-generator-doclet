# V1 Scope

Defines the frozen scope for the Mustache rewrite v1.

## In Scope

- Mustache-style rendering support for:
  - variable interpolation
  - section blocks
  - inverted section blocks
  - dotted-path lookup
- Template migration for these template kinds:
  - `PROJECT`
  - `PACKAGE`
  - `CLASS`
  - `INTERFACE`
  - method-group page template for overload rendering
- Typed bookmark values for migrated domains (`String`, `Boolean`, `Number`, `Map`, `List`).
- Conditional rendering for empty/non-empty sections.
- Loop rendering for repeated subsection content (for example overloads).

## Out of Scope

- Full expression language (no arbitrary condition expressions).
- Partials/layout inheritance (unless promoted later).
- New documentation domains outside current v1 domains.
- Major output layout redesign beyond existing structure parity.
- Backward-compatibility guarantees for external consumers (none currently).

## Supported Bookmark Domains (v1)

- `common.*`
- `project.*`
- `package.*`
- `class.*`
- `interface.*`
- `method.*` (method-group/overload views)

## Explicit Non-Goals

- Re-implementing a full scripting template language.
- Solving every `BOOKMARKS_TODO.md` item in v1.
- Performance optimization beyond baseline parity checks.

## Ownership

- Technical owner: _TBD_
- Reviewer(s): _TBD_
- Scope freeze date: _TBD_
