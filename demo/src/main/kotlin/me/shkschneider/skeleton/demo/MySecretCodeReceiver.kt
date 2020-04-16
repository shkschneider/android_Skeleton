package me.shkschneider.skeleton.demo

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import me.shkschneider.skeleton.demo.main.MainActivity
import me.shkschneider.skeleton.extensions.Intent

import me.shkschneider.skeleton.helper.IntentHelper
import me.shkschneider.skeleton.helperx.log.Logger

private const val SECRET_CODE = "SECRET_CODE"
private const val ACTION = "android.provider.Telephony.SECRET_CODE"

// <https://github.com/SimonMarquis/Android-SecretCodes>
class MySecretCodeReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        intent.dataString?.let { dataString ->
            var secretCode = dataString
            intent.data?.scheme?.let { scheme ->
                secretCode = dataString.substring(scheme.length)
                if (secretCode.startsWith("://")) {
                    secretCode = secretCode.substring("://".length, secretCode.length)
                }
            }
            Logger.debug("SecretCode: $secretCode")
            if (intent.action == ACTION) {
                context.startActivity(Intent(context, MainActivity::class)
                        .setFlags(IntentHelper.FLAGS_HOME)
                        .putExtra(SECRET_CODE, secretCode))
            }
        }
    }

}
