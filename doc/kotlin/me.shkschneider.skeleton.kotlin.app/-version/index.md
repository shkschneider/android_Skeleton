[kotlin](../../index.md) / [me.shkschneider.skeleton.kotlin.app](../index.md) / [Version](./index.md)

# Version

`open class Version : `[`Comparable`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-comparable/index.html)`<`[`Version`](./index.md)`>`

### Types

| Name | Summary |
|---|---|
| [Comparator](-comparator/index.md) | `class Comparator : `[`Comparator`](https://docs.oracle.com/javase/6/docs/api/java/util/Comparator.html)`<`[`Version`](./index.md)`>` |

### Constructors

| Name | Summary |
|---|---|
| [&lt;init&gt;](-init-.md) | `Version(version: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`)` |

### Properties

| Name | Summary |
|---|---|
| [isSemVer](is-sem-ver.md) | `val isSemVer: `[`Boolean`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |

### Functions

| Name | Summary |
|---|---|
| [compareTo](compare-to.md) | `open fun compareTo(other: `[`Version`](./index.md)`): `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |
| [toString](to-string.md) | `open fun toString(): `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |

### Extension Functions

| Name | Summary |
|---|---|
| [toSemVer](../to-sem-ver.md) | `fun `[`Version`](./index.md)`.toSemVer(): `[`SemanticVersion`](../-semantic-version/index.md) |
