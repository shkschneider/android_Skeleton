package me.shkschneider.skeleton.securityx.fingerprint

import android.content.Context
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import me.shkschneider.skeleton.extensions.TAG
import me.shkschneider.skeleton.helperx.SystemServices
import me.shkschneider.skeleton.securityx.FingerprintDialogFragment

object Fingerprint {

    @JvmStatic
    fun isAvailable(context: Context): Boolean {
        val keyguardManager = SystemServices.keyguardManager()
        val fingerprintManager = SystemServices.fingerprintManager(context)
        return when {
            keyguardManager?.isKeyguardSecure == true &&
                    fingerprintManager.isHardwareDetected &&
                    fingerprintManager.hasEnrolledFingerprints() -> true
            else -> false
        }
    }

    fun scan(context: Context, fragmentManager: FragmentManager, callback: FingerprintCallback): FingerprintDialogFragment? =
            if (isAvailable(context)) FingerprintDialogFragment.newInstance("")
                    .apply { this.callback = callback }
                    .also { it.show(fragmentManager, it.TAG) }
            else null

}
