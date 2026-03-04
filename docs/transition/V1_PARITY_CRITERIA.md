# V1 Parity Criteria

Pass/fail criteria for switching default renderer to Mustache-based implementation.

## Global Criteria

- Generation completes without template parse/runtime errors on all v1 fixtures.
- Output is deterministic across repeated runs on the same input.
- No unresolved template tokens remain in generated markdown.

## Project Page Criteria

- `index.md` is generated.
- Project title is rendered.
- Project description is rendered.
- Modules section is absent when no modules exist.
- Modules section is present and non-empty when modules exist.
- Packages section is present and non-empty when packages exist.

## Package Page Criteria

- Every selected package has a `index.md` page.
- Package heading and description are rendered.
- Contents section renders list/group output according to template.
- Empty package contents do not produce broken placeholder text.

## Class/Interface Page Criteria

- Page exists for each selected class/interface in fixture manifest scope.
- Conditional sections are omitted when backing data is empty.
- Visible sections render non-empty content and no placeholder tokens.

## Method Group / Overload Criteria

- One method-group page per method name in selected scope.
- All overloads for the same method name appear on that page.
- Overload sub-sections use consistent repeated block formatting.
- Overload count matches extracted model data.

## Snapshot Criteria

- Golden snapshot comparison passes for approved fixture set.
- Differences are only accepted with explicit reviewer approval.

## Failure Conditions

- Any unresolved token in output (`${...}` or `{{...}}` literal remnants).
- Missing required files for selected fixture scope.
- Incorrect conditional behavior (empty sections rendered when they should be omitted).
- Overload grouping mismatch (missing or extra overload entries).
