package me.shkschneider.skeleton.demo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;

import me.shkschneider.skeleton.helper.IntentHelper;
import me.shkschneider.skeleton.helper.LogHelper;

// <https://github.com/SimonMarquis/Android-SecretCodes>
public class MySecretCodeReceiver extends BroadcastReceiver {

    public static final String SECRET_CODE = "SECRET_CODE";

    private static final String ACTION = "android.provider.Telephony.SECRET_CODE";

    @Override
    public void onReceive(final Context context, final Intent intent) {
        String secretCode = intent.getDataString();
        final String scheme = intent.getData().getScheme();
        if (! TextUtils.isEmpty(scheme)) {
            //                                android_secret_code://
            secretCode = secretCode.substring(intent.getData().getScheme().length());
            if (secretCode.startsWith("://")) {
                secretCode = secretCode.substring("://".length(), secretCode.length());
            }
        }
        LogHelper.debug("SecretCode: " + secretCode);

        if (intent.getAction().equals(ACTION)) {
            context.startActivity(new Intent(context, MainActivity.class)
                    .setFlags(IntentHelper.FLAGS_HOME)
                    .putExtra(SECRET_CODE, secretCode));
        }
    }

}
