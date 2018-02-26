package me.shkschneider.skeleton.helper

import android.annotation.TargetApi
import android.content.Intent
import android.content.pm.ShortcutInfo
import android.graphics.drawable.Icon
import android.support.annotation.DrawableRes
import android.text.TextUtils

// <https://developer.android.com/reference/android/content/pm/ShortcutManager.html>
@TargetApi(AndroidHelper.API_25)
object ShortcutHelper {

    private const val MAX_RECOMMENDED_SHORTCUTS = 4

    class Shortcut {

        private var _id: String
        private var _icon: Int
        private var _label: String
        private var _intent: Intent

        constructor(id: String, @DrawableRes icon: Int, label: String, intent: Intent) {
            _id = id
            _icon = icon
            _label = label
            _intent = intent
            if (TextUtils.isEmpty(intent.action)) {
                intent.action = ApplicationHelper.packageName() + "\\." + _id
            }
        }

        fun shortcutInfo(): ShortcutInfo {
            return ShortcutInfo.Builder(ContextHelper.applicationContext(), _id)
                    .setIcon(Icon.createWithResource(ContextHelper.applicationContext(), _icon))
                    .setShortLabel(_label)
                    .setIntent(_intent)
                    .build()
        }

    }

    fun addDynamicShortcut(shortcut: Shortcut): Boolean? {
        val shortcutManager = SystemServices.shortcutManager()
        if (shortcutManager?.dynamicShortcuts == null) {
            return false
        }
        if (shortcutManager.dynamicShortcuts.size + 1 >= MAX_RECOMMENDED_SHORTCUTS) {
            LogHelper.wtf("Lots of shortcuts")
        }
        return !shortcutManager.isRateLimitingActive && shortcutManager.addDynamicShortcuts(listOf(shortcut.shortcutInfo()))
    }

    fun setDynamicShortcuts(vararg shortcuts: Shortcut): Boolean? {
        if (shortcuts.size >= MAX_RECOMMENDED_SHORTCUTS) {
            LogHelper.wtf("Lots of shortcuts")
        }
        val shortcutManager = SystemServices.shortcutManager()
        if (shortcutManager?.isRateLimitingActive == true) {
            return false
        }
        val shortcutInfos = shortcuts.map { it.shortcutInfo() }
        return shortcutManager?.setDynamicShortcuts(shortcutInfos)
    }

}
