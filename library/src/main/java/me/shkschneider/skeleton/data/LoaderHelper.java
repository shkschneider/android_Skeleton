package me.shkschneider.skeleton.data;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.LoaderManager;

public class LoaderHelper {

    public static void startOrRestart(@NonNull final LoaderManager loaderManager,
                                      final int id, final Bundle args, final LoaderManager.LoaderCallbacks callbacks) {
        if (loaderManager.getLoader(id) != null) {
            loaderManager.restartLoader(id, args, callbacks);
        }
        else {
            loaderManager.initLoader(id, args, callbacks);
        }
    }

}
