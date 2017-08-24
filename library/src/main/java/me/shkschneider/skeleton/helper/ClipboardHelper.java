package me.shkschneider.skeleton.helper;

import android.content.ClipData;
import android.support.annotation.NonNull;

public class ClipboardHelper {

    protected ClipboardHelper() {
        // Empty
    }

    public static void copy(@NonNull final String string) {
        final ClipData clipData = ClipData.newPlainText(ApplicationHelper.packageName(), string);
        SystemServices.clipboardManager().setPrimaryClip(clipData);
    }

}
