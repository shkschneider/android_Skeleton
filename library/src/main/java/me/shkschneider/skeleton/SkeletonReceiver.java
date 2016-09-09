package me.shkschneider.skeleton;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.os.ResultReceiver;

// <http://sohailaziz05.blogspot.fr/2012/05/intentservice-providing-data-back-to.html>
@SuppressLint("ParcelCreator")
public class SkeletonReceiver extends ResultReceiver {

    private Callback mCallback;

    public SkeletonReceiver(@NonNull final Handler handler, @NonNull final Callback callback) {
        super(handler);
        mCallback = callback;
    }

    public SkeletonReceiver(@NonNull final Callback callback) {
        this(new Handler(Looper.getMainLooper()), callback);
    }

    @Override
    public void send(final int resultCode, @Nullable final Bundle resultData) {
        super.send(resultCode, resultData);
    }

    @Override
    protected void onReceiveResult(final int resultCode, @Nullable final Bundle resultData) {
        mCallback.onCallback(resultCode, resultData);
    }

    public interface Callback {

        void onCallback(final int resultCode, @Nullable final Bundle resultData);

    }

}
