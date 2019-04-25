package me.shkschneider.skeleton.demo

import android.os.Build
import com.squareup.leakcanary.LeakCanary
import me.shkschneider.skeleton.SkeletonApplication
import me.shkschneider.skeleton.demo.about.AboutActivity
import me.shkschneider.skeleton.demo.data.DataManager
import me.shkschneider.skeleton.di.koin
import me.shkschneider.skeleton.extensions.android.Intent
import me.shkschneider.skeleton.helper.ContextHelper
import me.shkschneider.skeleton.helper.IntentHelper
import me.shkschneider.skeleton.helper.ShortcutHelper
import me.shkschneider.skeleton.kotlinx.Coroutines
import org.koin.dsl.module
import java.util.UUID

class MainApplication : SkeletonApplication() {

    override fun onCreate() {
        super.onCreate()

        koin(module {
            factory<UUID> { UUID.randomUUID() }
        })

        if (LeakCanary.isInAnalyzerProcess(this)) {
            return;
        }
        LeakCanary.install(this);

        shortcut("About")

        Coroutines.io {
            DataManager.dummy()
        }
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

}
