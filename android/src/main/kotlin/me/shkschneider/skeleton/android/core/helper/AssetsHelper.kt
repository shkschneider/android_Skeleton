package me.shkschneider.skeleton.android.core.helper

import android.content.res.AssetManager
import me.shkschneider.skeleton.android.core.data.DataHelper
import me.shkschneider.skeleton.android.log.Logger
import java.io.IOException
import java.io.InputStream

object AssetsHelper {

    fun assetManager(): AssetManager =
        ContextHelper.applicationContext().assets

    fun list(): List<String>? =
        try {
            assetManager().list("")?.toList()
        } catch (e: IOException) {
            Logger.wtf(e)
            null
        }

    fun open(name: String): InputStream? =
        try {
            assetManager().open(name)
        } catch (e: IOException) {
            Logger.wtf(e)
            null
        }

    fun dump(): Boolean {
        var errors = 0
        list()?.forEach { file ->
            try {
                open(file)?.let { inputStream ->
                    DataHelper.Internal.openOutput(file)?.let { outputStream ->
                        inputStream.copyTo(outputStream)
                        inputStream.close()
                        outputStream.flush()
                        outputStream.close()
                    }
                }
            } catch (e: IOException) {
                Logger.wtf(e)
                errors++
            }
        } ?: errors++
        return errors == 0
    }

}
