package me.shkschneider.skeleton;

import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.io.Serializable;

import me.shkschneider.skeleton.helper.HandlerHelper;

// android.support.v4.os.ResultReceiver
public abstract class SkeletonReceiver {

    private final Handler mHandler;

    public SkeletonReceiver(@NonNull final Handler handler) {
        mHandler = handler;
    }

    public SkeletonReceiver() {
        this(HandlerHelper.main());
    }

    public void post(@NonNull final String id, final Serializable serializable) {
        mHandler.post(new MyRunnable(id, serializable));
    }

    abstract protected void onReceive(@NonNull final String id, @Nullable final Serializable serializable);

    private class MyRunnable implements Runnable {

        final String mId;
        final Serializable mSerializable;

        public MyRunnable(@NonNull final String id, final Serializable serializable) {
            mId = id;
            mSerializable = serializable;
        }

        @Override
        public void run() {
            onReceive(mId, mSerializable);
        }

    }

}
