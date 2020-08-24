package me.shkschneider.skeleton.android.security

import me.shkschneider.skeleton.android.log.Logger
import java.math.BigInteger
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException

object HashHelper {

    private const val MD5 = "MD5"
    private const val SHA1 = "SHA-1"
    private const val SHA256 = "SHA-256"
    private const val SHA512 = "SHA-512"

    private fun hash(algorithm: String, string: String): String? {
        try {
            with(MessageDigest.getInstance(algorithm).digest(string.toByteArray())) {
                return BigInteger(1, this)
                        .toString(16).padStart(32, '0')
            }
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

    fun sha256(string: String): String? {
        return hash(SHA256, string)
    }

    fun sha512(string: String): String? {
        return hash(SHA512, string)
    }

}
