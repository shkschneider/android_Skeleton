package me.shkschneider.skeleton.security

import java.security.InvalidAlgorithmParameterException
import java.security.InvalidKeyException
import java.util.Arrays

import javax.crypto.BadPaddingException
import javax.crypto.Cipher
import javax.crypto.IllegalBlockSizeException
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec

import me.shkschneider.skeleton.helper.LogHelper
import me.shkschneider.skeleton.java.RandomHelper

class ComplexCrypt {

    private val ALGORITHM = "AES/CTR/NoPadding" // CFB OFB CTR
    private val ALGORITHM_KEY_LENGTH = 128
    private val ALGORITHM_KEY_PAD = 16

    private var _secret: ByteArray
    private var _ivParameterSpec: IvParameterSpec
    private var _secretKeySpec: SecretKeySpec
    private var _secretKey: SecretKey
    private var _cipher: Cipher

    constructor(secret: ByteArray) {
        _secret = secret
        // salt
        _secret = String(secret).toByteArray()
        // initialization vector
        _ivParameterSpec = IvParameterSpec(RandomHelper.string(ALGORITHM_KEY_PAD).toByteArray())
        // key
        val keyGenerator = KeyGenerator.getInstance(ALGORITHM.split("/".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()[0])
        keyGenerator.init(ALGORITHM_KEY_LENGTH)
        _secretKey = keyGenerator.generateKey()
        _secretKeySpec = SecretKeySpec(_secretKey.encoded, ALGORITHM.split("/".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()[0])
        // cipher
        _cipher = Cipher.getInstance(ALGORITHM)
    }

    fun algorithm(): String {
        return _secretKey.algorithm
    }

    fun key(): String {
        return Base64Helper.encode(_secretKey.encoded)
    }

    fun secret(): ByteArray {
        return _secret
    }

    fun encrypt(bytes: ByteArray): ByteArray? {
        try {
            _cipher.init(Cipher.ENCRYPT_MODE, _secretKeySpec, _ivParameterSpec)
            return _cipher.doFinal(pad(bytes))
        } catch (e: InvalidKeyException) {
            LogHelper.wtf(e)
            return null
        } catch (e: InvalidAlgorithmParameterException) {
            LogHelper.wtf(e)
            return null
        } catch (e: IllegalBlockSizeException) {
            LogHelper.wtf(e)
            return null
        } catch (e: BadPaddingException) {
            LogHelper.wtf(e)
            return null
        }
    }

    fun decrypt(bytes: ByteArray): ByteArray? {
        try {
            _cipher.init(Cipher.DECRYPT_MODE, _secretKeySpec, _ivParameterSpec)
            var decrypted = _cipher.doFinal(pad(bytes))
            if (decrypted.isNotEmpty()) {
                val trim = decrypted.indices.reversed().count { decrypted[it].toInt() == 0 }
                if (trim > 0) {
                    val newArray = ByteArray(decrypted.size - trim)
                    System.arraycopy(decrypted, 0, newArray, 0, decrypted.size - trim)
                    decrypted = newArray
                }
            }
            return decrypted
        } catch (e: InvalidKeyException) {
            LogHelper.wtf(e)
            return null
        } catch (e: InvalidAlgorithmParameterException) {
            LogHelper.wtf(e)
            return null
        } catch (e: IllegalBlockSizeException) {
            LogHelper.wtf(e)
            return null
        } catch (e: BadPaddingException) {
            LogHelper.wtf(e)
            return null
        }
    }

    private fun pad(bytes: ByteArray): ByteArray {
        val extras = ALGORITHM_KEY_PAD - bytes.size % ALGORITHM_KEY_PAD
        if (extras == 0 || extras == ALGORITHM_KEY_PAD) {
            return bytes
        }
        val padded = ByteArray(bytes.size + extras)
        Arrays.fill(padded, 0x00.toByte())
        System.arraycopy(bytes, 0, padded, 0, bytes.size)
        return padded
    }

}
