package me.shkschneider.skeleton.android.security.fingerprint

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import kotlinx.android.synthetic.main.sk_fingerprint_dialog.*
import me.shkschneider.skeleton.android.R
import me.shkschneider.skeleton.android.core.extensions.cancelable
import me.shkschneider.skeleton.android.core.extensions.dimBehind
import me.shkschneider.skeleton.android.core.extensions.tint
import me.shkschneider.skeleton.android.core.helperx.SystemServices
import me.shkschneider.skeleton.android.log.Logger
import me.shkschneider.skeleton.android.core.kotlinx.exhaustive
import me.shkschneider.skeleton.android.ui.Notify
import me.shkschneider.skeleton.kotlin.TAG
import java.util.concurrent.TimeUnit

private const val TITLE = "TITLE"
private val DELAY = TimeUnit.SECONDS.toMillis(1.toLong())

// <https://github.com/android/security>
class FingerprintDialogFragment : DialogFragment() {

    companion object {

        @JvmStatic
        fun newInstance(title: String) = FingerprintDialogFragment().apply {
            arguments = bundleOf(
                    (TITLE to title)
            )
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
        setStyle(STYLE_NORMAL, android.R.style.Theme_Material_Light_Dialog)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) =
            inflater.inflate(R.layout.sk_fingerprint_dialog, container, false)

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog =
            super.onCreateDialog(savedInstanceState).also { dialog ->
                dialog.dimBehind(true)
                dialog.setTitle(requireNotNull(arguments?.getString(TITLE)))
                dialog.cancelable(cancelable = true, canceledOnTouchOutside = true) {
                    onFingerprintState(FingerprintState.Cancel)
                }
            }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // TODO if unavailable, callback
    }

    override fun onResume() {
        super.onResume()
        fingerprintHandler.onResume()
    }

    override fun onPause() {
        super.onPause()
        fingerprintHandler.onPause()
    }

    // region State

    private val fingerprintHandler: FingerprintHandler by lazy {
        FingerprintHandler(SystemServices.fingerprintManager(requireNotNull(context)), { onFingerprintState(it) })
    }
    lateinit var callback: FingerprintCallback

    private fun onFingerprintState(state: FingerprintState) {
        Logger.debug("onFingerprintState: ${state.TAG}")
        when (state) {
            is FingerprintState.Success -> {
                skFingerprintIcon.tint(ContextCompat.getColor(skFingerprintIcon.context, R.color.sk_material_green))
                dismissWithState(state)
            }
            is FingerprintState.Failure -> {
                skFingerprintIcon.tint(ContextCompat.getColor(skFingerprintIcon.context, R.color.sk_material_orange))
                state.help?.let { Notify.toast(it) }
            }
            is FingerprintState.Error -> {
                skFingerprintIcon.tint(ContextCompat.getColor(skFingerprintIcon.context, R.color.sk_material_red))
                state.error?.let { Notify.toast(it) }
                dismissWithState(state)
            }
            is FingerprintState.Cancel -> {
                dismissWithState(state)
            }
            is FingerprintState.Unavailable -> {
                callback(state)
                dismissNowWithState(state)
            }
        }.exhaustive
        callback(state)
    }

    fun dismissWithState(state: FingerprintState) {
        skFingerprintIcon.postDelayed({
            dismissNowWithState(state)
        }, DELAY)
    }

    fun dismissNowWithState(state: FingerprintState) {
        callback(state)
        super.dismiss()
    }

    @Deprecated("dismissWithState()", ReplaceWith("dismissWithState()"))
    override fun dismiss() {
        super.dismiss()
    }

    // endregion

}
