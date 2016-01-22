package me.shkschneider.skeleton.ui;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.TypedValue;

import me.shkschneider.skeleton.R;

public class ThemeHelper {

    private static int color(@NonNull final Context context, final int id) {
        final TypedValue typedValue = new TypedValue();
        context.getTheme().resolveAttribute(id, typedValue, true);
        return typedValue.data;
    }

    public static int accentColor(@NonNull final Context context) {
        return color(context, R.attr.colorAccent);
    }

    public static int primaryColor(@NonNull final Context context) {
        return color(context, R.attr.colorPrimary);
    }

    public static int primaryColorDark(@NonNull final Context context) {
        return color(context, R.attr.colorPrimaryDark);
    }

}
