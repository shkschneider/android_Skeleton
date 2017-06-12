package me.shkschneider.skeleton.ui;

import android.util.TypedValue;

import me.shkschneider.skeleton.R;
import me.shkschneider.skeleton.helper.ContextHelper;

public class ThemeHelper {

    private static int color(final int id) {
        final TypedValue typedValue = new TypedValue();
        ContextHelper.applicationContext().getTheme().resolveAttribute(id, typedValue, true);
        return typedValue.data;
    }

    public static int accentColor() {
        return color(R.attr.colorAccent);
    }

    public static int primaryColor() {
        return color(R.attr.colorPrimary);
    }

    public static int primaryColorDark() {
        return color(R.attr.colorPrimaryDark);
    }

}
