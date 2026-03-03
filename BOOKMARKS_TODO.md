# Bookmarks Support TODO

Task tree of bookmark support by domain with priority and intent.

Priority legend:

- `P0` = MVP / required for initial template usefulness
- `P1` = high value enhancement
- `P2` = nice-to-have / advanced view

## common

| Done | Priority | Bookmark                   | Description                                                                          |
| ---- | -------- | -------------------------- | ------------------------------------------------------------------------------------ |
| [x]  | P0       | `common.simpleName`        | Request the short display name of the current element.                               |
| [x]  | P0       | `common.qualifiedName`     | Request the fully qualified name of the current element.                             |
| [x]  | P0       | `common.kind`              | Request the base kind (`class`, `interface`, `enum`, etc.).                          |
| [x]  | P0       | `common.kindWithModifiers` | Request kind prefixed by important modifiers (`abstract class`, `sealed interface`). |
| [x]  | P0       | `common.description`       | Request normalized element description text (canonical alias for doc comment body).  |
| [x]  | P0       | `common.docComment`        | Request normalized top-level doc comment text for the element.                       |
| [x]  | P1       | `common.modifiers`         | Request canonical modifier list for the element declaration.                         |
| [x]  | P1       | `common.visibility`        | Request effective visibility (`public`, `protected`, `package`, `private`).          |
| [ ]  | P1       | `common.annotations`       | Request annotations applied to the element.                                          |
| [ ]  | P2       | `common.isDeprecated`      | Request whether the element is deprecated.                                           |
| [ ]  | P2       | `common.since`             | Request `@since` tag value when available.                                           |

## project

| Done | Priority | Bookmark                   | Description                                          |
| ---- | -------- | -------------------------- | ---------------------------------------------------- |
| [x]  | P0       | `project.name`             | Request project display name.                        |
| [x]  | P0       | `project.description`      | Request project-level summary/description text.      |
| [x]  | P0       | `project.moduleCount`      | Request total module count in project scope.         |
| [x]  | P0       | `project.packageCount`     | Request total package count in project scope.        |
| [ ]  | P1       | `project.typeCount`        | Request total number of documentable types.          |
| [x]  | P0       | `project.modules`          | Request module list in default view.                 |
| [ ]  | P2       | `project.modules.table`    | Request module list rendered as a table.             |
| [x]  | P0       | `project.packages`         | Request package list in default view.                |
| [ ]  | P1       | `project.packages.tree`    | Request package list rendered as hierarchy/tree.     |
| [ ]  | P1       | `project.packages.summary` | Request compact package summary with minimal detail. |

## module

| Done | Priority | Bookmark                               | Description                                                        |
| ---- | -------- | -------------------------------------- | ------------------------------------------------------------------ |
| [ ]  | P1       | `module.isOpen`                        | Request whether module is declared as `open`.                      |
| [ ]  | P1       | `module.requires`                      | Request required module dependencies.                              |
| [ ]  | P1       | `module.exports`                       | Request exported packages and targets.                             |
| [ ]  | P2       | `module.opens`                         | Request opened packages and targets.                               |
| [ ]  | P2       | `module.uses`                          | Request service types consumed via `uses`.                         |
| [ ]  | P2       | `module.provides`                      | Request service provider mappings from `provides ... with ...`.    |
| [ ]  | P1       | `module.packages`                      | Request module package list in default view.                       |
| [ ]  | P2       | `module.packages.tree`                 | Request module package list in hierarchical view.                  |
| [ ]  | P2       | `module.packages.grouped.byVisibility` | Request module packages grouped by export visibility/access scope. |

## package

| Done | Priority | Bookmark                          | Description                                          |
| ---- | -------- | --------------------------------- | ---------------------------------------------------- |
| [x]  | P0       | `package.contents`                | Request all package members in default list format.  |
| [ ]  | P1       | `package.contents.flat`           | Request package contents as explicit flat list view. |
| [ ]  | P1       | `package.contents.tree`           | Request package contents in hierarchical/tree view.  |
| [ ]  | P2       | `package.contents.grouped.byKind` | Request package contents grouped by type kind.       |
| [ ]  | P1       | `package.contents.class`          | Request package classes only.                        |
| [ ]  | P1       | `package.contents.interface`      | Request package interfaces only.                     |
| [ ]  | P1       | `package.contents.record`         | Request package records only.                        |
| [ ]  | P1       | `package.contents.enum`           | Request package enums only.                          |
| [ ]  | P2       | `package.contents.annotation`     | Request package annotation types only.               |

## class

| Done | Priority | Bookmark                             | Description                                              |
| ---- | -------- | ------------------------------------ | -------------------------------------------------------- |
| [ ]  | P0       | `class.signature`                    | Request declaration signature including type parameters. |
| [ ]  | P0       | `class.extends`                      | Request direct superclass information.                   |
| [ ]  | P0       | `class.implements`                   | Request implemented interface list.                      |
| [ ]  | P0       | `class.fields`                       | Request all class fields in default view.                |
| [ ]  | P1       | `class.fields.public`                | Request public fields only.                              |
| [ ]  | P1       | `class.fields.private`               | Request private fields only.                             |
| [ ]  | P1       | `class.fields.static`                | Request static fields only.                              |
| [ ]  | P0       | `class.constructors`                 | Request constructors for the class.                      |
| [ ]  | P0       | `class.methods`                      | Request all class methods in default view.               |
| [ ]  | P1       | `class.methods.public`               | Request public methods only.                             |
| [ ]  | P1       | `class.methods.protected`            | Request protected methods only.                          |
| [ ]  | P1       | `class.methods.private`              | Request private methods only.                            |
| [ ]  | P1       | `class.methods.static`               | Request static methods only.                             |
| [ ]  | P1       | `class.methods.instance`             | Request instance (non-static) methods only.              |
| [ ]  | P2       | `class.methods.abstract`             | Request abstract methods only.                           |
| [ ]  | P2       | `class.members.grouped.byVisibility` | Request combined members grouped by visibility.          |
| [ ]  | P2       | `class.methods.table`                | Request methods rendered in table form.                  |
| [ ]  | P2       | `class.nestedTypes`                  | Request nested/inner types declared by the class.        |

## interface

| Done | Priority | Bookmark                           | Description                                               |
| ---- | -------- | ---------------------------------- | --------------------------------------------------------- |
| [ ]  | P0       | `interface.signature`              | Request interface declaration signature.                  |
| [ ]  | P0       | `interface.extends`                | Request superinterface list.                              |
| [ ]  | P1       | `interface.constants`              | Request interface constants (public static final fields). |
| [ ]  | P0       | `interface.methods`                | Request all interface methods in default view.            |
| [ ]  | P1       | `interface.methods.abstract`       | Request abstract interface methods only.                  |
| [ ]  | P1       | `interface.methods.default`        | Request default interface methods only.                   |
| [ ]  | P1       | `interface.methods.static`         | Request static interface methods only.                    |
| [ ]  | P2       | `interface.methods.grouped.byKind` | Request methods grouped by abstract/default/static kind.  |
| [ ]  | P2       | `interface.methods.table`          | Request methods rendered as table.                        |
| [ ]  | P2       | `interface.nestedTypes`            | Request nested types declared inside interface.           |

## record

| Done | Priority | Bookmark                              | Description                                                      |
| ---- | -------- | ------------------------------------- | ---------------------------------------------------------------- |
| [ ]  | P0       | `record.signature`                    | Request record declaration signature.                            |
| [ ]  | P0       | `record.components`                   | Request record components in declaration order.                  |
| [ ]  | P1       | `record.constructors`                 | Request declared constructors and canonical constructor details. |
| [ ]  | P1       | `record.methods`                      | Request record methods in default view.                          |
| [ ]  | P1       | `record.methods.static`               | Request static record methods only.                              |
| [ ]  | P1       | `record.methods.instance`             | Request instance record methods only.                            |
| [ ]  | P2       | `record.components.table`             | Request components rendered as a table.                          |
| [ ]  | P2       | `record.members.grouped.byVisibility` | Request record members grouped by visibility.                    |

## enum

| Done | Priority | Bookmark                            | Description                                                  |
| ---- | -------- | ----------------------------------- | ------------------------------------------------------------ |
| [ ]  | P0       | `enum.signature`                    | Request enum declaration signature.                          |
| [ ]  | P0       | `enum.constants`                    | Request enum constants in declaration order.                 |
| [ ]  | P1       | `enum.fields`                       | Request enum fields (excluding constants unless configured). |
| [ ]  | P1       | `enum.methods`                      | Request enum methods in default view.                        |
| [ ]  | P1       | `enum.methods.static`               | Request static enum methods only.                            |
| [ ]  | P1       | `enum.methods.instance`             | Request instance enum methods only.                          |
| [ ]  | P2       | `enum.constants.table`              | Request enum constants as table output.                      |
| [ ]  | P2       | `enum.members.grouped.byVisibility` | Request enum members grouped by visibility.                  |

## method

| Done | Priority | Bookmark                  | Description                                                |
| ---- | -------- | ------------------------- | ---------------------------------------------------------- |
| [ ]  | P0       | `method.signature`        | Request full method signature (name, params, return type). |
| [ ]  | P0       | `method.returnType`       | Request method return type.                                |
| [ ]  | P0       | `method.parameters`       | Request ordered method parameter list.                     |
| [ ]  | P2       | `method.parameters.table` | Request parameters rendered in table format.               |
| [ ]  | P1       | `method.throws`           | Request declared thrown exception types.                   |
| [ ]  | P1       | `method.modifiers`        | Request method modifiers as canonical string/list.         |
| [ ]  | P1       | `method.visibility`       | Request effective method visibility.                       |
| [ ]  | P1       | `method.isStatic`         | Request whether method is static.                          |
| [ ]  | P1       | `method.isAbstract`       | Request whether method is abstract.                        |
| [ ]  | P1       | `method.isDefault`        | Request whether method is a default interface method.      |
| [ ]  | P2       | `method.isSynchronized`   | Request whether method is synchronized.                    |
| [ ]  | P2       | `method.isVarArgs`        | Request whether method has varargs parameter.              |
| [ ]  | P0       | `method.docComment`       | Request method-level documentation summary/body.           |
| [ ]  | P1       | `method.typeParameters`   | Request method type parameter declarations/bounds.         |
| [ ]  | P1       | `method.annotations`      | Request annotations declared on the method.                |
