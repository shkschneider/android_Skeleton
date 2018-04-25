package me.shkschneider.skeleton.helper

import android.content.Intent
import android.content.pm.ShortcutInfo
import android.graphics.drawable.Icon
import android.support.annotation.DrawableRes
import android.support.annotation.RequiresApi

// <https://developer.android.com/reference/android/content/pm/ShortcutManager.html>
@RequiresApi(AndroidHelper.API_25)
object ShortcutHelper {

    private val DEFAULT = 4
    private var maxRecommendedShortcuts = DEFAULT

    init {
        maxRecommendedShortcuts = SystemServices.shortcutManager()?.maxShortcutCountPerActivity ?: DEFAULT
    }

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
        SystemServices.shortcutManager()?.let { shortcutManager ->
            shortcutManager.dynamicShortcuts?.let { shortcutsInfo ->
                if (shortcutsInfo.size + 1 >= maxRecommendedShortcuts) {
                    Logger.warning("Lots of shortcuts")
                }
            }
            return shortcutManager.isRateLimitingActive && shortcutManager.addDynamicShortcuts(listOf(shortcut.shortcutInfo()))
        }
        Logger.warning("ShortcutManager was NULL")
        return false
    }

    fun setDynamicShortcuts(vararg shortcuts: Shortcut): Boolean {
        if (shortcuts.size >= maxRecommendedShortcuts) {
            Logger.warning("Lots of shortcuts")
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
