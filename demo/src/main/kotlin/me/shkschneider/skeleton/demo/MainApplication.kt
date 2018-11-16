package me.shkschneider.skeleton.demo

import android.content.Context
import android.os.Build
import androidx.multidex.MultiDex
import com.bumptech.glide.Glide
import me.shkschneider.skeleton.SkeletonApplication
import me.shkschneider.skeleton.extensions.Intent
import me.shkschneider.skeleton.helper.ContextHelper
import me.shkschneider.skeleton.helper.IntentHelper
import me.shkschneider.skeleton.helper.ShortcutHelper
import me.shkschneider.skeleton.helper.ThreadHelper

class MainApplication : SkeletonApplication() {

    override fun onCreate() {
        super.onCreate()
        shortcut("About")
        ThreadHelper.backgroundThread(Runnable {
            Glide.get(applicationContext).clearDiskCache()
        })
    }

    private fun shortcut(shortcut: String) {
        if (Build.VERSION.SDK_INT >= 25) {
            ShortcutHelper.setDynamicShortcuts(ShortcutHelper.Shortcut(shortcut.toLowerCase(),
                    R.mipmap.ic_launcher,
                    shortcut.capitalize(),
                    Intent(ContextHelper.applicationContext(), AboutActivity::class)
                            .setFlags(IntentHelper.FLAGS_CLEAR)))
        }
    }

    override fun attachBaseContext(context: Context) {
        super.attachBaseContext(context)
        MultiDex.install(context)
    }

}
