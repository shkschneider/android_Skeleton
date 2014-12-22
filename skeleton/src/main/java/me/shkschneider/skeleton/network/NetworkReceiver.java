package me.shkschneider.skeleton.network;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import me.shkschneider.skeleton.SkeletonEvent;
import me.shkschneider.skeleton.helpers.LogHelper;

public class NetworkReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(final Context context, final Intent intent) {
        if (NetworkHelper.online()) {
            LogHelper.info("Went ONLINE");
        }
        else {
            LogHelper.info("Went OFFLINE");
            // TODO: cancel WebServiceController requests?
        }

        new SkeletonEvent(NetworkEvents.CONNECTIVITY)
                .put(NetworkEvents.CONNECTIVITY_TYPE, NetworkHelper.type())
                .put(NetworkEvents.CONNECTIVITY_STATUS, NetworkHelper.online())
                .send();
    }

}
