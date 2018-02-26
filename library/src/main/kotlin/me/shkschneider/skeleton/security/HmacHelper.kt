package me.shkschneider.skeleton.security

import java.security.InvalidKeyException
import java.security.NoSuchAlgorithmException

import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec

import me.shkschneider.skeleton.helper.LogHelper
import me.shkschneider.skeleton.java.StringHelper

object HmacHelper {

    private const val ALGORITHM = "HmacSHA1"

    fun hash(key: String, string: String): String? {
        try {
            val secretKeySpec = SecretKeySpec(key.toByteArray(), ALGORITHM)
            val mac = Mac.getInstance(ALGORITHM)
            mac.init(secretKeySpec)
            val digest = mac.doFinal(string.toByteArray()) ?: return null
            return StringHelper.hexadecimal(digest)
        } catch (e: NoSuchAlgorithmException) {
            LogHelper.wtf(e)
            return null
        } catch (e: InvalidKeyException) {
            LogHelper.wtf(e)
            return null
        }
    }

}
