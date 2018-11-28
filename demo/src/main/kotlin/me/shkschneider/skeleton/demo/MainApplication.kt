package me.shkschneider.skeleton.demo

import android.content.Context
import android.os.Build
import androidx.multidex.MultiDex
import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.bind
import com.github.salomonbrys.kodein.factory
import com.github.salomonbrys.kodein.instance
import com.github.salomonbrys.kodein.singleton
import com.github.salomonbrys.kodein.with
import me.shkschneider.skeleton.demo.about.AboutActivity
import me.shkschneider.skeleton.di.SkeletonApplication
import me.shkschneider.skeleton.extensions.android.Intent
import me.shkschneider.skeleton.helper.ContextHelper
import me.shkschneider.skeleton.helper.IntentHelper
import me.shkschneider.skeleton.helper.ShortcutHelper
import java.util.UUID

@Suppress("unused")
class MainApplication : SkeletonApplication() {

    override val kodein = Kodein {
        bind<Controller>() with singleton {
            Controller(applicationContext)
        }
        bind<Data>() with factory { tag: UUID ->
            Data(context = applicationContext, id = tag)
        }
    }
    val controller1 by lazy { kodein.instance<Controller>() }
    val controller2 by lazy { kodein.instance<Controller>() }
    // controller1 == controller2

    override fun onCreate() {
        super.onCreate()
        shortcut("About")
        val uuid = UUID.randomUUID()
        val data1 = kodein.with(uuid).instance<Data>()
        val data2 = kodein.with(uuid).instance<Data>()
        // data1 != data2
        val breakpoint: Nothing? = null
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

class Controller(val context: Context) {

    val name: String = "TEST"

}

data class Data(
        val context: Context,
        val id: UUID,
        val text: String?= null
)
