package me.shkschneider.skeleton.securityx.fingerprint

import androidx.core.hardware.fingerprint.FingerprintManagerCompat
import androidx.core.os.CancellationSignal
import me.shkschneider.skeleton.helperx.log.Logger
import me.shkschneider.skeleton.kotlinx.Test.callback

class FingerprintHandler(
        private val fingerprintManager: FingerprintManagerCompat,
        private val callback: FingerprintCallback
) : FingerprintManagerCompat.AuthenticationCallback() {

    private var cancellationSignal: CancellationSignal? = null
    private var selfCancelled = false

    // region LifeCycle

    fun onResume() {
        cancellationSignal = CancellationSignal()
        selfCancelled = false
        fingerprintManager.authenticate(null, 0, cancellationSignal, this, null)
    }

    fun onPause() {
        cancellationSignal?.also {
            selfCancelled = true
            it.cancel()
        }
        cancellationSignal = null
    }

    // endregion

    // region AuthenticationCallback

    override fun onAuthenticationSucceeded(result: FingerprintManagerCompat.AuthenticationResult) {
        callback(FingerprintState.Success)
    }

    override fun onAuthenticationError(errMsgId: Int, errString: CharSequence) {
        if (selfCancelled) {
            callback(FingerprintState.Cancel)
        } else {
            callback(FingerprintState.Error(errString.toString()))
        }
    }

    override fun onAuthenticationHelp(helpMsgId: Int, helpString: CharSequence) {
        Logger.info(helpString.toString())
        callback(FingerprintState.Failure(helpString.toString()))
    }

    override fun onAuthenticationFailed() {
        callback(FingerprintState.Failure(null))
    }

    // endregion

}
