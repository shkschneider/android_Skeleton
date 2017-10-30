package me.shkschneider.skeleton.demo

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.text.TextUtils

import me.shkschneider.skeleton.helper.IntentHelper
import me.shkschneider.skeleton.helper.LogHelper

// <https://github.com/SimonMarquis/Android-SecretCodes>
class MySecretCodeReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        var secretCode = intent.dataString
        val scheme = intent.data!!.scheme
        if (!TextUtils.isEmpty(scheme)) {
            //                                  android_secret_code://
            secretCode = secretCode!!.substring(intent.data!!.scheme.length)
            if (secretCode.startsWith("://")) {
                secretCode = secretCode.substring("://".length, secretCode.length)
            }
        }
        LogHelper.debug("SecretCode: " + secretCode!!)
        if (intent.action == ACTION) {
            context.startActivity(Intent(context, MainActivity::class.java)
                    .setFlags(IntentHelper.FLAGS_HOME)
                    .putExtra(SECRET_CODE, secretCode))
        }
    }

    companion object {

        val SECRET_CODE = "SECRET_CODE"
        val ACTION = "android.provider.Telephony.SECRET_CODE"

    }

}
