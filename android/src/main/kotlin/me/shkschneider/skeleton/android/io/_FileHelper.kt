package me.shkschneider.skeleton.android.io

import androidx.annotation.RawRes
import me.shkschneider.skeleton.android.app.ApplicationHelper
import me.shkschneider.skeleton.android.provider.ContextProvider
import me.shkschneider.skeleton.kotlin.io.FileHelper
import me.shkschneider.skeleton.kotlin.jvm.tryOrNull
import java.io.InputStream

val FileHelper.PREFIX_ASSETS: String get() = "file:///android_asset/"
val FileHelper.PREFIX_RES: String get() = "file:///android_res/"

fun FileHelper.openRaw(@RawRes id: Int): InputStream? =
    tryOrNull {
        ApplicationHelper.resources.openRawResource(id)
    }

fun FileHelper.openAsset(assetName: String): InputStream? =
    ContextProvider.applicationContext().assets.open(assetName)
