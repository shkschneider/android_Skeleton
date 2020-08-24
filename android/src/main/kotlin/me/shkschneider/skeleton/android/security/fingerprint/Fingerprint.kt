package me.shkschneider.skeleton.android.security.fingerprint

import android.content.Context
import androidx.annotation.RequiresPermission
import androidx.fragment.app.FragmentManager
import me.shkschneider.skeleton.kotlin.jvm.TAG
import me.shkschneider.skeleton.android.security.Permissions
import me.shkschneider.skeleton.android.provider.SystemServices

// TODO BioMetrics
@Suppress("DEPRECATION")
object Fingerprint {

    @RequiresPermission(Permissions.USE_FINGERPRINT)
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

    @RequiresPermission(Permissions.USE_FINGERPRINT)
    fun scan(context: Context, fragmentManager: FragmentManager, callback: FingerprintCallback): FingerprintDialogFragment? =
            if (isAvailable(context)) FingerprintDialogFragment.newInstance("")
                    .apply { this.callback = callback }
                    .also { it.show(fragmentManager, it.TAG) }
            else null

}
