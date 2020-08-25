package me.shkschneider.skeleton.android.content

import android.content.Intent
import android.content.pm.ShortcutInfo
import android.graphics.drawable.Icon
import androidx.annotation.DrawableRes
import androidx.annotation.RequiresApi
import me.shkschneider.skeleton.android.app.ApplicationHelper
import me.shkschneider.skeleton.android.log.Logger
import me.shkschneider.skeleton.android.os.AndroidHelper
import me.shkschneider.skeleton.android.provider.ContextProvider
import me.shkschneider.skeleton.android.provider.SystemServices

// <https://developer.android.com/reference/android/content/pm/ShortcutManager.html>
@RequiresApi(AndroidHelper.API_25)
object ShortcutHelper {

    private val maxRecommendedShortcuts by lazy {
        SystemServices.shortcutManager()?.maxShortcutCountPerActivity ?: 4
    }

    open class Shortcut(
        private var id: String,
        @DrawableRes private var icon: Int,
        private var label: String,
        private var intent: Intent
    ) {

        init {
            if (intent.action.isNullOrEmpty()) {
                intent.action = ApplicationHelper.packageName + "\\." + this.id
            }
        }

        fun shortcutInfo(): ShortcutInfo =
            ShortcutInfo.Builder(ContextProvider.applicationContext(), id)
                .setIcon(Icon.createWithResource(ContextProvider.applicationContext(), icon))
                .setShortLabel(label)
                .setIntent(intent)
                .build()

    }

    fun addDynamicShortcut(shortcut: Shortcut): Boolean {
        SystemServices.shortcutManager()?.let { shortcutManager ->
            val shortcutsInfo = shortcutManager.dynamicShortcuts
            if (shortcutsInfo.size + 1 >= maxRecommendedShortcuts) {
                Logger.warning("Lots of shortcuts")
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
