package me.shkschneider.skeleton.crypt

import me.shkschneider.skeleton.data.Charsets
import me.shkschneider.skeleton.datax.fromBase64
import me.shkschneider.skeleton.log.Logger
import me.shkschneider.skeleton.datax.toBase64
import java.nio.charset.Charset
import java.security.InvalidAlgorithmParameterException
import java.security.InvalidKeyException
import javax.crypto.BadPaddingException
import javax.crypto.Cipher
import javax.crypto.IllegalBlockSizeException
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec

/**
 * Stronger encryption requires higher API levels.
 * See https://developer.android.com/reference/javax/crypto/Cipher
 */
private const val ALGORITHM = "AES/GCM/NoPadding" // KeyProperties.KEY_ALGORITHM_AES
private const val ALGORITHM_KEY_PAD = 16

/**
 * "Tr0ub4dor&3" ~28 bits of entropy
 * "correct horse battery staple" ~44 bits of entropy
 * <https://xkcd.com/936/>
 */
open class ComplexCrypt(key: String) : ICrypt<String>(key) {

    private var ivParameterSpec = IvParameterSpec(key.toByteArray())
    private var secretKeySpec = SecretKeySpec(pad(key.toByteArray()), ALGORITHM.split("/")[0])
    private var cipher = Cipher.getInstance(ALGORITHM)

    override fun algorithm(): String {
        return secretKeySpec.algorithm
    }

    override fun key(): String {
        return secretKeySpec.encoded.toBase64()
    }

    override fun encrypt(src: String): String? {
        try {
            cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, ivParameterSpec)
            val bytes = cipher.doFinal(src.toByteArray())
            return String(bytes) // TODO test
        } catch (e: IllegalStateException) {
            Logger.wtf(e)
            return null
        } catch (e: InvalidKeyException) {
            Logger.wtf(e)
            return null
        } catch (e: InvalidAlgorithmParameterException) {
            Logger.wtf(e)
            return null
        } catch (e: IllegalBlockSizeException) {
            Logger.wtf(e)
            return null
        } catch (e: BadPaddingException) {
            Logger.wtf(e)
            return null
        }
    }

    override fun decrypt(src: String): String? {
        try {
            cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, ivParameterSpec)
            val bytes = cipher.doFinal(src.fromBase64())
            return String(bytes, Charset.forName(Charsets.UTF8))
        } catch (e: InvalidKeyException) {
            Logger.wtf(e)
            return null
        } catch (e: InvalidAlgorithmParameterException) {
            Logger.wtf(e)
            return null
        } catch (e: IllegalBlockSizeException) {
            Logger.wtf(e)
            return null
        } catch (e: BadPaddingException) {
            Logger.wtf(e)
            return null
        }
    }

    private fun pad(bytes: ByteArray): ByteArray {
        val padded = bytes.size + (ALGORITHM_KEY_PAD - bytes.size % ALGORITHM_KEY_PAD)
        return bytes.copyOf(padded)
    }

}
