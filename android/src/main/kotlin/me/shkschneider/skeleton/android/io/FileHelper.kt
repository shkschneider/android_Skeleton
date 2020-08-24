package me.shkschneider.skeleton.android.io

import android.content.res.Resources
import androidx.annotation.RawRes
import kotlinx.io.IOException
import me.shkschneider.skeleton.android.app.ApplicationHelper
import me.shkschneider.skeleton.android.content.AssetsHelper
import me.shkschneider.skeleton.android.log.Logger
import java.io.File
import java.io.InputStream

object FileHelper {

    const val PREFIX_ASSETS = "file:///android_asset/"
    const val PREFIX_RES = "file:///android_res/"

    fun join(dirname: String, basename: String): String {
        val file = File(dirname, basename)
        return try {
            file.canonicalPath
        } catch (e: IOException) {
            Logger.wtf(e)
            file.path
        }
    }

    fun get(path: String): File =
        File(path)

    fun openRaw(@RawRes id: Int): InputStream? =
        try {
            ApplicationHelper.resources.openRawResource(id)
        } catch (e: Resources.NotFoundException) {
            Logger.wtf(e)
            null
        }

    fun openAsset(assetName: String): InputStream? =
        AssetsHelper.open(assetName)

}
