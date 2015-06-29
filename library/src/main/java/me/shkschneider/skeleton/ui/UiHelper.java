package me.shkschneider.skeleton.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import me.shkschneider.skeleton.helper.LogHelper;
import me.shkschneider.skeleton.helper.SystemServices;

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

    public static View inflate(final ViewGroup container, final int layout) {
        try {
            final LayoutInflater layoutInflater = SystemServices.layoutInflater();
            if (layoutInflater == null) {
                LogHelper.w("LayoutInflater was NULL");
                return null;
            }
            return layoutInflater.inflate(layout, container, false);
        }
        catch (final Exception e) {
            LogHelper.wtf(null, e);
            return null;
        }
    }

    public static View inflate(final int layout) {
        return inflate(null, layout);
    }

}
