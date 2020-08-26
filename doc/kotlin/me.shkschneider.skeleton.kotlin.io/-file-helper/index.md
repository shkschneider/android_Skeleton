[kotlin](../../index.md) / [me.shkschneider.skeleton.kotlin.io](../index.md) / [FileHelper](./index.md)

# FileHelper

`object FileHelper`

### Properties

| Name | Summary |
|---|---|
| [BUFFER_SIZE](-b-u-f-f-e-r_-s-i-z-e.md) | `const val BUFFER_SIZE: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |

### Functions

| Name | Summary |
|---|---|
| [get](get.md) | `fun get(path: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`): `[`File`](https://docs.oracle.com/javase/6/docs/api/java/io/File.html) |
| [join](join.md) | `fun join(dirname: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`, basename: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`): `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [openFile](open-file.md) | `fun openFile(file: `[`File`](https://docs.oracle.com/javase/6/docs/api/java/io/File.html)`): `[`InputStream`](https://docs.oracle.com/javase/6/docs/api/java/io/InputStream.html)`?` |
| [readString](read-string.md) | `fun readString(file: `[`File`](https://docs.oracle.com/javase/6/docs/api/java/io/File.html)`): `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`?` |
| [writeString](write-string.md) | `fun writeString(file: `[`File`](https://docs.oracle.com/javase/6/docs/api/java/io/File.html)`, content: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`): `[`Boolean`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
