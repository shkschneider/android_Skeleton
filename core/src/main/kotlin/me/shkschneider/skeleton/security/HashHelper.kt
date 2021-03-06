package me.shkschneider.skeleton.security

import me.shkschneider.skeleton.helperx.Logger
import me.shkschneider.skeleton.java.StringHelper
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import kotlin.experimental.and

object HashHelper {

    private const val MD5 = "MD5"
    private const val SHA1 = "SHA-1"
    private const val SHA2 = "SHA-2"

    private fun hash(algorithm: String, string: String): String? {
        try {
            val messageDigest = MessageDigest.getInstance(algorithm)
            messageDigest.reset()
            messageDigest.update(string.toByteArray())
            val stringBuilder = StringBuilder()
            stringBuilder.setLength(0)
            val digest = messageDigest.digest()
            for (b in digest) {
                stringBuilder.append(Integer.toString((b and 0xFF.toByte()) + 0x100, StringHelper.HEX.length).substring(1))
            }
            return stringBuilder.toString()
        } catch (e: NoSuchAlgorithmException) {
            Logger.wtf(e)
            return null
        }
    }

    @Deprecated("Unsafe.")
    fun md5(string: String): String? {
        return hash(MD5, string)
    }

    @Deprecated("Unsafe.")
    fun sha1(string: String): String? {
        return hash(SHA1, string)
    }

    fun sha2(string: String): String? {
        return hash(SHA2, string)
    }

}
