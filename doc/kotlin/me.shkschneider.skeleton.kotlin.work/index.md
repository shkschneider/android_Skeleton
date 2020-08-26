[kotlin](../index.md) / [me.shkschneider.skeleton.kotlin.work](./index.md)

## Package me.shkschneider.skeleton.kotlin.work

### Types

| Name | Summary |
|---|---|
| [Coroutines](-coroutines/index.md) | +------------+----------+-------------+-------------+ | state      | isActive | isCompleted | isCancelled | +------------+----------+-------------+-------------+ | new        | false    | false       | false       | | active     | true     | false       | false       | | completing | true     | false       | false       | | cancelling | false    | false       | true        | | cancelled  | false    | true        | true        | | completed  | false    | true        | false       | +------------+----------+-------------+-------------+`object Coroutines` |
| [Tasker](-tasker/index.md) | `open class Tasker` |

### Extensions for External Classes

| Name | Summary |
|---|---|
| [kotlinx.coroutines.CoroutineScope](kotlinx.coroutines.-coroutine-scope/index.md) |  |

### Functions

| Name | Summary |
|---|---|
| [io](io.md) | `fun <T : `[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)`> io(block: suspend () -> T): Job` |
