package me.shkschneider.skeleton.helper

import android.content.res.AssetManager
import me.shkschneider.skeleton.data.InternalDataHelper
import java.io.IOException
import java.io.InputStream

object AssetsHelper {

    fun assetManager(): AssetManager {
        return ContextHelper.applicationContext().assets
    }

    fun list(): List<String>? {
        val assetManager = assetManager()
        try {
            return assetManager.list("")?.toList()
        } catch (e: IOException) {
            Logger.wtf(e)
            return null
        }
    }

    fun open(name: String): InputStream? {
        val assetManager = assetManager()
        try {
            return assetManager.open(name)
        } catch (e: IOException) {
            Logger.wtf(e)
            return null
        }
    }

    fun dump(): Boolean {
        var errors = 0
        val assets = list()
        assets ?: errors++
        assets?.forEach {
            try {
                val inputStream = open(it) ?: throw NullPointerException()
                val outputStream = InternalDataHelper.openOutput(it) ?: throw NullPointerException()
                inputStream.copyTo(outputStream)
                inputStream.close()
                outputStream.flush()
                outputStream.close()
            } catch (e: IOException) {
                Logger.wtf(e)
                errors++
            }
        }
        return errors == 0
    }

}
