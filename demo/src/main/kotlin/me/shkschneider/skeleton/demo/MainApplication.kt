package me.shkschneider.skeleton.demo

import android.content.Context
import android.content.Intent
import android.support.multidex.MultiDex
import me.shkschneider.skeleton.SkeletonApplication
import me.shkschneider.skeleton.helper.*

class MainApplication : SkeletonApplication() {

    override fun onCreate() {
        super.onCreate()
        LogHelper.verbose("DEBUGGABLE=" + ApplicationHelper.debuggable())
        shortcut("About")
    }

    private fun shortcut(shortcut: String) {
        ShortcutHelper.setDynamicShortcuts(ShortcutHelper.Shortcut(shortcut.toLowerCase(),
                R.mipmap.ic_launcher,
                shortcut.capitalize(),
                Intent(ContextHelper.applicationContext(), AboutActivity::class.java)
                        .setFlags(IntentHelper.FLAGS_CLEAR)))
    }

    override fun attachBaseContext(context: Context) {
        super.attachBaseContext(context)
        MultiDex.install(context)
    }

}
