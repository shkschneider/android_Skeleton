package me.shkschneider.skeleton.security

import android.annotation.SuppressLint
import me.shkschneider.skeleton.helper.Logger
import java.security.InvalidAlgorithmParameterException
import java.security.InvalidKeyException
import javax.crypto.BadPaddingException
import javax.crypto.Cipher
import javax.crypto.IllegalBlockSizeException
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec

class ComplexCrypt(key: ByteArray) : ICrypt<ByteArray>(key) {

    private val ALGORITHM = "AES" // KeyProperties.KEY_ALGORITHM_AES
    private val ALGORITHM_KEY_PAD = 16

    private var ivParameterSpec = IvParameterSpec(key)
    private var secretKeySpec = SecretKeySpec(pad(key), ALGORITHM.split("/")[0])
    @SuppressLint("GetInstance")
    private var cipher = Cipher.getInstance(ALGORITHM)

    override fun algorithm(): String {
        return secretKeySpec.algorithm
    }

    override fun key(): ByteArray {
        return secretKeySpec.encoded
    }

    override fun encrypt(src: ByteArray): ByteArray? {
        try {
            cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, ivParameterSpec)
            return cipher.doFinal(src)
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

    // Format with String(decrypt) and not decrypt.toString()
    override fun decrypt(src: ByteArray): ByteArray? {
        try {
            cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, ivParameterSpec)
            return cipher.doFinal(src)
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
                .apply {
                    fill(0x00.toByte(), bytes.size, size)
                }
    }

}
