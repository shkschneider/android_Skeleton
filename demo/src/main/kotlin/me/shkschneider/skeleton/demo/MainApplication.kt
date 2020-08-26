package me.shkschneider.skeleton.demo

import android.os.Build
import me.shkschneider.skeleton.android.app.SkeletonApplication
import me.shkschneider.skeleton.android.content.IntentHelper
import me.shkschneider.skeleton.android.content.ShortcutHelper
import me.shkschneider.skeleton.android.content.intent
import me.shkschneider.skeleton.android.di.koin
import me.shkschneider.skeleton.android.provider.ContextProvider
import me.shkschneider.skeleton.demo.about.AboutActivity
import me.shkschneider.skeleton.kotlin.work.Coroutines
import org.koin.dsl.module
import java.util.Locale
import java.util.UUID

open class MainApplication : SkeletonApplication() {

    override fun onCreate() {
        super.onCreate()

        // FIXME koin(Koin.mainComponent)

        shortcut("About")
    }

    @Suppress("SameParameterValue")
    private fun shortcut(shortcut: String) {
        if (Build.VERSION.SDK_INT >= 25) {
            ShortcutHelper.setDynamicShortcuts(ShortcutHelper.Shortcut(shortcut.toLowerCase(Locale.getDefault()),
                    R.mipmap.ic_launcher,
                    shortcut.capitalize(Locale.getDefault()),
                    intent(ContextProvider.applicationContext(), AboutActivity::class)
                            .setFlags(IntentHelper.FLAGS_CLEAR)))
        }
    }

}