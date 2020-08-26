[kotlin](../../index.md) / [me.shkschneider.skeleton.kotlin.work](../index.md) / [Coroutines](./index.md)

# Coroutines

`object Coroutines`

+------------+----------+-------------+-------------+
| state      | isActive | isCompleted | isCancelled |
+------------+----------+-------------+-------------+
| new        | false    | false       | false       |
| active     | true     | false       | false       |
| completing | true     | false       | false       |
| cancelling | false    | false       | true        |
| cancelled  | false    | true        | true        |
| completed  | false    | true        | false       |
+------------+----------+-------------+-------------+

### Functions

| Name | Summary |
|---|---|
| [io](io.md) | `fun io(work: suspend () -> `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)`): Job` |
| [ioThenMain](io-then-main.md) | `fun <T : `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`> ioThenMain(work: suspend () -> T?, callback: (T?) -> `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)`): Job` |
