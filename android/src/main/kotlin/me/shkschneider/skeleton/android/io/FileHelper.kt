package me.shkschneider.skeleton.android.io

import androidx.annotation.RawRes
import me.shkschneider.skeleton.android.app.ApplicationHelper
import me.shkschneider.skeleton.android.provider.ContextProvider
import me.shkschneider.skeleton.kotlin.jvm.tryOr
import me.shkschneider.skeleton.kotlin.jvm.tryOrNull
import java.io.File
import java.io.InputStream

object FileHelper {

    const val PREFIX_ASSETS = "file:///android_asset/"
    const val PREFIX_RES = "file:///android_res/"

    fun join(dirname: String, basename: String): String {
        val file = File(dirname, basename)
        return tryOr(file.path) {
            file.canonicalPath
        } as String
    }

    fun get(path: String): File =
        File(path)

    fun openRaw(@RawRes id: Int): InputStream? =
        tryOrNull {
            ApplicationHelper.resources.openRawResource(id)
        }

    fun openAsset(assetName: String): InputStream? =
        ContextProvider.applicationContext().assets.open(assetName)

}
