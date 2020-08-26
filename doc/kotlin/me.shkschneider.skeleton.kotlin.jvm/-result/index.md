[kotlin](../../index.md) / [me.shkschneider.skeleton.kotlin.jvm](../index.md) / [Result](./index.md)

# Result

`class Result<out T> : `[`Serializable`](https://docs.oracle.com/javase/6/docs/api/java/io/Serializable.html)

Substitute for kotlin.Result
https://github.com/Kotlin/KEEP/blob/master/proposals/stdlib/result.md#limitations

### Properties

| Name | Summary |
|---|---|
| [isFailure](is-failure.md) | `val isFailure: `[`Boolean`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
| [isSuccess](is-success.md) | `val isSuccess: `[`Boolean`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |

### Functions

| Name | Summary |
|---|---|
| [exceptionOrNull](exception-or-null.md) | `fun exceptionOrNull(): `[`Throwable`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-throwable/index.html)`?` |
| [fold](fold.md) | `fun fold(onSuccess: (value: T) -> `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)`, onFailure: (exception: `[`Throwable`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-throwable/index.html)`) -> `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)`): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) |
| [getOrNull](get-or-null.md) | `fun getOrNull(): T?` |
| [toString](to-string.md) | `fun toString(): `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |

### Companion Object Functions

| Name | Summary |
|---|---|
| [failure](failure.md) | `fun <T> failure(exception: `[`Throwable`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-throwable/index.html)`): `[`Result`](./index.md)`<T>` |
| [success](success.md) | `fun <T> success(value: T): `[`Result`](./index.md)`<T>` |
