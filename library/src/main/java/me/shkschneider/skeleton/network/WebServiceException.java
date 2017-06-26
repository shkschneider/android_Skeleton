package me.shkschneider.skeleton.network;

import android.support.annotation.IntRange;
import android.support.annotation.NonNull;

import me.shkschneider.skeleton.helper.LocaleHelper;

public class WebServiceException extends Exception {

    public static final int INTERNAL_ERROR = 666;

    private final int mCode;

    WebServiceException(@IntRange(from=0, to=INTERNAL_ERROR) final int responseCode, @NonNull final String responseMessage) {
        super(responseMessage);
        mCode = responseCode;
    }

    public int getCode() {
        return mCode;
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }

    @Override
    public String toString() {
        return String.format(LocaleHelper.locale(), "%d %s", getCode(), getMessage());
    }

}
