package me.shkschneider.skeleton.demo

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import me.shkschneider.skeleton.extensions.Intent

import me.shkschneider.skeleton.helper.IntentHelper
import me.shkschneider.skeleton.helper.Logger

// <https://github.com/SimonMarquis/Android-SecretCodes>
class MySecretCodeReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        var secretCode = intent.dataString
        if (secretCode.isNullOrEmpty()) return
        intent.data ?: return
        intent.data?.scheme?.let { scheme ->
            // android_secret_code://
            with(secretCode!!) {
                secretCode = substring(scheme.length)
                if (startsWith("://")) {
                    secretCode = substring("://".length, length)
                }
            }
        }
        Logger.debug("SecretCode: $secretCode")
        if (intent.action == ACTION) {
            context.startActivity(Intent(context, MainActivity::class)
                    .setFlags(IntentHelper.FLAGS_HOME)
                    .putExtra(SECRET_CODE, secretCode))
        }
    }

    companion object {

        const val SECRET_CODE = "SECRET_CODE"
        const val ACTION = "android.provider.Telephony.SECRET_CODE"

    }

}
