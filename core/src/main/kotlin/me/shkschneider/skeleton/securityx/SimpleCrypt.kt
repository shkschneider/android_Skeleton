package me.shkschneider.skeleton.securityx

import me.shkschneider.skeleton.kotlinx.fromBase64
import me.shkschneider.skeleton.kotlinx.toBase64

private const val ALGORITHM = "XOR"

/**
 * > Welcome to the missile launch web interface!
 * > Enter the target's coordinates.
 * > Enter your email address for our records.
 * > Enter you email again, to ensure you typed it correctly.
 * <https://xkcd.com/970/>
 */
open class SimpleCrypt(key: String) : ICrypt<String>(key) {

    private val key: CharArray = key.toCharArray()

    override fun algorithm(): String {
        return ALGORITHM
    }

    override fun key(): String {
        return String(key)
    }

    override fun encrypt(src: String): String? {
        val chars = src.toCharArray()
        val encrypted = CharArray(chars.size)
        for (i in chars.indices) {
            encrypted[i] = (chars[i].toInt() xor key[i % key.size].toInt()).toChar()
        }
        return String(encrypted).toBase64()
    }

    override fun decrypt(src: String): String? {
        return src.fromBase64()?.let { encrypted ->
            val decrypted = CharArray(encrypted.length)
            for (i in encrypted.indices) {
                decrypted[i] = (encrypted[i].toInt() xor key[i % key.size].toInt()).toChar()
            }
            return String(decrypted)
        }
    }

}
