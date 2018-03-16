package me.shkschneider.skeleton.security

import me.shkschneider.skeleton.helper.LogHelper
import java.security.InvalidAlgorithmParameterException
import java.security.InvalidKeyException
import javax.crypto.BadPaddingException
import javax.crypto.Cipher
import javax.crypto.IllegalBlockSizeException
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec

class ComplexCrypt : ICrypt<ByteArray> {

    private val ALGORITHM = "AES" // AES/CTR/NoPadding" // CFB OFB CTR
    private val ALGORITHM_KEY_PAD = 16

    private var ivParameterSpec: IvParameterSpec
    private var secretKeySpec: SecretKeySpec
    private var cipher: Cipher

    constructor(key: ByteArray) : super(key) {
        ivParameterSpec = IvParameterSpec(key)
        secretKeySpec = SecretKeySpec(pad(key), ALGORITHM.split("/")[0])
        cipher = Cipher.getInstance(ALGORITHM)
    }

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

    // Format with String(decrypt) and not decrypt.toString()
    override fun decrypt(src: ByteArray): ByteArray? {
        try {
            cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, ivParameterSpec)
            return cipher.doFinal(src)
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
        val padded = bytes.size + (ALGORITHM_KEY_PAD - bytes.size % ALGORITHM_KEY_PAD)
        return bytes.copyOf(padded)
                .apply {
                    fill(0x00.toByte(), bytes.size, size)
                }
    }

}
