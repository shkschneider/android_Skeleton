package me.shkschneider.skeleton.android.content

import android.content.res.AssetManager
import me.shkschneider.skeleton.android.log.Logger
import me.shkschneider.skeleton.android.provider.ContextProvider
import java.io.IOException
import java.io.InputStream

object AssetsHelper {

    fun assetManager(): AssetManager =
        ContextProvider.applicationContext().assets

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

    @Throws(NotImplementedError::class)
    fun dump(): Boolean {
        TODO()
    }

}
