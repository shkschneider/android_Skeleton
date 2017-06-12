package me.shkschneider.skeleton;

import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.io.Serializable;

import me.shkschneider.skeleton.helper.HandlerHelper;

// <http://sohailaziz05.blogspot.fr/2012/05/intentservice-providing-data-back-to.html>
public abstract class SkeletonReceiver {

    private Handler mHandler;

    public SkeletonReceiver(@NonNull final Handler handler) {
        mHandler = handler;
    }

    public SkeletonReceiver() {
        this(HandlerHelper.main());
    }

    // android.support.v4.os.ResultReceiver

    public void send(final int resultCode, final Serializable resultData) {
        mHandler.post(new MyRunnable(resultCode, resultData));
    }

    abstract protected void onReceive(final int resultCode, @Nullable final Serializable resultData);

    protected void onReceiveResult(final int resultCode, final Serializable resultData) {
        onReceive(resultCode, resultData);
    }

    private class MyRunnable implements Runnable {

        final int mResultCode;
        final Serializable mResultData;

        public MyRunnable(final int resultCode, final Serializable resultData) {
            mResultCode = resultCode;
            mResultData = resultData;
        }

        @Override
        public void run() {
            onReceiveResult(mResultCode, mResultData);
        }

    }

}
