package me.shkschneider.skeleton.ui;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import me.shkschneider.skeleton.helper.ApplicationHelper;

public class UiHelper {

    protected UiHelper() {
        // Empty
    }

    public static final float CARD_ELEVATION_0 = 0F;
    public static final float CARD_ELEVATION_1 = 1F;
    public static final float CARD_ELEVATION_2 = 2F;
    public static final float CARD_ELEVATION_3 = 3F;
    public static final float CARD_ELEVATION_4 = 4F;
    // public static final float CARD_ELEVATION_MIN = CARD_ELEVATION_1;
    // public static final float CARD_ELEVATION_MAX = CARD_ELEVATION_4;

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
    public static View inflate(final int layout) {
        final LayoutInflater layoutInflater = LayoutInflater.from(ApplicationHelper.context());
        return inflate(layoutInflater, null, layout);
    }

}
