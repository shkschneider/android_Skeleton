package me.shkschneider.skeleton.demo

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

import me.shkschneider.skeleton.helper.IntentHelper
import me.shkschneider.skeleton.helper.Logger

// <https://github.com/SimonMarquis/Android-SecretCodes>
class MySecretCodeReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        var secretCode = intent.dataString
        if (secretCode.isNullOrEmpty() || intent.data == null) return

        val scheme = intent.data.scheme
        if (! scheme.isNullOrBlank()) {
            //      android_secret_code://
            secretCode = secretCode!!.substring(scheme.length)
            if (secretCode.startsWith("://")) {
                secretCode = secretCode.substring("://".length, secretCode.length)
            }
        }
        Logger.debug("SecretCode: " + secretCode)
        if (intent.action == ACTION) {
            context.startActivity(Intent(context, MainActivity::class.java)
                    .setFlags(IntentHelper.FLAGS_HOME)
                    .putExtra(SECRET_CODE, secretCode))
        }
    }

    companion object {

        const val SECRET_CODE = "SECRET_CODE"
        const val ACTION = "android.provider.Telephony.SECRET_CODE"

    }

}
