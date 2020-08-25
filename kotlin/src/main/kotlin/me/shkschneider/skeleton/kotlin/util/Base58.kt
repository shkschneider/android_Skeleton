package me.shkschneider.skeleton.kotlin.util

private val alphabet = (('0'..'9') - '0' + ('A'..'Z') - 'I' - 'O' + ('a'..'z') - 'l').sorted()
private val base58 = alphabet.size.also {
    if (it != 58) throw RuntimeException("Base58!=Base$it")
}
private const val base256 = 256
private val indexes = IntArray(128).apply {
    fill(-1)
    for (i in alphabet.indices) {
        this[alphabet[i].toInt()] = i
    }
}

// https://gist.github.com/vrotaru/1753908
fun ByteArray.toBase58(): String {
    if (isEmpty()) return ""
    val input = copyOf()
    val zeros = input.takeWhile { it.toInt() == 0 }.count()

    val tmp = ByteArray(input.size * 2)
    var start = zeros
    var end = tmp.size
    while (start < input.size) {
        val mod = divMod58(input, start)
        if (input[start].toInt() == 0) {
            ++start
        }
        tmp[--end] = alphabet[mod.toInt()].toByte()
    }

    end += tmp.copyOfRange(end, tmp.size).takeWhile { it.toInt() == alphabet[0].toInt() }.count()
    tmp.fill(alphabet[0].toByte(), end - zeros, end)
    return String(tmp.copyOfRange(end, tmp.size))
}

// https://gist.github.com/vrotaru/1753908
fun String.fromBase58(): ByteArray {
    if (isEmpty()) return ByteArray(0)
    val input58 = ByteArray(length)
    for (i in indices) {
        input58[i] = indexes.getOrNull(this[i].toInt())?.toByte() ?: throw RuntimeException("Not a Base58 input: $this")
    }
    val zeros = input58.takeWhile { it.toInt() == 0 }.count()

    val tmp = ByteArray(length)
    var start = zeros
    var end = tmp.size
    while (start < input58.size) {
        val mod = divMod256(input58, start)
        if (input58[start].toInt() == 0) {
            ++start
        }
        tmp[--end] = mod
    }

    end += tmp.copyOfRange(end, tmp.size).takeWhile { it.toInt() == 0 }.count()
    return tmp.copyOfRange(end - zeros, tmp.size)
}

private fun divMod58(number: ByteArray, start: Int): Byte {
    var remainder = 0
    for (i in start until number.size) {
        val digit256 = number[i].toInt() and 0xFF
        val n = remainder * base256 + digit256
        number[i] = (n / base58).toByte()
        remainder = n % base58
    }
    return remainder.toByte()
}

private fun divMod256(number: ByteArray, start: Int): Byte {
    var remainder = 0
    for (i in start until number.size) {
        val digit58 = number[i].toInt() and 0xFF
        val temp = remainder * base58 + digit58
        number[i] = (temp / base256).toByte()
        remainder = temp % base256
    }
    return remainder.toByte()
}
