package me.shkschneider.skeleton.demo

import android.content.Context
import android.content.Intent
import android.os.Build
import android.support.multidex.MultiDex
import me.shkschneider.skeleton.SkeletonApplication
import me.shkschneider.skeleton.helper.*

class MainApplication : SkeletonApplication() {

    override fun onCreate() {
        super.onCreate()
        Logger.verbose("DEBUGGABLE=" + ApplicationHelper.debuggable())
        shortcut("About")
    }

    private fun shortcut(shortcut: String) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N_MR1) {
            ShortcutHelper.setDynamicShortcuts(ShortcutHelper.Shortcut(shortcut.toLowerCase(),
                    R.mipmap.ic_launcher,
                    shortcut.capitalize(),
                    Intent(ContextHelper.applicationContext(), AboutActivity::class.java)
                            .setFlags(IntentHelper.FLAGS_CLEAR)))
        }
    }

    override fun attachBaseContext(context: Context) {
        super.attachBaseContext(context)
        MultiDex.install(context)
    }

}
