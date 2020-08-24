package me.shkschneider.skeleton.android.security.fingerprint

sealed class FingerprintState {

    object Unavailable : FingerprintState()

    object Success : FingerprintState()
    class Failure(val help: String?) : FingerprintState()
    class Error(val error: String?) : FingerprintState()
    object Cancel : FingerprintState()

}
