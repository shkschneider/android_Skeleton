package me.shkschneider.skeleton.demo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

// <https://github.com/SimonMarquis/Android-SecretCodes>
public class MySecretCodeReceiver extends BroadcastReceiver {

    private static final String ACTION = "android.provider.Telephony.SECRET_CODE";

    @Override
    public void onReceive(final Context context, final Intent intent) {
        if (intent.getAction().equals(ACTION)) {
            context.startActivity(new Intent(context, MainActivity.class));
        }
    }

}
