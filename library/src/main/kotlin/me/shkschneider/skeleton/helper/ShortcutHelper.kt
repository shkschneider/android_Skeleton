package me.shkschneider.skeleton.helper

import android.content.Intent
import android.content.pm.ShortcutInfo
import android.graphics.drawable.Icon
import android.support.annotation.DrawableRes
import android.support.annotation.RequiresApi

// <https://developer.android.com/reference/android/content/pm/ShortcutManager.html>
@RequiresApi(AndroidHelper.API_25)
object ShortcutHelper {

    private val MAX_RECOMMENDED_SHORTCUTS = 4

    class Shortcut {

        private var id: String
        private var icon: Int
        private var label: String
        private var intent: Intent

        constructor(id: String, @DrawableRes icon: Int, label: String, intent: Intent) {
            this.id = id
            this.icon = icon
            this.label = label
            this.intent = intent
            if (intent.action.isNullOrEmpty()) {
                intent.action = ApplicationHelper.packageName() + "\\." + this.id
            }
        }

        fun shortcutInfo(): ShortcutInfo {
            return ShortcutInfo.Builder(ContextHelper.applicationContext(), id)
                    .setIcon(Icon.createWithResource(ContextHelper.applicationContext(), icon))
                    .setShortLabel(label)
                    .setIntent(intent)
                    .build()
        }

    }

    fun addDynamicShortcut(shortcut: Shortcut): Boolean {
        val shortcutManager = SystemServices.shortcutManager() ?: return false
        val dynamicShortcuts = shortcutManager.dynamicShortcuts
        dynamicShortcuts?.let {
            if (it.size + 1 >= MAX_RECOMMENDED_SHORTCUTS) {
                LogHelper.warning("Lots of shortcuts")
            }
        }
        return shortcutManager.isRateLimitingActive && shortcutManager.addDynamicShortcuts(listOf(shortcut.shortcutInfo()))
    }

    fun setDynamicShortcuts(vararg shortcuts: Shortcut): Boolean {
        if (AndroidHelper.api() < AndroidHelper.API_25) return false
        if (shortcuts.size >= MAX_RECOMMENDED_SHORTCUTS) {
            LogHelper.warning("Lots of shortcuts")
        }
        val shortcutManager = SystemServices.shortcutManager() ?: return false
        if (shortcutManager.isRateLimitingActive) {
            return false
        }
        val shortcutInfos = shortcuts.map { it.shortcutInfo() }
        return shortcutManager.setDynamicShortcuts(shortcutInfos)
    }

    fun removeDynamicShortcuts(): Boolean {
        val shortcutManager = SystemServices.shortcutManager() ?: return false
        shortcutManager.removeAllDynamicShortcuts()
        return true
    }

}
