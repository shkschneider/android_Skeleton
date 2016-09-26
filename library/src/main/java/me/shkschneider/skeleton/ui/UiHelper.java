package me.shkschneider.skeleton.ui;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class UiHelper {

    protected UiHelper() {
        // Empty
    }

    @Nullable
    public static View inflate(@NonNull final LayoutInflater layoutInflater, final ViewGroup container, final int layout) {
        return layoutInflater.inflate(layout, container, false);
    }

    @Nullable
    public static View inflate(@NonNull final ViewGroup container, final int layout) {
        final LayoutInflater layoutInflater = LayoutInflater.from(container.getContext());
        return inflate(layoutInflater, container, layout);
    }

    @Nullable
    public static View inflate(@NonNull final Context context, final int layout) {
        final LayoutInflater layoutInflater = LayoutInflater.from(context);
        return inflate(layoutInflater, null, layout);
    }

}
