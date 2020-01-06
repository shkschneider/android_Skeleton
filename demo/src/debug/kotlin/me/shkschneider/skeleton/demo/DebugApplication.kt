package me.shkschneider.skeleton.demo

import com.facebook.flipper.android.AndroidFlipperClient
import com.facebook.flipper.android.utils.FlipperUtils
import com.facebook.flipper.plugins.crashreporter.CrashReporterPlugin
import com.facebook.flipper.plugins.inspector.DescriptorMapping
import com.facebook.flipper.plugins.inspector.InspectorFlipperPlugin
import com.facebook.flipper.plugins.network.NetworkFlipperPlugin
import com.facebook.soloader.SoLoader
import me.shkschneider.skeleton.demo.data.DataManager
import me.shkschneider.skeleton.kotlinx.Coroutines

class DebugApplication : MainApplication() {

    override fun onCreate() {
        super.onCreate()

        flipper()

        Coroutines.io {
            DataManager.dummy()
        }
    }

    private fun flipper() {
        SoLoader.init(this, false);
        if (FlipperUtils.shouldEnableFlipper(this)) {
            AndroidFlipperClient.getInstance(this).also {
                // Layout Inscpector
                it.addPlugin(InspectorFlipperPlugin(this, DescriptorMapping.withDefaults()))
                // Crash Reporter
                it.addPlugin(CrashReporterPlugin.getInstance())
                // Network (OkHttp: addNetworkInterceptor(FlipperOkhttpInterceptor(NetworkFlipperPlugin())))
                it.addPlugin(NetworkFlipperPlugin());
            }.start()
        }
    }

}
