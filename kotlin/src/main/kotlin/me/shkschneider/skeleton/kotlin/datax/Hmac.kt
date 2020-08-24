package me.shkschneider.skeleton.kotlin.datax

import me.shkschneider.skeleton.kotlin.toHexadecimal
import java.security.InvalidKeyException
import java.security.NoSuchAlgorithmException
import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec

object Hmac {

    const val ALGORITHM = "HmacSHA1"

    @Throws(NoSuchAlgorithmException::class, InvalidKeyException::class)
    fun sign(key: ByteArray, data: ByteArray): String {
        val secretKeySpec = SecretKeySpec(key, ALGORITHM)
        val mac = Mac.getInstance(ALGORITHM)
        mac.init(secretKeySpec)
        return requireNotNull(mac.doFinal(data)).toHexadecimal()
    }

    fun verify(key: ByteArray, data: ByteArray, expected: ByteArray): Boolean {
        sign(key, data).also {
            if (it.length != expected.size) return false
        }.forEachIndexed { i, b ->
            if (b.toInt().xor(expected[i].toInt()) != 0) return false
        }
        return true
    }

}
