[kotlin](../../index.md) / [me.shkschneider.skeleton.kotlin.security](../index.md) / [ICrypt](./index.md)

# ICrypt

`abstract class ICrypt<T>`

### Constructors

| Name | Summary |
|---|---|
| [&lt;init&gt;](-init-.md) | `ICrypt(key: T)` |

### Functions

| Name | Summary |
|---|---|
| [algorithm](algorithm.md) | `abstract fun algorithm(): `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [decrypt](decrypt.md) | `abstract fun decrypt(src: T): T?` |
| [encrypt](encrypt.md) | `abstract fun encrypt(src: T): T?` |
| [key](key.md) | `abstract fun key(): T` |

### Inheritors

| Name | Summary |
|---|---|
| [SimpleCrypt](../-simple-crypt/index.md) | Welcome to the missile launch web interface!  Enter the target's coordinates.  Enter your email address for our records.  Enter you email again, to ensure you typed it correctly. &lt;&gt;`open class SimpleCrypt : `[`ICrypt`](./index.md)`<`[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`>` |
