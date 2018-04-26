package me.shkschneider.skeleton.security

import android.Manifest
import android.hardware.fingerprint.FingerprintManager
import android.os.Build
import android.os.CancellationSignal
import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import android.support.annotation.RequiresApi
import android.support.annotation.RequiresPermission
import me.shkschneider.skeleton.helper.AndroidHelper
import me.shkschneider.skeleton.helper.SystemServices
import java.security.KeyStore
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey

object FingerprintHelper {

    private const val KEYSTORE = "AndroidKeyStore"
    private const val KEY = "FingerPrint"

    fun available(): Boolean {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return false
        }
        // if (ActivityCompat.checkSelfPermission(ContextHelper.applicationContext(), Manifest.permission.USE_FINGERPRINT) != PackageManager.PERMISSION_GRANTED) return false
        val keyguardManager = SystemServices.keyguardManager()
        keyguardManager ?: return false
        if (!keyguardManager.isKeyguardSecure) return false
        val fingerprintManager = SystemServices.fingerprintManager()
        fingerprintManager ?: return false
        with (fingerprintManager) {
            if (!isHardwareDetected) return false
            if (!hasEnrolledFingerprints()) return false
        }
        return true
    }

    @RequiresApi(AndroidHelper.API_23)
    private fun signature(): FingerprintManager.CryptoObject {
        val keyStore = KeyStore.getInstance(KEYSTORE)
        val keyGenerator = KeyGenerator.getInstance(KeyProperties.KEY_ALGORITHM_AES, KEYSTORE)
        keyStore.load(null)
        keyGenerator.init(KeyGenParameterSpec.Builder(KEY,
                KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT)
                .setBlockModes(KeyProperties.BLOCK_MODE_CBC)
                .setUserAuthenticationRequired(true)
                .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_PKCS7)
                .build())
        keyGenerator.generateKey()
        val cipher = Cipher.getInstance(KeyProperties.KEY_ALGORITHM_AES + "/"
                + KeyProperties.BLOCK_MODE_CBC + "/"
                + KeyProperties.ENCRYPTION_PADDING_PKCS7)
        val key = keyStore.getKey(KEY, null) as SecretKey
        cipher.init(Cipher.ENCRYPT_MODE, key)
        return FingerprintManager.CryptoObject(cipher)
    }

    @RequiresApi(AndroidHelper.API_23)
    @RequiresPermission(Manifest.permission.USE_FINGERPRINT)
    fun background(callback: FingerprintManager.AuthenticationCallback): CancellationSignal? {
        return SystemServices.fingerprintManager()?.let { fingerPrintManager ->
            val cancellationSignal = CancellationSignal()
            fingerPrintManager.authenticate(signature(), cancellationSignal, 0, callback, null)
            return cancellationSignal
        }
    }

//    TODO <https://cdn-images-1.medium.com/max/1000/1*8_pvcPgA_CwK2ddt1gH-KQ.png>
    @RequiresApi(AndroidHelper.API_23)
//    @TargetApi(AndroidHelper.API_28)
    @RequiresPermission(Manifest.permission.USE_FINGERPRINT)
    fun foreground(callback: FingerprintManager.AuthenticationCallback) {
//        SystemServices.fingerprintManager()?.let {
//            FingerprintDialog.Builder()
//                    .setTitle("")
//                    //.setSubtitle("")
//                    //.setDescription("")
//                    .setNegativeButton(android.R.string.cancel, Executors.newSingleThreadExecutor(), null)
//                    .build(this)
//        }
        throw NotImplementedError("Coming soon on Android P!")
    }

    fun cancel(cancellationSignal: CancellationSignal?) {
        if (cancellationSignal?.isCanceled == false) {
            cancellationSignal.cancel()
        }
    }

    fun simplyCancelled(errCode: Int): Boolean {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return errCode == FingerprintManager.FINGERPRINT_ERROR_CANCELED
        } else {
            return false
        }
    }

}