[kotlin](../../index.md) / [me.shkschneider.skeleton.kotlin.work](../index.md) / [Tasker](./index.md)

# Tasker

`open class Tasker`

### Types

| Name | Summary |
|---|---|
| [Task](-task/index.md) | `class Task : `[`Runnable`](https://docs.oracle.com/javase/6/docs/api/java/lang/Runnable.html) |

### Constructors

| Name | Summary |
|---|---|
| [&lt;init&gt;](-init-.md) | `Tasker(executorService: `[`ExecutorService`](https://docs.oracle.com/javase/6/docs/api/java/util/concurrent/ExecutorService.html)` = Executors.newSingleThreadExecutor())` |

### Properties

| Name | Summary |
|---|---|
| [cancelled](cancelled.md) | `val cancelled: `[`Boolean`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)`?` |
| [done](done.md) | `val done: `[`Boolean`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)`?` |

### Functions

| Name | Summary |
|---|---|
| [cancel](cancel.md) | `fun cancel(mayInterruptIfRunning: `[`Boolean`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)` = true): `[`Boolean`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)`?` |
| [run](run.md) | `fun run(task: Task): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) |
