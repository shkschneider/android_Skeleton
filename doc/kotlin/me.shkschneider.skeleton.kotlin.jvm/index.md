[kotlin](../index.md) / [me.shkschneider.skeleton.kotlin.jvm](./index.md)

## Package me.shkschneider.skeleton.kotlin.jvm

### Types

| Name | Summary |
|---|---|
| [ExceptionHelper](-exception-helper/index.md) | `object ExceptionHelper` |
| [Hidden](-hidden/index.md) | `class Hidden<T>` |
| [Once](-once/index.md) | `class Once<T>` |
| [Quad](-quad/index.md) | `data class Quad<out A, out B, out C, out D> : `[`Serializable`](https://docs.oracle.com/javase/6/docs/api/java/io/Serializable.html) |
| [Result](-result/index.md) | Substitute for kotlin.Result https://github.com/Kotlin/KEEP/blob/master/proposals/stdlib/result.md#limitations`class Result<out T> : `[`Serializable`](https://docs.oracle.com/javase/6/docs/api/java/io/Serializable.html) |

### Extensions for External Classes

| Name | Summary |
|---|---|
| [kotlin.Any](kotlin.-any/index.md) |  |
| [kotlin.ByteArray](kotlin.-byte-array/index.md) |  |
| [kotlin.Int](kotlin.-int/index.md) |  |
| [kotlin.Long](kotlin.-long/index.md) |  |
| [kotlin.reflect.KClass](kotlin.reflect.-k-class/index.md) |  |
| [kotlin.String](kotlin.-string/index.md) |  |

### Properties

| Name | Summary |
|---|---|
| [exhaustive](exhaustive.md) | `val <T> T.exhaustive: T` |

### Functions

| Name | Summary |
|---|---|
| [once](once.md) | `fun <T> once(): `[`Once`](-once/index.md)`<T>`<br>`fun <T> once(notifier: () -> `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)`): `[`Once`](-once/index.md)`<T>` |
| [toList](to-list.md) | `fun <T> `[`Quad`](-quad/index.md)`<T, T, T, T>.toList(): `[`List`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)`<T>` |
| [tryOr](try-or.md) | `fun <T : `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`> tryOr(or: T?, block: () -> T?, finally: (() -> `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)`)? = null): T?`<br>`fun <T : `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`> tryOr(or: T?, block: () -> T?): T?` |
| [tryOrNull](try-or-null.md) | `fun <T : `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`> tryOrNull(block: () -> T?, finally: (() -> `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)`)? = null): T?`<br>`fun <T : `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`> tryOrNull(block: () -> T?): T?` |
| [tryOrRun](try-or-run.md) | `fun <T : `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`> tryOrRun(block: () -> T?, or: (`[`Exception`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-exception/index.html)`) -> T?, finally: (() -> `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)`)? = null): T?`<br>`fun <T : `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`> tryOrRun(block: () -> T?, or: (`[`Exception`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-exception/index.html)`) -> T?): T?` |
| [trySilently](try-silently.md) | `fun <T : `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`> trySilently(block: () -> T?): T?` |
| [valueOf](value-of.md) | `fun <E : `[`Enum`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-enum/index.html)`<E>> valueOf(name: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`, default: E): E`<br>`fun <E : `[`Enum`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-enum/index.html)`<E>> valueOf(ordinal: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)`, default: E): E` |
| [valueOfIgnoreCase](value-of-ignore-case.md) | `fun <E : `[`Enum`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-enum/index.html)`<E>> valueOfIgnoreCase(name: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`): E?` |
| [valueOfWithCondition](value-of-with-condition.md) | `fun <E : `[`Enum`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-enum/index.html)`<E>> valueOfWithCondition(condition: (E) -> `[`Boolean`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)`): E?`<br>`fun <E : `[`Enum`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-enum/index.html)`<E>> valueOfWithCondition(condition: (E) -> `[`Boolean`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)`, default: E): E` |
