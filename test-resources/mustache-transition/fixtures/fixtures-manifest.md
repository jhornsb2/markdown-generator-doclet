# Mustache Transition Fixture Manifest

Fixture set used for v1 parity and golden-output validation.

## Selection Rules

- Include at least one fixture with Java modules.
- Include at least one fixture without modules.
- Include at least one fixture with overloaded methods.

## Fixtures

| ID       | Source Path                      | Purpose                                                   | Required Coverage                                      | Status     |
| -------- | -------------------------------- | --------------------------------------------------------- | ------------------------------------------------------ | ---------- |
| `FX-001` | `test-resources/TestJavaProject` | Baseline existing sample project.                         | Project/package/type rendering baseline.               | `selected` |
| `FX-002` | _TBD: no-module sample_          | Validate conditional omission of modules section.         | No modules; packages and types still render.           | `todo`     |
| `FX-003` | _TBD: overload-heavy sample_     | Validate method-group overload looping.                   | Multiple overloads by same method name.                | `todo`     |
| `FX-004` | _TBD: interface-focused sample_  | Validate interface conditional sections and method kinds. | Interface method rendering and empty section behavior. | `todo`     |

## Golden Output Location

- Root: `test-resources/mustache-transition/golden/`
- Per fixture: `test-resources/mustache-transition/golden/<fixture-id>/`

## Notes

- Keep fixture inputs small and deterministic.
- Avoid adding fixtures that duplicate existing coverage unless targeting a known regression.
- Update this manifest whenever a fixture is added, removed, or repurposed.
