package me.shkschneider.skeleton.kotlin.datax

import me.shkschneider.skeleton.kotlin.log.Logger
import java.math.BigInteger
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException

object Hash {

    private const val MD5 = "MD5"
    private const val SHA1 = "SHA-1"
    private const val SHA256 = "SHA-256"
    private const val SHA512 = "SHA-512"

    private fun hash(algorithm: String, string: String): String? =
        try {
            val messageDigest = MessageDigest.getInstance(algorithm).digest(string.toByteArray())
            BigInteger(1, messageDigest).toString(16).padStart(32, '0')
        } catch (e: NoSuchAlgorithmException) {
            Logger.wtf(e)
            null
        }

    @Deprecated("Unsafe.")
    fun md5(string: String): String? =
        hash(MD5, string)

    @Deprecated("Unsafe.")
    fun sha1(string: String): String? =
        hash(SHA1, string)

    fun sha256(string: String): String? =
        hash(SHA256, string)

    fun sha512(string: String): String? =
        hash(SHA512, string)

}
