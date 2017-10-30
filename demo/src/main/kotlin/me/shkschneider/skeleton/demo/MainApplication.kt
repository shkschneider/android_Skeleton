package me.shkschneider.skeleton.demo

import android.content.Context
import android.content.Intent
import android.support.multidex.MultiDex

import me.shkschneider.skeleton.SkeletonApplication
import me.shkschneider.skeleton.helper.ApplicationHelper
import me.shkschneider.skeleton.helper.ContextHelper
import me.shkschneider.skeleton.helper.IntentHelper
import me.shkschneider.skeleton.helper.LogHelper
import me.shkschneider.skeleton.helper.ShortcutHelper
import me.shkschneider.skeleton.java.StringHelper
import me.shkschneider.skeleton.network.Proxy

class MainApplication : SkeletonApplication() {

    override fun onCreate() {
        super.onCreate()
        LogHelper.verbose("DEBUGGABLE=" + ApplicationHelper.debuggable())
        Proxy.get().requestQueue.cache.clear()
        shortcut("About")
    }

    private fun shortcut(shortcut: String) {
        ShortcutHelper.setDynamicShortcuts(ShortcutHelper.Shortcut(StringHelper.lower(shortcut),
                R.mipmap.ic_launcher,
                StringHelper.capitalize(shortcut),
                Intent(ContextHelper.applicationContext(), AboutActivity::class.java)
                        .setFlags(IntentHelper.FLAGS_CLEAR)))
    }

    override fun attachBaseContext(context: Context) {
        super.attachBaseContext(context)
        MultiDex.install(context)
    }

}
