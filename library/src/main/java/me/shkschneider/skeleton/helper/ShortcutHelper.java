package me.shkschneider.skeleton.helper;

import android.annotation.TargetApi;
import android.content.Intent;
import android.content.pm.ShortcutInfo;
import android.content.pm.ShortcutManager;
import android.graphics.drawable.Icon;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

// <https://developer.android.com/reference/android/content/pm/ShortcutManager.html>
@TargetApi(AndroidHelper.API_25)
public class ShortcutHelper {

    private static final int MAX_RECOMMENDED_SHORTCUTS = 4;

    protected ShortcutHelper() {
        // Empty
    }

    public static boolean addDynamicShortcut(@NonNull final Shortcut shortcut) {
        final ShortcutManager shortcutManager = SystemServices.shortcutManager();
        if (shortcutManager.getDynamicShortcuts().size() + 1 >= MAX_RECOMMENDED_SHORTCUTS) {
            LogHelper.wtf("Lots of shortcuts");
        }
        if (shortcutManager.isRateLimitingActive()) {
            return false;
        }

        return shortcutManager.addDynamicShortcuts(Collections.singletonList(shortcut.shortcutInfo()));
    }

    public static boolean setDynamicShortcuts(@NonNull final Shortcut ... shortcuts) {
        if (shortcuts.length >= MAX_RECOMMENDED_SHORTCUTS) {
            LogHelper.wtf("Lots of shortcuts");
        }
        final ShortcutManager shortcutManager = SystemServices.shortcutManager();
        if (shortcutManager.isRateLimitingActive()) {
            return false;
        }

        final List<ShortcutInfo> shortcutInfos = new ArrayList<>();
        for (final Shortcut shortcut : shortcuts) {
            shortcutInfos.add(shortcut.shortcutInfo());
        }
        return shortcutManager.setDynamicShortcuts(shortcutInfos);
    }

    public static class Shortcut {

        private String id;
        private int icon;
        private String label;
        private Intent intent;

        public Shortcut(@NonNull final String id,
                        @DrawableRes final int icon,
                        @NonNull final String label,
                        @NonNull final Intent intent) {
            this.id = id;
            this.icon = icon;
            this.label = label;
            this.intent = intent;
        }

        private ShortcutInfo shortcutInfo() {
            return new ShortcutInfo.Builder(ContextHelper.applicationContext(), this.id)
                    .setIcon(Icon.createWithResource(ContextHelper.applicationContext(), this.icon))
                    .setShortLabel(this.label)
                    .setIntent(this.intent)
                    .build();
        }

    }

}
