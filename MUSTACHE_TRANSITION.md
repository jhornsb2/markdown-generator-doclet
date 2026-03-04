# Mustache Transition TODO

Task tree for rewriting the doclet around Mustache-style templating.

Priority legend:

- `P0` = required foundation / rewrite blocker
- `P1` = high value core migration
- `P2` = quality, hardening, and polish

## 0) scope-and-contract

| Done | Priority | Task ID                     | Description                                                                                           |
| ---- | -------- | --------------------------- | ----------------------------------------------------------------------------------------------------- |
| [ ]  | P0       | `scope.freeze.v1`           | Freeze rewrite scope: supported template kinds, output layout, and bookmark domains for v1.           |
| [ ]  | P0       | `scope.acceptance.criteria` | Define explicit acceptance criteria for parity on project/package/class/interface outputs.            |
| [ ]  | P0       | `scope.golden.fixtures`     | Select and commit 3-5 representative fixture projects for golden-output verification.                 |
| [ ]  | P1       | `scope.deprecation.policy`  | Decide whether legacy `${...}` syntax is dropped immediately or retained behind a compatibility flag. |

### Scope P0 implementation tickets (execute in order)

| Done | Order | Ticket ID       | Deliverable                                                                                                                                     |
| ---- | ----- | --------------- | ----------------------------------------------------------------------------------------------------------------------------------------------- |
| [X]  | 1     | `TKT-SCOPE-001` | Create `docs/transition/V1_SCOPE.md` with v1 boundaries: supported `TemplateKind`, supported bookmark domains, and explicit non-goals.          |
| [X]  | 2     | `TKT-SCOPE-002` | Create `docs/transition/V1_OUTPUT_CONTRACT.md` defining output folder/file layout and naming rules for generated markdown.                      |
| [X]  | 3     | `TKT-SCOPE-003` | Create `docs/transition/V1_PARITY_CRITERIA.md` with pass/fail parity criteria for project/package/class/interface pages.                        |
| [X]  | 4     | `TKT-SCOPE-004` | Add fixture selection manifest `test-resources/mustache-transition/fixtures/fixtures-manifest.md` listing 3-5 projects and why each exists.     |
| [ ]  | 5     | `TKT-SCOPE-005` | Add committed baseline outputs under `test-resources/mustache-transition/golden/<fixture-name>/` from current engine for comparison.            |
| [ ]  | 6     | `TKT-SCOPE-006` | Add `docs/transition/V1_SIGNOFF_CHECKLIST.md` with checklist for scope freeze and owner signoff of scope, output contract, and parity criteria. |

#### Ticket acceptance checklist

| Done | Ticket ID       | Acceptance criteria                                                                                                            |
| ---- | --------------- | ------------------------------------------------------------------------------------------------------------------------------ |
| [ ]  | `TKT-SCOPE-001` | Document includes explicit in-scope and out-of-scope lists; each list has at least one item per template domain touched in v1. |
| [ ]  | `TKT-SCOPE-002` | Document specifies relative path rules and filename conventions for every v1 template kind.                                    |
| [ ]  | `TKT-SCOPE-003` | Criteria include deterministic checks for headings, section presence/absence, and representative list rendering.               |
| [ ]  | `TKT-SCOPE-004` | Manifest includes at least one project with modules, one without modules, and one with overloaded methods.                     |
| [ ]  | `TKT-SCOPE-005` | Golden files are committed, reproducible, and linked from the fixture manifest.                                                |
| [ ]  | `TKT-SCOPE-006` | Checklist has named approver fields and all prerequisite docs referenced by path.                                              |

## 1) architecture-foundation

| Done | Priority | Task ID                    | Description                                                                                               |
| ---- | -------- | -------------------------- | --------------------------------------------------------------------------------------------------------- |
| [ ]  | P0       | `arch.renderer.contract`   | Define new renderer contract for Mustache semantics (`var`, `section`, `inverted section`, dotted paths). |
| [ ]  | P0       | `arch.value.model`         | Introduce typed template value model (`String`, `Boolean`, `Number`, `Map`, `List`).                      |
| [ ]  | P0       | `arch.resolver.api`        | Replace flat string bookmark contract with typed map contract (`Map<String, Object>` or equivalent).      |
| [ ]  | P0       | `arch.context.stack.rules` | Specify context stack lookup rules for nested sections and list-item scopes.                              |
| [ ]  | P1       | `arch.escape.policy`       | Define markdown-safe escaping policy and raw-value behavior for trusted content.                          |

## 2) renderer-implementation

| Done | Priority | Task ID                         | Description                                                                                                  |
| ---- | -------- | ------------------------------- | ------------------------------------------------------------------------------------------------------------ |
| [ ]  | P0       | `renderer.tokenizer`            | Implement tokenizer/parser for Mustache tags (`{{name}}`, `{{#section}}`, `{{^section}}`, `{{/section}}`).   |
| [ ]  | P0       | `renderer.ast`                  | Implement minimal AST nodes for text, variable, section, and inverted section blocks.                        |
| [ ]  | P0       | `renderer.evaluator`            | Implement evaluator with dotted-path resolution and truthiness rules for lists/maps/scalars.                 |
| [ ]  | P0       | `renderer.errors`               | Add helpful template parse/runtime error messages (unclosed section, mismatched close tag, unknown root).    |
| [ ]  | P1       | `renderer.compatibility.mode`   | Optional: support legacy `${bookmark}` interpolation in compatibility mode during migration.                 |
| [ ]  | P2       | `renderer.performance.baseline` | Add a simple performance benchmark and ensure rendering stays within acceptable limits for fixture projects. |

## 3) data-and-resolver-migration

| Done | Priority | Task ID                       | Description                                                                                                        |
| ---- | -------- | ----------------------------- | ------------------------------------------------------------------------------------------------------------------ |
| [ ]  | P0       | `resolver.common.typed`       | Migrate common resolver outputs to typed values and nested maps.                                                   |
| [ ]  | P0       | `resolver.project.typed`      | Migrate project resolver to list/object outputs (`modules`, `packages`) rather than pre-rendered markdown strings. |
| [ ]  | P1       | `resolver.package.typed`      | Migrate package resolver outputs to support list rendering and future grouped views.                               |
| [ ]  | P1       | `resolver.class.typed`        | Implement class resolver output for conditional sections and member lists (methods/fields/properties).             |
| [ ]  | P1       | `resolver.interface.typed`    | Implement interface resolver output for method lists and kind-based grouping.                                      |
| [ ]  | P0       | `resolver.method.overloads`   | Introduce method-group model where `method.overloads` is a list of overload objects for loop rendering.            |
| [ ]  | P2       | `resolver.view-model.helpers` | Add reusable helper builders for table rows, grouped buckets, and summary projections.                             |

## 4) template-migration

| Done | Priority | Task ID                          | Description                                                                                                                |
| ---- | -------- | -------------------------------- | -------------------------------------------------------------------------------------------------------------------------- |
| [ ]  | P0       | `template.project.mustache`      | Rewrite project template with sections/inverted sections so empty module/package blocks disappear automatically.           |
| [ ]  | P1       | `template.package.mustache`      | Rewrite package template to use list/section rendering for contents and future alternate views.                            |
| [ ]  | P1       | `template.class.mustache`        | Rewrite class template with conditional subsections for constants, static methods, properties, methods, inherited members. |
| [ ]  | P1       | `template.interface.mustache`    | Rewrite interface template with conditional method sections and optional grouped/table views.                              |
| [ ]  | P0       | `template.method-group.mustache` | Rewrite method template to loop over `method.overloads` and render a repeated overload block.                              |
| [ ]  | P2       | `template.partial.blocks`        | Optional: add partial/block support to avoid repeating identical subsection markup.                                        |

## 5) test-and-verification

| Done | Priority | Task ID                       | Description                                                                                                  |
| ---- | -------- | ----------------------------- | ------------------------------------------------------------------------------------------------------------ |
| [ ]  | P0       | `test.renderer.unit`          | Add parser/evaluator unit tests for interpolation, sections, inverted sections, dotted lookups, and nesting. |
| [ ]  | P0       | `test.resolver.contract`      | Add resolver contract tests validating typed outputs for every migrated domain.                              |
| [ ]  | P0       | `test.golden.outputs`         | Add golden snapshot tests for fixture projects and compare generated markdown output trees.                  |
| [ ]  | P1       | `test.overload.grouping`      | Add focused tests proving overloads with same name are grouped and rendered consistently.                    |
| [ ]  | P1       | `test.empty-section.behavior` | Add tests confirming empty lists/false values remove headings and entire subsections.                        |
| [ ]  | P2       | `test.error-diagnostics`      | Add tests for malformed templates and quality of diagnostic messages.                                        |

## 6) rollout-and-cleanup

| Done | Priority | Task ID                      | Description                                                                                     |
| ---- | -------- | ---------------------------- | ----------------------------------------------------------------------------------------------- |
| [ ]  | P0       | `rollout.default-switch`     | Switch default renderer to Mustache engine once parity gates pass.                              |
| [ ]  | P1       | `rollout.remove-legacy-path` | Remove legacy renderer and old bookmark string workarounds after migration window.              |
| [ ]  | P1       | `rollout.docs.update`        | Update README and template author docs with Mustache syntax and migration examples.             |
| [ ]  | P2       | `rollout.todo-reconcile`     | Reconcile `BOOKMARKS_TODO.md` entries with new typed bookmark outputs and mark completed items. |
| [ ]  | P2       | `rollout.post-launch-audit`  | Run one post-launch audit for performance, output quality, and missing bookmark gaps.           |

## Suggested execution order (high level)

1. Complete all `scope-and-contract` tasks.
2. Complete all `architecture-foundation` tasks.
3. Complete `renderer-implementation` P0 tasks.
4. Complete `data-and-resolver-migration` P0 tasks.
5. Complete `template-migration` P0 tasks.
6. Complete all `test-and-verification` P0 tasks.
7. Perform `rollout.default-switch`.
8. Finish remaining P1 then P2 tasks.
