package me.shkschneider.skeleton.android.security

import android.util.Base64
import me.shkschneider.skeleton.android.log.Logger
import me.shkschneider.skeleton.android.core.kotlinx.fromBase64
import me.shkschneider.skeleton.android.core.kotlinx.toBase64
import me.shkschneider.skeleton.kotlin.crypt.ICrypt
import me.shkschneider.skeleton.kotlin.data.Charsets
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

    override fun algorithm(): String =
        secretKeySpec.algorithm

    override fun key(): String =
        Base64.encodeToString(secretKeySpec.encoded, Base64.NO_WRAP)

    override fun encrypt(src: String): String? =
        try {
            cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, ivParameterSpec)
            val bytes = cipher.doFinal(src.toByteArray())
            String(bytes).toBase64()
        } catch (e: IllegalStateException) {
            Logger.wtf(e)
            null
        } catch (e: InvalidKeyException) {
            Logger.wtf(e)
            null
        } catch (e: InvalidAlgorithmParameterException) {
            Logger.wtf(e)
            null
        } catch (e: IllegalBlockSizeException) {
            Logger.wtf(e)
            null
        } catch (e: BadPaddingException) {
            Logger.wtf(e)
            null
        }

    override fun decrypt(src: String): String? =
        try {
            cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, ivParameterSpec)
            val bytes = cipher.doFinal(src.fromBase64()?.toByteArray())
            String(bytes, Charset.forName(Charsets.UTF8))
        } catch (e: InvalidKeyException) {
            Logger.wtf(e)
            null
        } catch (e: InvalidAlgorithmParameterException) {
            Logger.wtf(e)
            null
        } catch (e: IllegalBlockSizeException) {
            Logger.wtf(e)
            null
        } catch (e: BadPaddingException) {
            Logger.wtf(e)
            null
        }

    private fun pad(bytes: ByteArray): ByteArray =
        bytes.copyOf(bytes.size + (ALGORITHM_KEY_PAD - bytes.size % ALGORITHM_KEY_PAD))

}
